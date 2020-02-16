package cn.wgn.website.mapper;

import cn.wgn.website.entity.DocEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Doc Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Repository
public interface DocMapper extends BaseMapper<DocEntity> {
    /**
     * 获取开发文档列表
     *
     * @param page
     * @return
     */
    IPage<DocEntity> getDoc(Page page);
}
