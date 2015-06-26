package au.aurin.org.io;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class classMail {
  private JavaMailSenderImpl mailSender;

  String host;
  String port;
  Boolean starttls;
  Boolean auth;
  String username;
  String password;
  String from;
  String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getHost() {
    return host;
  }

  public void setHost(final String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(final String port) {
    this.port = port;
  }

  public Boolean getStarttls() {
    return starttls;
  }

  public void setStarttls(final Boolean starttls) {
    this.starttls = starttls;
  }

  public Boolean getAuth() {
    return auth;
  }

  public void setAuth(final Boolean auth) {
    this.auth = auth;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(final String from) {
    this.from = from;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public void setMailSender(final JavaMailSenderImpl mailSender) {
    this.mailSender = mailSender;
  }

  public void sendMail(final String from, final String to,
      final String subject, final String msg) throws MessagingException {

    // final SimpleMailMessage message = new SimpleMailMessage();
    // message.setFrom(from);
    // message.setTo(to);
    // message.setSubject(subject);
    // message.setText(msg);
    // mailSender.send(message);

    // final JavaMailSenderImpl sender = new JavaMailSenderImpl();
    mailSender = new JavaMailSenderImpl();
    final MimeMessage mimeMessage = mailSender.createMimeMessage();
    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false,
        "utf-8");
    final String htmlMsg = msg;
    mimeMessage.setContent(htmlMsg, "text/html");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setFrom(from);
    mailSender.send(mimeMessage);

  }
}
