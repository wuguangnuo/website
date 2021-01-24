package cn.wgn.framework.web.domain;

import java.util.HashMap;
import java.util.List;

/**
 * 树形菜单
 *
 * @author WuGuangNuo
 * @date Created in 2020/7/1 22:04
 */
public class MenuTree extends BaseDto {
    private String icon;
    private String index;
    private String title;
    private List<HashMap<String, String>> subs;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HashMap<String, String>> getSubs() {
        return subs;
    }

    public void setSubs(List<HashMap<String, String>> subs) {
        this.subs = subs;
    }
}
