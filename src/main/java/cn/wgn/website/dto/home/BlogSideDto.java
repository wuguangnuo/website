package cn.wgn.website.dto.home;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/19 15:29
 */
@Data
@Accessors(chain = true)
public class BlogSideDto {
    private Long id;
    private String postTitle;
    private String postTitleCut;
}
