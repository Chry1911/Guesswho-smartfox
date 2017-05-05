package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
//import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ExitClanHandler extends BaseClientRequestHandler {
	
	public void handleClientRequest(User user, ISFSObject params){
		
	    trace("Sto chiedendo di eliminare uno user dentro un clan al server");
		
		int userplayer = params.getInt("user_id");
		int clan_id = params.getInt("clan_id");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		Connection connection = null;
		try {
			connection = dbmanager.getConnection();
			//PreparedStatement stmt4 = connection.prepareStatement("Select * from Clan where id_clan = " + clan_id);
			PreparedStatement stmt4 = connection.prepareStatement("select * from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer);
			ResultSet q = stmt4.executeQuery();
			while(q.next()){
				int utente = q.getInt("id_user");
				trace(utente + ": id_user");
				
				
				Statement stmt5;
				SFSObject success = new SFSObject();
				
				if(utente == userplayer){
					String sql = "delete from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}
				else{
					trace("impossibile cancellare lo user");
					ISFSObject error = new SFSObject();
					error.putUtfString("error", "sql problem error");
					send("exitclan", error, user);
					break;
				}
			}
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			trace("vediamo cosa contiene l'array" + error.toString());
			error.putUtfString("error", "MySQL error");
			send("userdetail" , error, user);
			ex.printStackTrace();
	}
		finally {
        	try{
        		connection.close();
        	}catch (SQLException e){
        		trace("A SQL Error occurred: " + e.getMessage());
        	}
        }
	}
}
