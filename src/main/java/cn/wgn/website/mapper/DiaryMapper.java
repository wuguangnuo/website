package cn.wgn.website.mapper;

import cn.wgn.website.entity.DiaryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Diary Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-02-16
 */
@Repository
public interface DiaryMapper extends BaseMapper<DiaryEntity> {

    /**
     * 获取最后一条数据
     *
     * @return
     */
    @Select("SELECT * FROM `wu_diary` ORDER BY id DESC LIMIT 1")
    DiaryEntity getLastDiary();
}
