package cn.wgn.website.handler;

import cn.wgn.website.dto.ApiRes;
import cn.wgn.website.dto.UserData;
import cn.wgn.website.enums.RedisPrefixKeyEnum;
import cn.wgn.website.utils.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class TokenAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) var1;
        HttpServletResponse response = (HttpServletResponse) var2;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,token");
        response.setHeader("Access-Control-Expose-Headers", "*");

        String methods = request.getMethod();
        if ("OPTIONS".equals(methods)) {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        String token = request.getHeader("token");
        boolean isAuthSuccess = false;

        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        assert ctx != null;
        RedisUtil redisUtil = ctx.getBean(RedisUtil.class);

//        final String url = request.getRequestURI();

        if (Strings.isNullOrEmpty(token) || !redisUtil.exists(token, RedisPrefixKeyEnum.Token.toString())) {
            log.warn("UnAuthorized url: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStream outputStream = response.getOutputStream();

            outputStream.write(JSONObject.toJSONBytes(ApiRes.unAuthorized()));
            outputStream.flush();
            outputStream.close();
        } else {
            String idAndAccount = redisUtil.get(token, RedisPrefixKeyEnum.Token.toString());
            String[] arr = idAndAccount.split(":");

            if (arr.length == 3) {
                UserData userData = new UserData();
                userData.setId(Integer.valueOf(arr[0]));
                userData.setAccount(arr[1]);
                userData.setRoleId(Integer.valueOf(arr[2]));
                request.setAttribute("userData", userData);
                isAuthSuccess = true;
            }
        }

        if (isAuthSuccess) {
            var3.doFilter(var1, var2);
        }
    }

    /**
     * tokenIgnore
     */
//    private static boolean tokenIgnore(String url, String str) {
//        if (url.equals("/swagger-ui.html")) {
//            return true;
//        } else if (str.endsWith("*")) {
//            str = str.substring(0, str.length() - 1);
//            return url.toLowerCase().startsWith(str.toLowerCase());
//        } else {
//            return url.toLowerCase().equals(str.toLowerCase());
//        }
//    }
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("\r\n" +
                "   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗\r\n" +
                " ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝\r\n" +
                " ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗\r\n" +
                " ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║\r\n" +
                " ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝\r\n" +
                "  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝\r\n" +
                "  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░\r\n" +
                "  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░            我变秃了，但没有变强\r\n" +
                "           ░     ░ ░      ░  ░");
    }
}
