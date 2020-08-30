package cn.wgn.website.sys.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 其他 Mapper 接口
 * </p>
 *
 * @author WuGuangNuo
 * @since 2020-08-19
 */
@Repository
public interface TaskMapper {
    /**
     * 获取数据库容量信息
     *
     * @param dbName
     * @return
     */
    List<HashMap<String, String>> getDBInfo(@Param("dbName") String dbName, @Param("limit") Integer limit);
}
