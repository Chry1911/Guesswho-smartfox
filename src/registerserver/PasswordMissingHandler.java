package registerserver;


import javax.mail.MessagingException;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.entities.Email;
import com.smartfoxserver.v2.entities.SFSEmail;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class PasswordMissingHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	SendEmail se = null;
	public void handleClientRequest(User user, ISFSObject params){
		String email = params.getUtfString("email");
		trace("Sto chiedendo di recuperare la password al server");
		
	    RandomString captcha = new RandomString();
	    String code = captcha.toString();
		//se = new SendEmail(email);
		Email myEmail = new SFSEmail("lavagnacondivisa@gmail.com", email, "Activation Code",
				"Hello from GuessWho admin /n here your code to change password " + code);
		try {
			SmartFoxServer.getInstance().getMailService().sendMail(myEmail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
