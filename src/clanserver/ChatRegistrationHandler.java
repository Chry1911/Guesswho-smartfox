package clanserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ChatRegistrationHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	
	public void handleClientRequest(User user, ISFSObject params){
		trace("Sto chiedendo al server di registrare la chat utenti clan");
		
		String clan_name = params.getUtfString("clan_name");
		int id_user = params.getInt("user");
		String message = params.getUtfString("message");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
			
			String sql = "Insert into " + clan_name + "_chat(id_user, message) values (?,?)";
			try {
				trace("sono entrato nel primo try");
				 obj = dbmanager.executeInsert(sql,
	                     new Object[] {id_user, message});
				 
				 ISFSObject success = new SFSObject();
			      	success.putUtfString("success" ,"Message storage");
			      	send("savingchat", success, user);
		}catch (SQLException e) {
			
			ISFSObject error = new SFSObject();
			error.putUtfString("error", "MySQL update error");
			send("savingchat" , error, user);
			e.printStackTrace();
			trace(e.toString());
	}
	}
}
