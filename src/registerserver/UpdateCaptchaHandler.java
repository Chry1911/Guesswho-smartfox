package registerserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class UpdateCaptchaHandler  extends BaseClientRequestHandler {

	Object obj = null;
	
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		// TODO Auto-generated method stub
		trace("Sto chiedendo al server di cambiare il captcha dello user");
		
		String email = params.getUtfString("email");
		String captcha = params.getUtfString("captcha");
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		try{
			trace("sono entrato nel try");
		
	          obj = dbmanager.executeQuery("update guesswho.users set password = ? where email = ?",
	                     new Object[] {captcha, email});
	          
	          ISFSObject success = new SFSObject();
	      	success.putUtfString("success" ,"Codice captcha cambiato si può cambiare la password");
	      	send("updatecaptcha", success, user);
		} 
		
catch(SQLException e) {
	
	ISFSObject error = new SFSObject();
	error.putUtfString("error", "MySQL update error");
	send("updatecaptcha" , error, user);



}
	}
}
