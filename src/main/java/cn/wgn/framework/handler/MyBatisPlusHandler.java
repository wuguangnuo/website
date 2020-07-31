package cn.wgn.framework.handler;

import cn.wgn.framework.utils.TokenUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

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
//    @Autowired
//    private TokenUtil tokenUtil;

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
            this.strictInsertFill(metaObject, "createById", Long.class, TokenUtil.getUserId());
            this.strictInsertFill(metaObject, "createByName", String.class, TokenUtil.getUserName());
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
            this.strictInsertFill(metaObject, "modifiedById", Long.class, TokenUtil.getUserId());
            this.strictInsertFill(metaObject, "modifiedByName", String.class, TokenUtil.getUserName());
        } catch (NullPointerException e) {
            String objName = metaObject.getOriginalObject().getClass().getName();
            log.info("[" + objName + "]更新填充进行了不安全的操作，无法获取操作人信息");
        }
    }
}
