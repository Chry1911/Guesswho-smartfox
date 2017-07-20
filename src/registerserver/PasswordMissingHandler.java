package registerserver;


import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;

import com.smartfoxserver.v2.SmartFoxServer;
import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.Email;
import com.smartfoxserver.v2.entities.SFSEmail;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class PasswordMissingHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	SendEmail se = null;
	@SuppressWarnings("deprecation")
	public void handleClientRequest(User user, ISFSObject params){
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		String email = params.getUtfString("email");
		trace("Sto chiedendo di recuperare la password al server");
		Connection connection = null;
	    RandomString captcha = new RandomString();
	    String code = captcha.toString();
		//se = new SendEmail(email);
		Email myEmail = new SFSEmail("lavagnacondivisa@gmail.com", email, "Activation Code",
				"Hello from GuessWho admin /n here your code to change password " + code);
		
		try {
			connection = dbmanager.getConnection();
			trace("sono entrato nel primo try");
			//obj = dbmanager.executeQuery(sql2, new Object[] {1});
			ISFSArray ar = dbmanager.executeQuery("SELECT id_user FROM users WHERE  email=? ", new Object[] {email}); 
			
			ISFSObject ob = ar.getSFSObject(0);
			
			
		//	SFSArray ar = (SFSArray) obj;
			
		
			
			if(ar.size() > 1){
				
				trace("Errore secco");
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "impossibile reperire lo user richiesto");
				send("passwordmissing" , error, user);
				//return;
			}else {
				trace("cambiamo il codice captcha dello user");
		
				int id_user=ob.getInt("id_user");
					trace("stampiamo l'id user " + id_user);
				
		
		
		
		
		
		try {
			trace("sono entrato nel secondo try");
			SmartFoxServer.getInstance().getMailService().sendMail(myEmail);
			String sql = "update guesswho.users set captcha = '" + code + "' where email = '" + email + "' and id_user = " + id_user;
			dbmanager.executeUpdate(sql);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	} catch (SQLException e1) {
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL error");
		send("passwordmissing" , error, user);
	}
		
		finally{
			try{
        		connection.close();
        	}catch (SQLException e){
        		 e.getMessage();
        	}
		}
	
}}
