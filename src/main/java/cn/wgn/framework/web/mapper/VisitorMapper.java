package cn.wgn.framework.web.mapper;

import cn.wgn.framework.web.entity.VisitorEntity;
import cn.wgn.website.sys.dto.HomeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 访客统计表 Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */
@Repository
public interface VisitorMapper extends BaseMapper<VisitorEntity> {
    /**
     * 获取首页信息
     *
     * @param id
     * @return
     */
    HomeInfo getHomeInfo(@Param("uid") Long id);
}
