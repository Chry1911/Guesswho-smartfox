package registerserver;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;

import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class UpdateCaptchaHandler  extends BaseClientRequestHandler {

	Object obj = null;
	
	Connection connection = null;
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		// TODO Auto-generated method stub
		trace("Sto chiedendo al server di cambiare il captcha dello user");
		
		String email = params.getUtfString("email");
		String captcha = params.getUtfString("captcha");
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
		
		try {
			connection = dbmanager.getConnection();
			trace("sono entrato nel primo try");
			//obj = dbmanager.executeQuery(sql2, new Object[] {1});
			ISFSArray ar = dbmanager.executeQuery("SELECT captcha FROM users WHERE  email=? ", new Object[] {email}); 
			
			ISFSObject ob = ar.getSFSObject(0);
			
			
			if(ar.size() > 1){
				
				trace("Errore secco");
				ISFSObject error = new SFSObject();
				error.putUtfString("nosuccess", "impossibile reperire lo user richiesto");
				send("updatecaptcha" , error, user);
				//return;
			}else {
				trace("cambiamo il codice captcha dello user");
		
				String code=ob.getUtfString("captcha");
					trace("stampiamo il codice " + code);
					
					
					trace("stampiamo il captcha da client " + captcha);
				
		if(captcha.equals(code)){
			trace("Codice identico");
			ISFSObject success = new SFSObject();
			success.putUtfString("success", "codice di verifica identico");
			send("updatecaptcha" , success, user);
		}else if(captcha != code){
			trace("Codice sbagliato");
			ISFSObject nosuccess = new SFSObject();
			nosuccess.putUtfString("nosuccess", "codice di verifica diverso");
			send("updatecaptcha" , nosuccess, user);
		}
		
		
		}
			} catch (SQLException e1) {
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL error");
				send("updatecaptcha" , error, user);
			}
		
		finally{
			try{
        		connection.close();
        	}catch (SQLException e){
        		e.getMessage();
        	}
		}
			
	
}
	
}
