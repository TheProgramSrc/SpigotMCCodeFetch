package xyz.theprogramsrc;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SpigotMCCodeFetch {

    /**
     * Fetch the 2FA Code from your email
     *
     * @param host Mail POP3 Host (GMail: pop.gmail.com)
     * @param port Mail POP3 Port (GMail: 995)
     * @param user Mail address
     * @param password Mail password
     * @return last code received
     */
    public static String getSpigot2FACode(String host, String port, String user, String password) {
        try{
            Properties properties = System.getProperties();
            properties.setProperty("mail.pop3.host", host);
            properties.setProperty("mail.pop3.port", port);
            properties.setProperty("mail.pop3.auth", "true");
            properties.setProperty("mail.pop3.starttls.enable", "true");
            properties.setProperty("mail.pop3.starttls.required.enable", "true");
            properties.setProperty("mail.pop3.ssl.enable", "true");
            properties.setProperty("mail.pop3.ssl.trust", "*");
            Session session = Session.getDefaultInstance(properties);

            Store pop3Store = session.getStore("pop3s");
            pop3Store.connect(host, user, password);

            Folder inbox = pop3Store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);

            for (Message message : inbox.getMessages()) {
                if (message.getSubject().equals("SpigotMC - High Performance Minecraft: Login Verification")) {
                    MimeMultipart mimeMultipart = ((MimeMultipart) message.getContent());
                    for (int x = 0; x < mimeMultipart.getCount(); ++x) {
                        BodyPart bodyPart = mimeMultipart.getBodyPart(x);
                        String content = ((String) bodyPart.getContent());
                        if (content.contains("<h2 style=\"font-size: 18pt; font-weight: normal; margin: 10px 0\">")) {
                            for (String s : content.split("\n")) {
                                if (s.contains("<h2 style=\"font-size: 18pt; font-weight: normal; margin: 10px 0\">")) {
                                    s = s.replace("<h2 style=\"font-size: 18pt; font-weight: normal; margin: 10px 0\">", "");
                                    s = s.replace("</h2>", "");
                                    return s;
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
