package clanserver;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ClanMemberHandler extends BaseClientRequestHandler {

	Object obj = null;
	private Connection connection;
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		
		trace("Sto chiedendo al server l'elenco membri del clan");
		//int id_clan = params.getInt("clan_id");
		String clan_name = params.getUtfString("clan_name");
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		connection = null;
		try{
			trace("Ho fatto l'accesso per richiedere al server la mia query");
			connection = dbmanager.getConnection();
			ISFSArray arr = 
					dbmanager.executeQuery("Select guesswho.users.username, guesswho.clan.clan_name,  "
							+ "guesswho.users.trofei, guesswho.users.position from guesswho.users "	
							+ "INNER JOIN CLAN_USERS ON CLAN_USERS.ID_USER = USERS.ID_USER "
                            + "INNER JOIN CLAN ON CLAN.ID_CLAN = CLAN_USERS.ID_CLAN "
							+ "where clan_name = ? ", 
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
