package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.api.CreateRoomSettings.RoomExtensionSettings;
import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
//import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
//import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
//import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class EnterClanHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	Object obj2 = null;

	
	public void handleClientRequest(User user, ISFSObject params){
		trace("Sto chiedendo di registrare uno user dentro un clan al server");
		
		int userplayer = params.getInt("user_id");
		int clan_id = params.getInt("clan_id");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		Connection connection = null;
		try {
			connection = dbmanager.getConnection();
			trace("sono entrato nel primo try per verificare che lo user non abbia già clan");
			
			PreparedStatement stmt = connection.prepareStatement("Select clan_name From Users "
									+ "LEFT JOIN CLAN_USERS ON CLAN_USERS.ID_USER = USERS.ID_USER "
                                    + "LEFT JOIN CLAN ON CLAN.ID_CLAN = CLAN_USERS.ID_CLAN "
							        + "where users.id_user = " + userplayer );

			  ResultSet res = stmt.executeQuery();
			    
			    while(res.next())
				{
			    	String clan_name = res.getString("clan_name");
			    	if(clan_name == null){
			    		clan_name="";
			    	}
			    	trace(clan_name);
			    	
			    	if(clan_name == "" || clan_name == null){
			    		trace("se l'utente non ha clan ora vediamo se il clan selezionato è Public");
			    		
			    		PreparedStatement stmt2 = connection.prepareStatement("Select tipo from Clan where id_clan = " + clan_id );
			    		
			    		ResultSet r = stmt2.executeQuery();
			    		while(r.next()){
			    			String tipo = r.getString("tipo");
			    			trace(" stampiamo il tipo" + tipo);
			    			
			    			if(tipo.equals("Public")){
			    				trace("Verifichiamo che quel clan non abbia già max users");
			    				PreparedStatement stmt3 = connection.prepareStatement("SELECT count(clan_users.id_user) as utenti_clan, maxUsers from clan_users "
											+ "Inner JOIN CLAN ON CLAN.ID_CLAN = CLAN_users.ID_CLAN "
                                            + "inner JOIN USERS ON Users.ID_user = CLAN_USERS.ID_user "	
										    + "where clan_users.id_clan = " + clan_id );
			    				
			    				ResultSet rs = stmt3.executeQuery();
			    				while(rs.next()){
			    					int contatore = rs.getInt("utenti_clan");
			    					trace(contatore);
			    					
			    					int maxusers = rs.getInt("maxUsers");
			    					trace(maxusers);
			    					
			    					if(contatore <= maxusers){
			    						trace("prepariamo l'inserimento dello user");
			    						
			    						String ruolo = "RECLUTA";
			    						
			    				          PreparedStatement stmt4 = connection.prepareStatement("INSERT INTO clan_users"+"(id_clan,id_user,ruolo) VALUES("+ clan_id + ", " + userplayer + ", '" + ruolo + "' );");
			    						
			    				         
			    							
			    					
			    						stmt4.executeUpdate();
			    						
			    						String ssql = "Insert into chat_general(id_user, id_clan, message, datamex, type_not) Values (1, " + clan_id + ", 'E entrato a far parte del clan lo user " + userplayer + "', Now(), 1)";
			    						
			    						stmt4 = connection.prepareStatement(ssql);
			    						
			    						stmt4.executeUpdate();
			    						
			    							SFSObject success = new SFSObject();
			    							
			    							
			    								String sql2 = "select id_clan, clan_name, trofei_total,"
			    						          		+ "stemma, position from guesswho.clan where id_clan = ? ";
			    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    						        	  
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								
			    								send("enterclan", success, user);
			    								
			    						          }
			    						          createRoom(user,params);
			    								break;
			    					}
			    							 else{
			    								trace("impossibile inserire lo user");
			    								ISFSObject error = new SFSObject();
			    								error.putUtfString("error", "sql problem error");
			    								send("enterclan", error, user);
			    								break;
			    							}
			    					
			    							
			    					}}else{
			    						trace("clan con utenti massimi");
			    						ISFSObject error = new SFSObject();
			    						error.putUtfString("error", "max user raggiunti");
			    						send("enterclan", error, user);
			    						break;
			    					}}}
			    			
			    				
										
			    			
			    			else{
			    				trace("clan non Pubblico");
			    				ISFSObject error = new SFSObject();
			    				error.putUtfString("error", "no public clan");
			    				send("enterclan", error, user);
			    				break;
			    			}
			    		}
			    	
				}
				   catch (SQLException e)
        {
		        	SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
		        	errData.addParameter("SQL Error: " + e.getMessage());
		        	e.printStackTrace();
		    		trace(e.toString());
		        	
		    		
		        }
		        finally {
		        	try{
		        		connection.close();
		        	}catch (SQLException e){
		        		e.printStackTrace();
		        		trace(e.toString());
		        	}
		        }


}
	
	private void createRoom(User sender, ISFSObject params){
		 String clan_name = params.getUtfString("clan_name");
		
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

}
