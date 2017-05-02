package clanserver;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class UserDetailHandler extends BaseClientRequestHandler{


	Object obj = null;
	private Connection connection;
	public void handleClientRequest(User user, ISFSObject params) {
		int id_user = params.getInt("user_id");
		//String username = params.getUtfString("username");
		trace("Sto richiedendo al server i dettagli di uno user");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		connection = null;
		try{
			trace("Ho fatto l'accesso per richiedere al server la mia query");
			connection = dbmanager.getConnection();
			ISFSArray arr = dbmanager.executeQuery("select * from guesswho.users "
					+ "where id_user = ? "
					, new Object[] {id_user});
			if (arr.size() > 0)
			{
			  SFSObject result = new SFSObject();
			  result.putSFSArray("success", arr);
			  send("userdetail", result, user);
			}
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			trace("vediamo cosa contiene l'array" + error.toString());
			error.putUtfString("error", "MySQL error");
			send("userdetail" , error, user);
			ex.printStackTrace();
	}
		finally{
			try{
				connection.close();
				
			}catch (SQLException e){
        		trace("A SQL Error occurred: " + e.getMessage());
        	}
        
		}
		}
}
