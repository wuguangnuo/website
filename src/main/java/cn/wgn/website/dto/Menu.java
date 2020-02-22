package cn.wgn.website.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/22 23:09
 */
@Data
@Accessors(chain = true)
public class Menu {
    @ApiModelProperty(value = "权限code")
    private String code;
    @ApiModelProperty(value = "菜单name")
    private String name;
    @ApiModelProperty(value = "菜单url")
    private String url;
    @ApiModelProperty(value = "图标icon")
    private String icon;
}
