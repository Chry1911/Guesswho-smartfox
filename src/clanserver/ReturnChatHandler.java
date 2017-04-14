package clanserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ReturnChatHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	//private SmartFox sfs;
	
	public void handleClientRequest(User user, ISFSObject params){
	
		
		trace("Sto chiedendo al server di restutire le chat di un clan");
		
		String clan_name = params.getUtfString("clan_name");
		
		String sql = "Select " + clan_name +"_chat.*, users.username from " + clan_name + "_chat "
				+ "inner join guesswho.users on guesswho.users.id_user = guesswho." + clan_name + "_chat.id_user "
				+ "order by ID asc limit 100 ";
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
		try{

			ISFSArray arr =   dbmanager.executeQuery(sql,
	                     new Object[] {});
			
			if (arr.size() > 0)
			{
	          
				SFSObject result = new SFSObject();
				  result.putSFSArray("success", arr);
				  send("returnchat", result, user);
			}
	}catch (SQLException e) {
		
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL update error");
		send("returnchat" , error, user);
		e.printStackTrace();
		trace(e.toString());
	}

}}
