package cn.wgn.website.dto.profile;

import cn.wgn.website.dto.SelectListItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/22 23:06
 */
@Data
@Accessors(chain = true)
public class MenuTree {
    private String icon;
    private String index;
    private String title;
    private List<SelectListItem> subs;
}
