package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;

import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.CreateRoomSettings.RoomExtensionSettings;
import com.smartfoxserver.v2.db.IDBManager;
//import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;





public class ClanRegistrationHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	
	private Connection connection;
	
	public void handleClientRequest(User user, ISFSObject params){

		trace("Sto chiedendo di registrare un clan al server");
		
		String clan_name = params.getUtfString("name");
        int user_founder = params.getInt("founder");
        String descrizione = params.getUtfString("descr");
        int min_trophy = params.getInt("trophies");
        String type = params.getUtfString("type");
        String position = params.getUtfString("location");
        int stemma = params.getInt("symbol");
        int minUsers = params.getInt("minUsers");
        int maxUsers = params.getInt("maxUsers");
       
        
		

		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
		connection = null;
		try {
			trace("sono entrato nel primo try");
			connection = dbmanager.getConnection();
			
			obj = dbmanager.executeQuery("SELECT * FROM clan WHERE  clan_name=?", new Object[] {clan_name}); 
			
			trace(obj.toString());
			
			SFSArray ar = (SFSArray) obj;
			
			
			
			if(ar.size() >= 1){
				trace("Errore clan gi� presente nel sistema");
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "clan gi� registrato nel database");
				send("clan" , error, user);
				
			}else {
				trace("registriamo il clan nel nostro database");
		
		String sql = "INSERT into clan(clan_name, stemma, descrizione, min_trofei, position, tipo, maxUsers, minUsers) values (?, ?, ?, ?, ?, ?, ?, ?)";

		try{
			
		
	          obj = dbmanager.executeInsert(sql,
	                     new Object[] {clan_name, stemma, descrizione, min_trophy, position, type, maxUsers, minUsers});
	          
	  
	          String sql2 = "select id_clan, clan_name, trofei_total,"
	          		+ "stemma from guesswho.clan where clan_name = ? ";
	          trace(sql2);
	          
	          String sql4 = "select * from guesswho.clan where clan_name = '" + clan_name + "'";
	          
	          PreparedStatement stmt3 = connection.prepareStatement(sql4);
	          ResultSet rs = stmt3.executeQuery();
				while(rs.next()){
					int idclan = rs.getInt("id_clan");
					trace(idclan);
	          
					String ruolo = "CAPO";
					
	          PreparedStatement stmt4 = connection.prepareStatement("INSERT INTO clan_users"+"(id_clan,id_user,ruolo) VALUES("+ idclan + ", " + user_founder + ", '" + ruolo + "' );");
				stmt4.executeUpdate();
				}
	          ISFSArray arr = dbmanager.executeQuery(sql2
						, new Object[] {clan_name});
	          if (arr.size() > 0){
	          
	        SFSObject success = new SFSObject();
	      	success.putUtfString("success" ,"Clan successfully registrated");
	      	success.putSFSArray("daticlan", arr);
	      	send("clan", success, user);
				}
	          
	    createRoom(user,params);
	   
		} 
		catch (SQLException e) {
		
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL update error");
		send("clan" , error, user);
		e.printStackTrace();
		trace(e.toString());
	}

			}	} catch (SQLException e1) {
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL error");
				send("clan" , error, user);
				e1.printStackTrace();
				trace(e1.toString());
			}
		finally{
			try{
				connection.close();
			}catch (SQLException e){
        		trace("A SQL Error occurred: " + e.getMessage());
        	}
        }
		}
	

	
	private void createRoom(User sender, ISFSObject params){
		 String clan_name = params.getUtfString("name");
		
		RoomExtensionSettings res = new RoomExtensionSettings("Server","clanserver.ClanExtension");
		      CreateRoomSettings crs = new CreateRoomSettings();
		     
		    
		      crs.setName(clan_name);
		      crs.setGroupId("clan_name " + clan_name);
		      crs.setMaxVariablesAllowed(20);
		      crs.setMaxUsers(20);
		      crs.setAutoRemoveMode(SFSRoomRemoveMode.WHEN_EMPTY);
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
		      
		    
	public String RandomString() {
		 String alphaNumerics = "qwertyuiopasdfghjklzxcvbnm1234567890";
		   String str = "";
		    for (int i = 0; i < 8; i++) {
		    str += alphaNumerics.charAt((int) (Math.random() * alphaNumerics.length()));
				}
			return str;
	}

	
}