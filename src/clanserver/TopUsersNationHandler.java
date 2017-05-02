package clanserver;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class TopUsersNationHandler extends BaseClientRequestHandler{


	Object obj = null;
	private Connection connection;
	public void handleClientRequest(User user, ISFSObject params) {
	
		trace("Sto richiedendo al server i 100 migliori utenti per nazione");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		String nation = params.getUtfString("nation");
		connection = null;
		try{
			trace("Ho fatto l'accesso per richiedere al server la mia query");
			connection = dbmanager.getConnection();
			//obj = dbmanager.executeQuery("SELECT * FROM guesswho.Clan Limit 100 ", new Object[] {}); 
			ISFSArray arr = dbmanager.executeQuery("SELECT guesswho.users.id_user, guesswho.users.username, "
					+ "guesswho.users.trofei, "
					+ "guesswho.users.position, "
					+ "guesswho.clan.clan_name FROM guesswho.Users "
					+ "left outer join guesswho.clan on guesswho.users.id_user = guesswho.clan.utente_fondatore  "
					+ "or guesswho.users.id_user = guesswho.clan.utente_2 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_3 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_4 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_5 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_6 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_7 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_8 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_9 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_10 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_11 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_12 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_13 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_14 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_15 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_16 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_17 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_18 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_19 "
					+ "or guesswho.users.id_user = guesswho.clan.utente_20 "
					+ "where guesswho.users.position = ? group by guesswho.users.username "
					+ "order by trofei desc limit 100 ", 
					new Object[] {nation});
			if (arr.size() > 0)
			{
			  SFSObject result = new SFSObject();
			  result.putSFSArray("success", arr);
			  send("topusersnation", result, user);
			}
			
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			error.putUtfString("error", "MySQL error");
			send("topusersnation" , error, user);
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