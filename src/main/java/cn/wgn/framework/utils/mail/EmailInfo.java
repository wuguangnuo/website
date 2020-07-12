package cn.wgn.framework.utils.mail;

/**
 * 邮件信息 Model
 *
 * @author WuGuangNuo
 */
public class EmailInfo {
    // 收件人
    private String toUser;
    // 邮件主题
    private String subject;
    // 邮件正文
    private String content;

    public EmailInfo(String toUser, String subject, String content) {
        this.toUser = toUser;
        this.subject = subject;
        this.content = content;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EmailInfo{" +
                "toUser='" + toUser + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}