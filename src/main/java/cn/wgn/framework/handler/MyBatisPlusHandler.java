package cn.wgn.framework.handler;

import cn.wgn.framework.exception.CommonException;
import cn.wgn.framework.utils.servlet.ServletUtil;
import cn.wgn.framework.web.domain.UserData;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * MyBatisPlus 自动填充 @TableField
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/31 13:19
 */
@Slf4j
@Component
public class MyBatisPlusHandler implements MetaObjectHandler {
    /**
     * 插入数据填充
     *
     * @param metaObject MetaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start insert fill for [" + metaObject.getOriginalObject().getClass().getName() + "]");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        try {
            ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            UserData userData = (UserData) attributes.getRequest().getAttribute("userData");
            this.strictInsertFill(metaObject, "createById", Long.class, userData.getId());
            this.strictInsertFill(metaObject, "createByName", String.class, userData.getAccount());
        } catch (NullPointerException e) {
            String objName = metaObject.getOriginalObject().getClass().getName();
            log.info("[" + objName + "]插入填充进行了不安全的操作，无法获取操作人信息");
        }
    }

    /**
     * 更新数据填充
     *
     * @param metaObject MetaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start update fill for [" + metaObject.getOriginalObject().getClass().getName() + "]");
        this.strictUpdateFill(metaObject, "modifiedTime", LocalDateTime.class, LocalDateTime.now());

        try {
            ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            UserData userData = (UserData) attributes.getRequest().getAttribute("userData");
            this.strictInsertFill(metaObject, "modifiedById", Long.class, userData.getId());
            this.strictInsertFill(metaObject, "modifiedByName", String.class, userData.getAccount());
        } catch (NullPointerException e) {
            String objName = metaObject.getOriginalObject().getClass().getName();
            log.info("[" + objName + "]更新填充进行了不安全的操作，无法获取操作人信息");
        }
    }
}
