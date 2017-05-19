package clanserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ChatRegistrationHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	Date date;
	private Connection connection;
	public void handleClientRequest(User user, ISFSObject params){
		trace("Sto chiedendo al server di registrare la chat utenti clan");
		
		//String clan_name = params.getUtfString("clan_name");
		int id_clan = params.getInt("clan");
		int id_user = params.getInt("user");
		String message = params.getUtfString("message");
		//String datetime = params.getUtfString("date");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		connection = null;
			
			//String sql = "Insert into " + clan_name + "_chat(id_user, message,datamex) values (?,?,?)";
		
		     String sql = "Insert into chat_general(id_user, id_clan,message,datamex) values(?,?,?,?)";
			try {
				 //DateFormat readFormat = new SimpleDateFormat( "DD/mm/yyyy");

				   // DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				connection = dbmanager.getConnection();  
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = new Date();
				dateFormat.format(date); //2016/11/16 12:08:43
				       
				    
				trace("sono entrato nel primo try");
				 obj = dbmanager.executeInsert(sql,
	                     new Object[] {id_user ,id_clan ,message, dateFormat.format(date)});
				 
				 ISFSObject success = new SFSObject();
			      	success.putUtfString("success" ,"Message storage");
			      	send("savingchat", success, user);
		}catch (SQLException e) {
			
			ISFSObject error = new SFSObject();
			error.putUtfString("error", "MySQL update error");
			send("savingchat" , error, user);
			e.printStackTrace();
			trace(e.toString());
	}finally{
		try{
			connection.close();
		}catch (SQLException e){
    		trace("A SQL Error occurred: " + e.getMessage());
    	}
    }
	}
}
