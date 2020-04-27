package cn.wgn.website.utils;

import cn.wgn.website.dto.utils.IpRegion;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author WuGuangNuo
 * @date Created in 2020/3/3 16:15
 */
@Component
public class IpUtil {
    @Value("${private-config.ip2region}")
    private String ipRegionPath;

    /**
     * 获取IP地址
     *
     * @param request HttpServletRequest
     * @return String
     */
    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "0.0.0.0";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public Integer getIp(HttpServletRequest request) {
        return this.ip2int(this.getIpAddr(request));
    }

    /**
     * ip to int (有负数,同PHP版)
     *
     * @param ip
     * @return
     */
    public Integer ip2int(String ip) {
        int ips = 0;
        String[] numbers = ip.split("[.]");
        for (int i = 0; i < 4; i++) {
            ips = ips << 8 | Integer.parseInt(numbers[i]);
        }
        return ips;
    }

    /**
     * int to ip (有负数,同PHP版)
     *
     * @param number
     * @return
     */
    public String int2ip(Integer number) {
        String ip = "";
        for (int i = 3; i >= 0; i--) {
            ip += String.valueOf((number >> 8 * i & 0xff));
            if (i != 0) {
                ip += ".";
            }
        }
        return ip;
    }

    /**
     * 获取IP地理位置
     *
     * @param ip
     * @return
     */
    public IpRegion getIpRegion(String ip) {
        String block = null;
        try {
            DbConfig dbConfig = new DbConfig();
            DbSearcher searcher = new DbSearcher(dbConfig, ipRegionPath);
            // B树搜索
            block = searcher.btreeSearch(ip).getRegion();
        } catch (DbMakerConfigException | IOException e) {
            e.printStackTrace();
        }

        assert block != null;
        String[] blocks = block.split("[|]");
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = "0".equals(blocks[i]) ? "" : blocks[i];
        }
        IpRegion ipRegion = new IpRegion();
        ipRegion.setCountry(blocks[0]);
        ipRegion.setArea(blocks[1]);
        ipRegion.setProvince(blocks[2]);
        ipRegion.setCity(blocks[3]);
        ipRegion.setIsp(blocks[4]);
        return ipRegion;
    }

    /**
     * 获取IP地理位置
     *
     * @param number
     * @return
     */
    public IpRegion getIpRegion(Integer number) {
        return this.getIpRegion(this.int2ip(number));
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    public String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * 获取本机名
     *
     * @return
     */
    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "未知";
        }
    }
}