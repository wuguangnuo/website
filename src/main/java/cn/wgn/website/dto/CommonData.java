package cn.wgn.website.dto;

/**
 * 通用数据(主要用于单条数据Post)
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/3 10:22
 */
public class CommonData {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
