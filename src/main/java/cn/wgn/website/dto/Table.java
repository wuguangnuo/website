package cn.wgn.website.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 表格封装
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/25 23:56
 */
@Data
@Accessors(chain = true)
public class Table<T> {
    @ApiModelProperty("表格内容")
    private IPage<T> tbody;
    @ApiModelProperty("表格表头")
    private List<String> theader;
}
