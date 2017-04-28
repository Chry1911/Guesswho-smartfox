package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	//private SmartFox sfs;
	
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
							+ "where id_user = " + userplayer );
							
					
			
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
			    				PreparedStatement stmt3 = connection.prepareStatement("SELECT count(users.id_user) as utenti_clan, maxUsers from Users "
										+ "left outer join guesswho.clan on guesswho.users.id_user = guesswho.clan.utente_2 "
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
										+ "where id_clan = " + clan_id );
			    				
			    				ResultSet rs = stmt3.executeQuery();
			    				while(rs.next()){
			    					int contatore = rs.getInt("utenti_clan");
			    					trace(contatore);
			    					
			    					int maxusers = rs.getInt("maxUsers");
			    					trace(maxusers);
			    					
			    					if(contatore <= maxusers){
			    						trace("prepariamo l'inserimento dello user");
			    						PreparedStatement stmt4 = connection.prepareStatement("Select * from Clan where id_clan = " + clan_id);
			    						ResultSet q = stmt4.executeQuery();
			    						while(q.next()){
			    							int utente2 = q.getInt("utente_2");
			    							trace(utente2);
			    							int utente3 = q.getInt("utente_3");
			    							trace(utente3);
			    							int utente4 = q.getInt("utente_4");
			    							trace(utente4);
			    							int utente5 = q.getInt("utente_5");
			    							trace(utente5);
			    							int utente6 = q.getInt("utente_6");
			    							trace(utente6);
			    							int utente7 = q.getInt("utente_7");
			    							trace(utente7);
			    							int utente8 = q.getInt("utente_8");
			    							trace(utente8);
			    							int utente9 = q.getInt("utente_9");
			    							trace(utente9);
			    							int utente10 = q.getInt("utente_10");
			    							trace(utente10);
			    							int utente11 = q.getInt("utente_11");
			    							trace(utente11);
			    							int utente12 = q.getInt("utente_12");
			    							trace(utente12);
			    							int utente13 = q.getInt("utente_13");
			    							trace(utente13);
			    							int utente14 = q.getInt("utente_14");
			    							trace(utente14);
			    							int utente15 = q.getInt("utente_15");
			    							trace(utente15);
			    							int utente16 = q.getInt("utente_16");
			    							trace(utente16);
			    							int utente17 = q.getInt("utente_17");
			    							trace(utente17);
			    							int utente18 = q.getInt("utente_18");
			    							trace(utente18);
			    							int utente19 = q.getInt("utente_19");
			    							trace(utente19);
			    							int utente20 = q.getInt("utente_20");
			    							trace(utente20);
			    							
			    							Statement stmt5;
			    							SFSObject success = new SFSObject();
			    							
			    							if(utente2 == 0){
			    								String sql = "Update clan set utente_2 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente2 inserito nel clan");
			    								

			    						          String sql2 = "select id_clan, clan_name, trofei_total,"
			    						          		+ "stemma from guesswho.clan where id_clan = ? ";
			    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							}else if (utente3 == 0) {
			    								String sql = "Update clan set utente_3 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente3 inserito nel clan");
			    								
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente4 == 0) {
			    								String sql = "Update clan set utente_4 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente4 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente5 == 0) {
			    								String sql = "Update clan set utente_5 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente5 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente6 == 0) {
			    								String sql = "Update clan set utente_6 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente6 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
				    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente7 == 0) {
			    								String sql = "Update clan set utente_7 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente7 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente8 == 0) {
			    								String sql = "Update clan set utente_8 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente8 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente9 == 0) {
			    								String sql = "Update clan set utente_9 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente9 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    						          break;
			    							} else if (utente10 == 0) {
			    								String sql = "Update clan set utente_10 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente10 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente11 == 0) {
			    								String sql = "Update clan set utente_11 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente11 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente12 == 0) {
			    								String sql = "Update clan set utente_12 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente12 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente13 == 0) {
			    								String sql = "Update clan set utente_13 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente13 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente14 == 0) {
			    								String sql = "Update clan set utente_14 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente14 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente15 == 0) {
			    								String sql = "Update clan set utente_15 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente15 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente16 == 0) {
			    								String sql = "Update clan set utente_16 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente16 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente17 == 0) {
			    								String sql = "Update clan set utente_17 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement(); 
			    								stmt5.executeUpdate(sql);
			    								trace("utente17 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente18 == 0) {
			    								String sql = "Update clan set utente_18 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente18 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								// createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente19 == 0) {
			    								String sql = "Update clan set utente_19 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente19 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else if (utente20 == 0) {
			    								String sql = "Update clan set utente_20 = " + userplayer + " where id_clan = " + clan_id;
			    								stmt5 = connection.createStatement();
			    								stmt5.executeUpdate(sql);
			    								trace("utente20 inserito nel clan");
			    								 String sql2 = "select id_clan, clan_name, trofei_total,"
				    						          		+ "stemma from guesswho.clan where id_clan = ? ";
				    						          trace(sql2);
			    						          
			    						          ISFSArray arr = dbmanager.executeQuery(sql2
			    											, new Object[] {clan_id});
			    						          if (arr.size() > 0){
			    								
			    								
			    								success.putUtfString("success", "utente inserito nel clan");
			    								success.putSFSArray("daticlan", arr);
			    								send("enterclan", success, user);
			    								 //createRoom(user,params);
			    						          }
			    						          createRoom(user,params);
			    								break;
			    							} else{
			    								trace("impossibile inserire lo user");
			    								ISFSObject error = new SFSObject();
			    								error.putUtfString("error", "sql problem error");
			    								send("enterclan", error, user);
			    								break;
			    							}
			    							
			    							}
			    					}else{
			    						trace("clan con utenti massimi");
			    						ISFSObject error = new SFSObject();
			    						error.putUtfString("error", "max user raggiunti");
			    						send("enterclan", error, user);
			    						break;
			    					}
			    				}
										
			    			}
			    			else{
			    				trace("clan non Pubblico");
			    				ISFSObject error = new SFSObject();
			    				error.putUtfString("error", "no public clan");
			    				send("enterclan", error, user);
			    				break;
			    			}
			    		}
			    	}else{
			    		trace("l'utente ha già clan");
			    		ISFSObject error = new SFSObject();
						error.putUtfString("error", "user ha già clan");
						send("enterclan", error, user);
			    		break;
			    	}
				}
				}   catch (SQLException e)
        {
		        	SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
		        	errData.addParameter("SQL Error: " + e.getMessage());
		        	e.printStackTrace();
		    		trace(e.toString());
		        	// Sends response about mysql errors
		    		
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
