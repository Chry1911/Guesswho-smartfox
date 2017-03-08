package registerserver;


import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;


public class UpdatePasswordHandler  extends BaseClientRequestHandler {

	Object obj = null;
	
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		// TODO Auto-generated method stub
		trace("Sto chiedendo al server di cambiare la password di uno user");
		
		String email = params.getUtfString("email");
		String password = params.getUtfString("password");
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		try{
			trace("sono entrato nel try");
		
	          obj = dbmanager.executeInsert("update guesswho.users set password = ? where email = ?",
	                     new Object[] {password, email});
	          
	          ISFSObject success = new SFSObject();
	      	success.putUtfString("success" ,"Password dell'utente cambiata correttamente");
	      	send("updatepassword", success, user);
		} 
		
catch(SQLException e) {
	
	ISFSObject error = new SFSObject();
	error.putUtfString("error", "MySQL update error");
	send("updatepassword" , error, user);



}
	}

}
