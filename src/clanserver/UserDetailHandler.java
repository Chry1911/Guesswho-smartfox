package clanserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class UserDetailHandler extends BaseClientRequestHandler{


	Object obj = null;
	public void handleClientRequest(User user, ISFSObject params) {
		//int id_user = params.getInt("user_id");
		String username = params.getUtfString("clan_name");
		trace("Sto richiedendo al server i dettagli di uno user");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
		try{
			trace("Ho fatto l'accesso per richiedere al server la mia query");
			//obj = dbmanager.executeQuery("SELECT * FROM guesswho.Clan Limit 100 ", new Object[] {}); 
			ISFSArray arr = dbmanager.executeQuery("SELECT guesswho.users.* From guesswho.users where id_user = ?",
					new Object[] {username});
			if (arr.size() > 0)
			{
			  SFSObject result = new SFSObject();
			  result.putSFSArray("success", arr);
			  send("userdetail", result, user);
			}
			
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			error.putUtfString("error", "MySQL error");
			send("userdetail" , error, user);
	}
		}
}
