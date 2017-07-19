package clanserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ReturnChatHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	
	private Connection connection;
	
	public void handleClientRequest(User user, ISFSObject params){
	
		
		trace("Sto chiedendo al server di restutire le chat di un clan");
		
		int id_clan = params.getInt("clan");
		
		String sql = "select chat_general.*, users.username, clan_users.ruolo, role.description from chat_general "
				+ "left join guesswho.users on guesswho.users.id_user = guesswho.chat_general.id_user  "
				+ "left join clan_users on clan_users.id_user = guesswho.chat_general.id_user "
				+ "LEFT JOIN ROLE ON ROLE.ID_ROLE = CLAN_USERS.RUOLO "
				+ "where guesswho.chat_general.id_clan = " + id_clan + " limit 100";
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		connection = null;
		try{

			connection = dbmanager.getConnection();
			ISFSArray arr =   dbmanager.executeQuery(sql,
	                     new Object[] {});
			
			if (arr.size() > 0)
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				dateFormat.format(date);
				
				long data = date.getTime();
				
				SFSObject result = new SFSObject();
				result.putSFSArray("success", arr);
				result.putLong("dataodierna", data);
				send("returnchat", result, user);
				  

			}else {
				SFSObject result = new SFSObject();
				result.putSFSArray("nosuccess", arr);
				send("returnchat", result, user);
			}
	}catch (SQLException e) {
		
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL update error");
		send("returnchat" , error, user);
		e.printStackTrace();
		trace(e.toString());
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
