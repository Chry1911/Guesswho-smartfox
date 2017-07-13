package clanserver;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ReturnDeckHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	
	private Connection connection;
	
	public void handleClientRequest(User user, ISFSObject params){
	
		
		trace("Sto chiedendo al server di restutirmi il deck dell'utente");
		
        int id_user = params.getInt("iduser");
		
		String sql = "select * from decks where id_user = " + id_user;
				
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		connection = null;
		try{

			connection = dbmanager.getConnection();
			ISFSArray arr =   dbmanager.executeQuery(sql,
	                     new Object[] {});
			
			if (arr.size() > 0)
			{
				
				for(int i = 0; i < arr.size(); i++) {
			    int selected = arr.getSFSObject(0).getInt("selected_deck");
				SFSObject result = new SFSObject();
				result.putSFSArray("success", arr);
				result.putInt("selecteddeck", selected);
				send("returndeck", result, user);
				
				  
				}
			}else {
				SFSObject result = new SFSObject();
				result.putSFSArray("nosuccess", arr);
				send("returndeck", result, user);
			}
	}catch (SQLException e) {
		
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL update error");
		send("returndeck" , error, user);
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
