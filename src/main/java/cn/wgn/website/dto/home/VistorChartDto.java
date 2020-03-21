package cn.wgn.website.dto.home;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

/**
 * 管理界面访客图表
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/20 22:05
 */
public class VistorChartDto {
    @ApiModelProperty("图表类型")
    private String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty("时间1")
    private LocalDate date1;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty("时间2")
    private LocalDate date2;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public LocalDate getDate2() {
        return date2;
    }

    public void setDate2(LocalDate date2) {
        this.date2 = date2;
    }

    @Override
    public String toString() {
        return "VistorChartDto{" +
                "type='" + type + '\'' +
                ", date1=" + date1 +
                ", date2=" + date2 +
                '}';
    }
}
