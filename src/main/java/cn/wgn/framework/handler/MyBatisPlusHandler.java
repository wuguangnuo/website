package cn.wgn.framework.handler;

import cn.wgn.framework.utils.TokenUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatisPlus 自动填充 @TableField
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/31 13:19
 */
@Component
public class MyBatisPlusHandler implements MetaObjectHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 插入数据填充
     *
     * @param metaObject MetaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start insert fill for [" + metaObject.getOriginalObject().getClass().getName() + "]");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        Long userId = TokenUtil.getUserId();
        String userName = TokenUtil.getUserName();
        if (userId != null) {
            this.strictInsertFill(metaObject, "createById", Long.class, userId);
        }
        if (userName != null) {
            this.strictInsertFill(metaObject, "createByName", String.class, userName);
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

        Long userId = TokenUtil.getUserId();
        String userName = TokenUtil.getUserName();
        if (userId != null) {
            this.strictInsertFill(metaObject, "modifiedById", Long.class, userId);
        }
        if (userName != null) {
            this.strictInsertFill(metaObject, "modifiedByName", String.class, userName);
        }
    }
}
