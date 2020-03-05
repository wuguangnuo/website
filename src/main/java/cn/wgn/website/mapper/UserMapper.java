package cn.wgn.website.mapper;

import cn.wgn.website.dto.profile.ProfileDto;
import cn.wgn.website.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * User Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 更新不为空的信息
     *
     * @param dto
     * @return
     */
    int updateButNull(@Param("dto") ProfileDto dto, @Param("id") int id);
}
