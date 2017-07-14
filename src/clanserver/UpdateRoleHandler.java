package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class UpdateRoleHandler extends BaseClientRequestHandler{
	
	public void handleClientRequest(User user, ISFSObject params){
		 trace("Sto chiedendo al server di aggiornare il ruolo di uno user del clan ");
			
			int userplayer = params.getInt("user_id");
			int clan_id = params.getInt("clan_id");
			String role = params.getUtfString("role");
			
			IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
			Connection connection = null;
			try {
				connection = dbmanager.getConnection();
				String update = "Update clan_users set ruolo = '" + role + "' where id_user = " + userplayer + " and id_clan = " + clan_id;
				PreparedStatement stmt4 = connection.prepareStatement(update);
				stmt4.executeUpdate();
				
				String ssql3 = "Insert into chat_general(id_user, id_clan, message, datamex) Values (1, " + clan_id + ", 'Hai aggiornato il ruolo dell'utente : " + userplayer + "', Now())";
				stmt4 = connection.prepareStatement(ssql3);
				stmt4.executeUpdate();
				
				ISFSObject success = new SFSObject();
				success.putUtfString("success", "aggiornato il ruolo dello user");
				
				send("updaterole", success, user);
				
	}catch (SQLException ex) {
		ISFSObject error = new SFSObject();
		trace("vediamo cosa contiene l'array" + error.toString());
		error.putUtfString("error", "MySQL error");
		send("updaterole" , error, user);
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
