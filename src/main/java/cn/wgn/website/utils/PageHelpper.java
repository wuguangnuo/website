package cn.wgn.website.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/16 15:38
 */
@Component
public class PageHelpper {
    public final Page MAX_SIZE = new Page(0, 200);
    public final Page DEFAULT_SIZE = new Page(0, 10);
}
