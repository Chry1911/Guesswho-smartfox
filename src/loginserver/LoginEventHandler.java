package loginserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Iterator;
import java.util.List;

import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.CreateRoomSettings.RoomExtensionSettings;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;




public class LoginEventHandler extends BaseServerEventHandler {
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		// TODO Auto-generated method stub
		trace("Sto chiedendo al server di loggare un utente");
		// Grab parameters from client request
		String userName = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
	    String cryptedPass = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
		//String email = (String)event.getParameter(SFSEventParam.LOGIN_PASSWORD);
		ISession session = (ISession) event.getParameter(SFSEventParam.SESSION);
		trace("Username----------" + userName);
		trace("Password----------" + cryptedPass);
		trace("Session----------" + session);
		
		 ISFSObject outData = (ISFSObject) event.getParameter(SFSEventParam.LOGIN_OUT_DATA);
		// ISFSObject outData2 = (ISFSObject) event.getParameter(SFSEventParam.LOGIN_OUT_DATA);
		   User user = null;
		   // Add data to the object
		   
		
		// Get password from DB
		IDBManager dbManager = getParentExtension().getParentZone().getDBManager();
		Connection connection = null;
	

		// Grab a connection from the DBManager connection pool
        try {
			connection = dbManager.getConnection();

			// Build a prepared statement
	        PreparedStatement stmt = connection.prepareStatement("SELECT id_user,username, password, trofei, gold, gems ,guesswho.users.position, id_clan ,clan_name, guesswho.clan.position as postoclan, trofei_total, stemma "
	        		+ "FROM Users "
	        		+ "left outer join guesswho.clan on guesswho.users.id_user = guesswho.clan.utente_fondatore "
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
	        		+ "where username='"+userName+"'");
	        
	    
               

		    // Execute query
		    ResultSet res = stmt.executeQuery();
		    
		    while(res.next())
			{
		    	
		    	int id_user = res.getInt("id_user");
		    	trace(id_user);

				String username = res.getString("username");
				trace(username);
				
				int trofei = res.getInt("trofei");
				trace(trofei);
				
				int gold = res.getInt("gold");
				trace(gold);
				int gems = res.getInt("gems");
				trace(gems);
				String position = res.getString("position");
				
				if (position == null){
					position = "";
				}
				trace(position);
				
				int clan_id = res.getInt("id_clan");
				trace(clan_id);
				
				String clan_name = res.getString("clan_name");
				
				if (clan_name == null){
					clan_name = "";
				}
				trace(clan_name);
				
				String clan_position = res.getString("postoclan");
				
				if(clan_position == null){
					clan_position = "";
				}
				trace(clan_position);
				
				int stemma = res.getInt("stemma");
				trace(stemma);
				
				int trofei_clan = res.getInt("trofei_total");
				trace(trofei_clan);
				
				outData.putInt("id_user", id_user);
				outData.putUtfString("nome_utente", username);
				outData.putInt("trofei", trofei);
				outData.putInt("gold", gold);
				outData.putInt("gems", gems);
				outData.putUtfString("position", position);
				outData.putInt("clan_id", clan_id);
				outData.putUtfString("clan_name", clan_name);
				outData.putUtfString("clan_position", clan_position);
				outData.putInt("trofei_clan", trofei_clan);
				outData.putInt("stemma", stemma);
				
				
				if(clan_name != null || clan_name != ""){
					
				List<Room> roomname = getParentExtension().getParentZone().getRoomList();
				trace("la lista delle stanze attive " + roomname.toString());
				/*Iterator<Room> iterator = roomname.iterator();
				while(iterator.hasNext()){*/
					if(!roomname.equals(clan_name)){
					//if(roomname.getName() != clan_name){
						createRoom(user,clan_name);
						trace("creata room clan " + clan_name);
						/*String sql = "Select *, username from " + clan_name + "_chat "
								+ "inner join guesswho.users on guesswho.users.id_user = guesswho." + clan_name + "_chat.id_user "
								+ "order by ID desc limit 100 ";
						PreparedStatement stmt2 = connection.prepareStatement(sql);
						ResultSet rs2 = stmt2.executeQuery();
						while(rs2.next()){
							int id = rs2.getInt("ID");
							String nickname = rs2.getString("username");
							String message = rs2.getString("message");
							
							trace(nickname);
							trace(id);
							trace(message);
							
							//outData.putInt("ID", id);
							//outData.putUtfString("nickname", nickname);
							//outData.putUtfString("message", message);
							//outData.putSFSArray("arrayMessage", array);
							
						}*/
						
					}else{
						
						trace("room già esistente");
					}
				}
				//}
					//createRoom(user,clan_name);
			}
		    
		    //outData.putInt("number", 100);
		   // Verify that one record was found
 			if (!res.first())
 			{
 				// This is the part that goes to the client
 				SFSErrorData errData = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
 				errData.addParameter(userName);
                trace("mi sono intoppato qui???");
 				// Sends response if user gave incorrect user name
 				throw new SFSLoginException("Bad user name: " + userName, errData);
 				
 			}
 			
 			

 			String dbPword = res.getString("password");

			// Verify the secure password
			if (!getApi().checkSecurePassword(session, dbPword, cryptedPass))
			{
				trace("mi sono intoppato dopo???");
				SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
				data.addParameter(userName);
				// Sends response if user gave incorrect password
				throw new SFSLoginException("Login failed for user: "  + userName, data);
			}

        }

        // User name was not found
        catch (SQLException e)
        {
        	SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
        	errData.addParameter("SQL Error: " + e.getMessage());
        	trace(" mi sono intoppato quiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii???");
        	// Sends response about mysql errors
        	throw new SFSLoginException("A SQL Error occurred: " + e.getMessage(), errData);
        }
        finally {
        	try{
        		connection.close();
        	}catch (SQLException e){
        		throw new SFSLoginException("A SQL Error occurred: " + e.getMessage());
        	}
        }

    }
	
	private void createRoom(User sender, String clanname){
		 String clan_name = clanname;
			//Room myfirstroom = getParentExtension().getParentZone().getRoomByName(clan_name);
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
				         //getApi().createRoom(sender.getZone(),crs,sender);
				         //getApi().createRoom(sender.getZone(),crs,null);
				    	  getApi().createRoom(getParentExtension().getParentZone(),crs,null);
				         reback.putBool("success", true);
				      } catch (SFSCreateRoomException e) {
				         e.printStackTrace();
				         reback.putBool("success", false);
				      }finally{
				         //send("createRoom", reback, sender);
				      }
			      }
			      
			    
			   

}