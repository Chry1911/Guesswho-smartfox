package registerserver;


import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;


public class UpdatePasswordHandler  extends BaseClientRequestHandler {

	Object obj = null;
	
	@SuppressWarnings("deprecation")
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		// TODO Auto-generated method stub
		trace("Sto chiedendo al server di cambiare la password di uno user");
		
		String email = params.getUtfString("email");
		String password = params.getUtfString("password");
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		try {
			trace("sono entrato nel primo try");
			//obj = dbmanager.executeQuery(sql2, new Object[] {1});
			ISFSArray ar = dbmanager.executeQuery("SELECT id_user FROM Users WHERE  email=? ", new Object[] {email}); 
			
			ISFSObject ob = ar.getSFSObject(0);
			
			
		//	SFSArray ar = (SFSArray) obj;
			
		
			
			if(ar.size() > 1){
				
				trace("Errore secco");
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "impossibile reperire lo user richiesto");
				send("updatepassword" , error, user);
				//return;
			}else {
				trace("cambiamo il codice captcha dello user");
		
				int id_user=ob.getInt("id_user");
					trace("stampiamo l'id user " + id_user);
				
		
		
		
		try{
			trace("sono entrato nel secondo try");
		
	          //obj = dbmanager.executeUpdate("update guesswho.users set captcha = ? where email = ? and id_user = ?",new Object[] {captcha, email, id_user});
			String sql = "update guesswho.users set password = '" + password + "' where email = '" + email + "' and id_user = " + id_user;
		
	         dbmanager.executeUpdate(sql);
	          ISFSObject success = new SFSObject();
	      	success.putUtfString("success" ,"Codice captcha cambiato si può cambiare la password");
	      	send("updatepassword", success, user);
		} 
		
catch(SQLException e) {
	
	ISFSObject error = new SFSObject();
	error.putUtfString("error", "MySQL update error");
	send("updatepassword" , error, user);



}
			}	
			} catch (SQLException e1) {
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL error");
				send("updatepassword" , error, user);
			}
			
	
}}