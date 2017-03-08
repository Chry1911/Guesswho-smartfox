package clanserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.CreateRoomSettings.RoomExtensionSettings;
import com.smartfoxserver.v2.db.IDBManager;
//import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;





public class ClanRegistrationHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	//private SmartFox sfs;
	
	public void handleClientRequest(User user, ISFSObject params){

		trace("Sto chiedendo di registrare un clan al server");
		
		String clan_name = params.getUtfString("name");
        int user_founder = params.getInt("founder");
        String descrizione = params.getUtfString("descr");
        int min_trophy = params.getInt("trophies");
        int type = params.getInt("type");
        String position = params.getUtfString("location");
        int stemma = params.getInt("symbol");
        int minUsers = params.getInt("minUsers");
        int maxUsers = params.getInt("maxUsers");
       
        
		//String email = params.getUtfString("email");

		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		//sfs = null;
		try {
			trace("sono entrato nel primo try");
			//obj = dbmanager.executeQuery(sql2, new Object[] {1});
			obj = dbmanager.executeQuery("SELECT * FROM Clan WHERE  clan_name=?", new Object[] {clan_name}); 
			
			trace(obj.toString());
			
			SFSArray ar = (SFSArray) obj;
			
			//trace(condition.toString() + "condizione");
			
			if(ar.size() >= 1){
				trace("Errore clan già presente nel sistema");
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "clan già registrato nel database");
				send("clan" , error, user);
				//return;
			}else {
				trace("registriamo il clan nel nostro database");
		//String sql="INSERT into loginprova(name, email) values ('"+name+"','"+email+"')";
		String sql = "INSERT into Clan(clan_name, utente_fondatore, stemma, descrizione, min_trofei, position, tipo, maxUsers, minUsers) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try{
			
		
	          obj = dbmanager.executeInsert(sql,
	                     new Object[] {clan_name, user_founder, stemma, descrizione, min_trophy, position, type, maxUsers, minUsers});
	          
	        ISFSObject success = new SFSObject();
	      	success.putUtfString("success" ,"Clan successfully registrated");
	      	send("clan", success, user);
	      	
	    createRoom(user,params);
		} 
		catch (SQLException e) {
		
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL update error");
		send("clan" , error, user);
	}

			}	} catch (SQLException e1) {
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL error");
				send("clan" , error, user);
			}
	
}
	
	private void createRoom(User sender, ISFSObject params){
		 String clan_name = params.getUtfString("name");
		//Room myfirstroom = getParentExtension().getParentZone().getRoomByName(clan_name);
		RoomExtensionSettings res = new RoomExtensionSettings("Server","clanserver.ClanExtension");
		      CreateRoomSettings crs = new CreateRoomSettings();
		     
		    
		      crs.setName(clan_name);
		      crs.setGroupId("clan_name " + clan_name);
		      crs.setMaxVariablesAllowed(20);
		      crs.setMaxUsers(20);
		      crs.setAutoRemoveMode(SFSRoomRemoveMode.NEVER_REMOVE);
		      crs.setDynamic(true);
		      
		      crs.setExtension(res);
		      
		     
			      
			      ISFSObject reback = SFSObject.newInstance();
			      try {
			         getApi().createRoom(sender.getZone(),crs,sender);
			         reback.putBool("success", true);
			      } catch (SFSCreateRoomException e) {
			         e.printStackTrace();
			         reback.putBool("success", false);
			      }finally{
			         send("createRoom", reback, sender);
			      }
		      }
		      
		    
		   

	
}