package cn.wgn.website.utils;

/**
 * HTML 模型
 *
 * @author WuGuangNuo
 * @date Created in 2020/3/17 23:47
 */
public class HtmlModel {
    /**
     * 邮件标准格式
     *
     * @param title  邮件类型
     * @param detail 详细内容
     * @return 邮件内容
     */
    public static String mailBody(String title, String detail) {
        return "<div style='font-size:16px;user-select:none'>" +
                "<p>这是一封<strong style='color:red;font-size:20px;'>" + title + "</strong>。</p>" +
                detail +
                "<p>点击进入\uD83D\uDC49<a style='color:blue;text-decoration:none'href='https://map.wuguangnuo.cn'>网站地图</a></p>" +
                "<p style='font-size:14px;font-family:Courier New'>@author&nbsp;<a style='color:black;text-decoration:none'href='https://github.com/wuguangnuo'>WuGuangNuo</a></p></div>";
    }

    /**
     * 分享链接页面
     *
     * @param title  结果信息
     * @param detail meta
     * @return 分享链接页面
     */
    public static String sharePage(String title, String detail) {
        String icoUrl = "https://t.wuguangnuo.cn/favicon.ico";

        return "<!DOCTYPE html><html><head>" +
                "<meta http-equiv='Content-Type'content='text/html;charset=utf-8'>" +
                "<meta http-equiv='X-UA-Compatible'content='IE=edge,chrome=1'>" +
                "<meta name='renderer'content='webkit|ie-comp|ie-stand'>" +
                "<meta name='viewport'content='width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no'>" +
                "<title>文件分享系统|諾</title>" +
                "<link rel='icon'type='image/x-icon'href='" + icoUrl + "'/>" +
                "<link rel='shortcut icon'type='image/x-icon'href='" + icoUrl + "'/>" +
                "<link rel='bookmark'type='image/x-icon'href='" + icoUrl + "'/>" +
                "<style type='text/css'>*{font-family:'Courier New','Microsoft YaHei',SimSun;word-break:break-all}" +
                "html{cursor:url('https://wuguangnuo-1257896087.cos.ap-guangzhou.myqcloud.com/public/img/default.cur'),default}" +
                "a{color:blue;text-decoration:none}" +
                "a,button{cursor:url('https://wuguangnuo-1257896087.cos.ap-guangzhou.myqcloud.com/public/img/select.cur'),default}" +
                "footer{text-align:center}</style></head><body>" +
                "<h1>" + title + "！</h1>" + detail +
                "<footer><p>Copyright &copy; 2020 <a href='http://www.wuguangnuo.cn'target='_blank'>wuguangnuo.cn</a> All Rights Reserved | Author by WuGN</p>" +
                "<p style='color:#888'>京ICP备20006588号</p></footer></body>" +
                "<script src='https://cdn.bootcss.com/clipboard.js/2.0.6/clipboard.min.js'></script>" +
                "<script>new ClipboardJS('#copybtn');/*禁用选取,键盘,拖动,右键*/" +
                "document.onselectstart=function(){return false};" +
                "document.body.onkeydown=function(){return false};" +
                "document.body.ondragstart=function(){return false};" +
                "document.body.oncontextmenu=function(){return false};" +
                "</script></html>";
    }
}
