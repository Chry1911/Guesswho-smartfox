package registerserver;

import java.util.*;

import javax.mail.*;

import javax.mail.internet.*;


public class SendEmail {
  private String host = "smtp.googlemail.com"; //tuo smtp
  private String from = "lavagnacondivisa@gmail.com"; //tuo indirizzo email
  private String ToAddress; //destinatario
  private String user = "lavagnacondivisa";
  private String pass = "lavagna2016";
  private MimeMessage msg = null;
  private Session session = null;
  
  public SendEmail(String toAddress) {
	  ToAddress = toAddress;
    try {      
      //Get system properties
      Properties props = System.getProperties( );
    
      //Setup mail server
      props.put("mail.smtp.host", host);
      props.put("mail.debug", "true");
      props.put("mail.smtp.starttls.enable","true");
      props.put("mail.smtp.auth","true");

      //Get session
      session = Session.getDefaultInstance(props, null);
      session.setDebug(true);
      session.setPasswordAuthentication(new URLName("smtp",host,25,"INBOX",user,pass), new PasswordAuthentication(user,pass));

      //Define message
      msg = new MimeMessage(session);
      //Set the from address
      msg.setFrom(new InternetAddress(from));
      //Set the to address
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(ToAddress));
    }
    catch (MessagingException e) {
      System.out.println(e);
    }
  } 
  
  public void send() {
	//Send message
      Transport tr;
	try {
		tr = session.getTransport("smtp");
		tr.connect(host, user, pass);
		msg.saveChanges();
		tr.sendMessage(msg, msg.getAllRecipients());
		tr.close();
	} catch (NoSuchProviderException e) {
		e.printStackTrace();
	}catch (MessagingException e) {
		e.printStackTrace();
	}
  }
  
  public void setText(String txt) {
	 try {
		msg.setText(txt);
	} catch (MessagingException e) {
		e.printStackTrace();
	}
  }
  
  public void setSubject(String s) {
	  try {
		msg.setSubject(s);
	} catch (MessagingException e) {
		e.printStackTrace();
	}
  }
  
  public static void main(String[] args) {
    String toAddress = "christian.cortese.cc@gmail.com";
    SendEmail se = new SendEmail(toAddress);
    String rand = new RandomString().toString();
    se.setText(rand);
    se.setSubject("Prova");
    se.send();
  }
  


}
