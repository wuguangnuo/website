package cn.wgn.framework.web.mapper;

import cn.wgn.framework.web.entity.UserEntity;
import cn.wgn.framework.web.domain.ProfileDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-06-07
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 更新不为空的信息
     *
     * @param dto
     * @return
     */
    int updateButNull(@Param("dto") ProfileDto dto, @Param("id") Long id);
}
