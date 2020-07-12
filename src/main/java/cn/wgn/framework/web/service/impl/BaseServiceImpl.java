package cn.wgn.framework.web.service.impl;

import cn.wgn.framework.web.service.IBaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/5/24 23:15
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T>
        implements IBaseService<T> {
    public Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * 随机排序所有数据
     *
     * @return
     */
    @Override
    public List<T> randList() {
        return this.randList(100);
    }

    /**
     * 随机排序所有数据+限制数量
     *
     * @return
     */
    @Override
    public List<T> randList(int limit) {
        return this.list(
                new QueryWrapper<T>().last("ORDER BY RAND() LIMIT " + limit)
        );
    }
}
