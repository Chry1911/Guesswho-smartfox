package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ExpulsionUserHandler extends BaseClientRequestHandler{

	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		trace("Sto chiedendo al server di espellere uno user dal mio clan");
		
		int userplayer = params.getInt("user_id");
		int clan_id = params.getInt("clan_id");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		Connection connection = null;
		try {
			connection = dbmanager.getConnection();
			Statement stmt5;
		
			String sql = "delete from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer;
			trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
			stmt5 = connection.createStatement();
			stmt5.executeUpdate(sql);
			trace("utente eliminato dal clan");
			
			String ssql3 = "Insert into chat_general(id_user, id_clan, message, datamex, type_not) "
					+ "Values (1, " + clan_id + ", 'E stato cacciato l utente " + userplayer + "', Now(), 10)";
			
			PreparedStatement stmt4;
			stmt4 = connection.prepareStatement(ssql3);
			
			stmt4.executeUpdate();
			
			SFSObject success = new SFSObject();
			success.putUtfString("success", "utente eliminato dal clan");
			
			send("expulsionmember", success, user);
			
			
			
		}	catch (SQLException e)
	        {
			        	ISFSObject error = new SFSObject();
						trace("vediamo cosa contiene l'array" + error.toString());
						error.putUtfString("error", "MySQL error");
						send("expulsionmember" , error, user);
						e.printStackTrace();
			        	
			    		
			        }
			        finally {
			        	try{
			        		connection.close();
			        	}catch (SQLException e){
			        		e.printStackTrace();
			        		trace(e.toString());
			        	}
			        }
	
	}
}
