package clanserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class TopUsersHandler extends BaseClientRequestHandler{


	Object obj = null;
	public void handleClientRequest(User user, ISFSObject params) {
	
		trace("Sto richiedendo al server i 100 migliori utenti");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		String nation = params.getUtfString("nation");
		try{
			trace("Ho fatto l'accesso per richiedere al server la mia query");
			
			ISFSArray arr = dbmanager.executeQuery("SELECT guesswho.users.id_user, "
					+ "guesswho.users.username, guesswho.users.trofei, guesswho.users.position, "

					+ "guesswho.clan.clan_name, guesswho.clan.stemma FROM guesswho.users "
					+ "LEFT JOIN clan_users ON clan_users.id_user = users.id_user "
                    + "LEFT JOIN clan ON clan.id_clan = clan_users.id_clan "

					+ "guesswho.clan.clan_name, guesswho.clan.stemma, guesswho.role.* FROM guesswho.Users "
					+ "LEFT JOIN CLAN_USERS ON CLAN_USERS.ID_USER = USERS.ID_USER "
                    + "LEFT JOIN CLAN ON CLAN.ID_CLAN = CLAN_USERS.ID_CLAN "
					+ "LEFT JOIN ROLE ON ROLE.ID_ROLE = CLAN_USERS.RUOLO "

					+ "where guesswho.users.position Like ? "
					+ "order by trofei desc limit 100", new Object[] {"%"+ nation + "%"});
			
			if (arr.size() > 0)
			{
			  SFSObject result = new SFSObject();
			  result.putSFSArray("success", arr);
			  send("topusers", result, user);
			}else{
				 SFSObject result2 = new SFSObject();
				  result2.putUtfString("nosuccess", "Nessuna corrispondenza trovata");
				  send("topusers", result2, user);
			}
			
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			error.putUtfString("error", "MySQL error");
			send("topusers" , error, user);
	}
		}

}
