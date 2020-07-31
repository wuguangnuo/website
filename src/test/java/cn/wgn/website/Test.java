package cn.wgn.website;

import cn.wgn.framework.utils.DateUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WuGuangNuo
 * @date Created in 2020/3/14 19:28
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(DateUtil.toDate(LocalDateTime.now()).getYear());
        System.out.println(Calendar.getInstance().get(Calendar.YEAR));

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        System.out.println(c.get(Calendar.YEAR));

//        String secret = "abc_xyz";
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("a", "123");
//        System.out.println(create(claims, secret));
//        System.out.println(create(claims, "secret"));
//        claims.put("b", "456");
//        System.out.println(create(claims, secret));
//        claims.put("1111111111111111111", "22222222222222222222222222");
//        System.out.println(create(claims, secret));
//        System.out.println(create(claims, secret));
//
//        System.out.println(parser("eyJhbGciOiJIUzUxMiJ9.eyJhIjoiMTIzIiwiYiI6IjQ1NiJ9.8UpOXSiOUb3k94VGg-Mm-0Hh7e3SgthcGAi2all3BUQbqUDFrrpqMghX9RP0SXeKb3-cdO5UICCowvVtTvML9A"
//                , secret));
//        System.out.println(parser("eyJhbGciOiJIUzUxMiJ9.eyJhIjoiMTIzIn0.8UpOXSiOUb3k94VGg-Mm-0Hh7e3SgthcGAi2all3BUQbqUDFrrpqMghX9RP0SXeKb3-cdO5UICCowvVtTvML9A"
//                , secret));
    }

    private static String create(Map claims, String secret) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private static Map parser(String jwt, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
