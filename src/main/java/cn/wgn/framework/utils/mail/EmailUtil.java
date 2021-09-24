package cn.wgn.framework.utils.mail;

import cn.wgn.framework.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件工具
 *
 * @author WuGuangNuo
 */
@Component
public class EmailUtil {

    private static String sslFactory;
    private static String smtpServer;
    private static String port;
    private static String fromUserName;
    private static String fromUserPassword;
    private static String copyTo;

    @Value("${private-config.mail.ssl-factory}")
    public void setSslFactory(String sslFactory) {
        EmailUtil.sslFactory = sslFactory;
    }

    @Value("${private-config.mail.smtp-server}")
    public void setSmtpServer(String smtpServer) {
        EmailUtil.smtpServer = smtpServer;
    }

    @Value("${private-config.mail.port}")
    public void setPort(String port) {
        EmailUtil.port = port;
    }

    @Value("${private-config.mail.from-user-name}")
    public void setFromUserName(String fromUserName) {
        EmailUtil.fromUserName = fromUserName;
    }

    @Value("${private-config.mail.from-user-password}")
    public void setFromUserPassword(String fromUserPassword) {
        EmailUtil.fromUserPassword = fromUserPassword;
    }

    @Value("${private-config.mail.copy-to}")
    public void setCopyTo(String copyTo) {
        EmailUtil.copyTo = copyTo;
    }

    public static String getSslFactory() {
        return sslFactory;
    }

    public static String getSmtpServer() {
        return smtpServer;
    }

    public static String getPort() {
        return port;
    }

    public static String getFromUserName() {
        return fromUserName;
    }

    public static String getFromUserPassword() {
        return fromUserPassword;
    }

    public static String getCopyTo() {
        return copyTo;
    }

    /**
     * 进行base64加密，防止中文乱码
     */
    private static String changeEncode(String str) {
        try {
            str = MimeUtility.encodeText(new String(str.getBytes(), StandardCharsets.UTF_8), "UTF-8", "B"); // "B"代表Base64
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 发送邮件,多个群发
     *
     * @param emailInfo 邮件信息
     * @return Success: true; Error: false
     */
    public static boolean sendHtmlMail(EmailInfo emailInfo) {
        return sendHtmlMail(emailInfo, true);
    }

    /**
     * 发送邮件,多个群发
     *
     * @param emailInfo 邮件信息
     * @param isCopyTo  允许抄送人
     * @return Success: true; Error: false
     */
    public static boolean sendHtmlMail(EmailInfo emailInfo, boolean isCopyTo) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpServer);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", sslFactory); // 使用JSSE的SSL
        properties.put("mail.smtp.socketFactory.fallback", "false"); // 只处理SSL的连接,对于非SSL的连接不做处理
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.socketFactory.port", port);
        Session session = Session.getInstance(properties);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);

        try {
            // 发件人
            Address address = new InternetAddress(fromUserName);
            message.setFrom(address);

            // 群发或单发，逗号分隔
            if (emailInfo.getToUser().contains(",")) {
                String[] toUsers = emailInfo.getToUser().split(",");
                Address[] toAddress = new Address[toUsers.length];
                for (int i = 0; i < toUsers.length; i++) {
                    toAddress[i] = new InternetAddress(toUsers[i]);
                }
                message.setRecipients(MimeMessage.RecipientType.TO, toAddress);
            } else {
                Address toAddress = new InternetAddress(emailInfo.getToUser());
                message.setRecipient(MimeMessage.RecipientType.TO, toAddress); // 设置收件人,并设置其接收类型为TO
            }

            if (StringUtils.isNotEmpty(copyTo) && isCopyTo) {
                // 抄送人
                Address ccAddress = new InternetAddress(copyTo);
                message.setRecipient(MimeMessage.RecipientType.CC, ccAddress); // 设置抄送人,并设置其接收类型为CC
            }

            // 主题message.setSubject(changeEncode(emailInfo.getSubject()));
            message.setSubject(emailInfo.getSubject());
            // 时间
            message.setSentDate(new Date());

            Multipart multipart = new MimeMultipart();

            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(emailInfo.getContent(), "text/html; charset=utf-8");
            multipart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            message.setContent(multipart);
            message.saveChanges();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(smtpServer, fromUserName, fromUserPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 发送邮件,多个单独发
     *
     * @param emailInfo 邮件信息
     * @return Success: true; Error: false
     */
    public static boolean sendHtmlMailSeparately(EmailInfo emailInfo) {
        if (emailInfo == null || StringUtil.isEmpty(emailInfo.getToUser())) {
            return false;
        }
        String toUser = emailInfo.getToUser();
        if (!toUser.contains(",")) {
            return sendHtmlMail(emailInfo, false);
        }
        boolean flag = true;
        List<String> users = Arrays.asList(toUser.split(","));
        if (StringUtils.isNotBlank(copyTo) && !users.contains(copyTo)) {
            users.add(copyTo);
        }

        for (String user : users) {
            emailInfo.setToUser(user);
            if (!sendHtmlMail(emailInfo, false)) {
                flag = false;
            }
        }
        return flag;
    }
}