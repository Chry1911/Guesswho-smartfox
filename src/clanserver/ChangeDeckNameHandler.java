package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ChangeDeckNameHandler  extends BaseClientRequestHandler{
	
	public void handleClientRequest(User user, ISFSObject params){

		trace("sto chiedendo al server di cambiare il nome del deck di un utente");
		
		int userplayer = params.getInt("user_id");
		int slot = params.getInt("slot_id");
		String deckname = params.getUtfString("deck_name");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		Connection connection = null;
		try {
			connection = dbmanager.getConnection();
			String update = "Update decks set deck_name = '" + deckname + "' where id_user = " + userplayer + " and id_slot = " + slot;
			PreparedStatement stmt4 = connection.prepareStatement(update);
			stmt4.executeUpdate();
			ISFSObject success = new SFSObject();
			success.putUtfString("success", "aggiornato il nome del deck dello user");
			
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
