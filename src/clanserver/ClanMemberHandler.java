package clanserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ClanMemberHandler extends BaseClientRequestHandler {

	Object obj = null;
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		
		trace("Sto chiedendo al server l'elenco membri del clan");
		//int id_clan = params.getInt("clan_id");
		String clan_name = params.getUtfString("clan_name");
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
		try{
			trace("Ho fatto l'accesso per richiedere al server la mia query");
			ISFSArray arr = 
					dbmanager.executeQuery("Select guesswho.users.username, guesswho.clan.clan_name,  "
							+ "guesswho.users.trofei, guesswho.users.position from guesswho.users "
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
							+ "where id_clan = ?", 
							new Object[] {clan_name});
			
			if (arr.size() > 0)
			{
			  SFSObject result = new SFSObject();
			  result.putSFSArray("success", arr);
			  send("clanmember", result, user);
			}
			
	}catch (SQLException ex) {
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL error");
		send("clanmembers" , error, user);
}}

}
