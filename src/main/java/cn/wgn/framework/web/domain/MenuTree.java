package cn.wgn.framework.web.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;

/**
 * 树形菜单
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/1 22:04
 */
@Data
@Accessors(chain = true)
public class MenuTree extends BaseDto {
    private String icon;
    private String index;
    private String title;
    private HashMap<String, String> subs;
}
