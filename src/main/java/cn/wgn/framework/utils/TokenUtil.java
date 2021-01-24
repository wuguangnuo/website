package cn.wgn.framework.utils;

import cn.wgn.framework.constant.MagicValue;
import cn.wgn.framework.utils.servlet.ServletUtil;
import cn.wgn.framework.web.domain.UserData;
import cn.wgn.framework.web.enums.RedisPrefixKeyEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Token 验证处理
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/26 21:16
 */
@Component
public class TokenUtil {
    /**
     * 令牌秘钥
     */
    public static String secret;

    /**
     * 令牌有效期（默认60分钟）
     */
    public static int expireTime;

    @Value("${private-config.token.secret}")
    public void setSecret(String secret) {
        TokenUtil.secret = secret;
    }

    @Value("${private-config.token.expireTime}")
    public void setExpireTime(int expireTime) {
        TokenUtil.expireTime = expireTime;
    }

    @Autowired
    private static RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        TokenUtil.redisUtil = redisUtil;
    }

    public static String getSecret() {
        return secret;
    }

    public static int getExpireTime() {
        return expireTime;
    }

    /**
     * 不足20分钟时刷新
     */
    private static final Long MINUTE_FRESH = 20L;

    /**
     * 创建令牌
     *
     * @param userData 用户信息
     * @return 令牌
     */
    public static String createToken(UserData userData) {
        String uuid = IdUtil.fastSimpleUUID();
        userData.setUuid(uuid);
        refreshToken(userData);

        Map<String, Object> claims = new HashMap<>();
        claims.put(MagicValue.UUID, uuid);
        claims.put(MagicValue.ID, userData.getId());
        claims.put(MagicValue.NAME, userData.getUsername());
        return createToken(claims);
    }

    /**
     * 删除令牌
     *
     * @param uuid
     */
    public static void deleteToken(String uuid) {
        redisUtil.delete(uuid, RedisPrefixKeyEnum.TOKEN.getValue());
    }

    /**
     * 验证令牌有效期，小于20分钟，自动刷新缓存
     *
     * @param uuid 要刷新的uuid
     * @return 令牌
     */
    public static UserData verifyToken(String uuid) {
        // -1:无限时间,-2:不存在
        long expireTime = redisUtil.getExpire(uuid, RedisPrefixKeyEnum.TOKEN.getValue());
        UserData userData = redisUtil.get(uuid, RedisPrefixKeyEnum.TOKEN.getValue(), UserData.class);
        if (expireTime <= MINUTE_FRESH * 60 && expireTime > 0) {
            userData.setUuid(uuid);
            refreshToken(userData);
            userData.setLastLogin(LocalDateTime.now());
        }
        return userData;
    }

    /**
     * 刷新令牌有效期
     *
     * @param userData 登录信息
     */
    public static void refreshToken(UserData userData) {
        userData.setLastLogin(LocalDateTime.now());
        // 根据uuid将loginUser缓存
        redisUtil.set(userData.getUuid(), RedisPrefixKeyEnum.TOKEN.getValue()
                , userData, expireTime * 60);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public static UserData getUserData() {
        // 获取请求携带的令牌
        String token = ServletUtil.getHeader(MagicValue.TOKEN);
        if (StringUtil.isEmpty(token)) {
            // 请求头未携带 token
            return null;
        }
        Claims claims = parseToken(token);
        // 解析对应的权限以及用户信息
        String uuid = (String) claims.get(MagicValue.UUID);
        if (StringUtil.isEmpty(uuid) || uuid.length() != 32) {
            // uuid 格式错误
            return null;
        }
        if (!redisUtil.exists(uuid, RedisPrefixKeyEnum.TOKEN.getValue())) {
            // Redis 不存在或过期
            return null;
        }
        return verifyToken(uuid);
    }

    /**
     * 从令牌中获取用户ID
     *
     * @return
     */
    public static Long getUserId() {
        try {
            String token = ServletUtil.getHeader(MagicValue.TOKEN);
            if (StringUtil.isEmpty(token)) {
                return null;
            }
            Claims claims = parseToken(token);
            return Long.valueOf(claims.get(MagicValue.ID) + "");
        } catch (NullPointerException | JwtException e) {
            return null;
        }
    }

    /**
     * 从令牌中获取用户
     *
     * @return
     */
    public static String getUserName() {
        try {
            String token = ServletUtil.getHeader(MagicValue.TOKEN);
            if (StringUtil.isEmpty(token)) {
                return null;
            }
            Claims claims = parseToken(token);
            return (String) claims.get(MagicValue.NAME);
        } catch (NullPointerException | JwtException e) {
            return null;
        }
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return jwt令牌
     */
    private static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token jwt令牌
     * @return 数据声明
     */
    private static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
