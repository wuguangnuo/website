package cn.wgn.website.dto.home;

import cn.wgn.website.dto.RequestPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * @author WuGuangNuo
 * @date Created in 2020/3/23 14:23
 */
public class VistorTableQuery extends RequestPage {
    @ApiModelProperty(value = "筛选链接")
    private String link;

    @ApiModelProperty(value = "筛选网络蜘蛛")
    private int check;

    @ApiModelProperty(value = "时间区间1")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime1;

    @ApiModelProperty(value = "时间区间2")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime2;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public LocalDateTime getDateTime1() {
        return dateTime1;
    }

    public void setDateTime1(LocalDateTime dateTime1) {
        this.dateTime1 = dateTime1;
    }

    public LocalDateTime getDateTime2() {
        return dateTime2;
    }

    public void setDateTime2(LocalDateTime dateTime2) {
        this.dateTime2 = dateTime2;
    }
}
