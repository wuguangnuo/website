package cn.wgn.website;

import cn.wgn.website.sys.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/9/13 10:55
 */
@SpringBootTest
public class WebsiteTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void dbTest() {
        String dbName = "wuguangnuo";
        int limit = 999;
        List<HashMap<String, String>> dbInfo = taskMapper.getDBInfo(dbName, limit);
        dbInfo.forEach(System.out::println);
    }

}
