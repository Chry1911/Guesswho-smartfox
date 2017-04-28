package clanserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
//import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ExitClanHandler extends BaseClientRequestHandler {
	
	public void handleClientRequest(User user, ISFSObject params){
		
	    trace("Sto chiedendo di eliminare uno user dentro un clan al server");
		
		int userplayer = params.getInt("user_id");
		int clan_id = params.getInt("clan_id");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		Connection connection = null;
		try {
			connection = dbmanager.getConnection();
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
				
				if(utente2 == userplayer){
					String sql = "Update clan set utente_2 = NULL where id_clan = " + clan_id + " and utente_2 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente2 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente3 == userplayer){
					String sql = "Update clan set utente_3 = NULL where id_clan = " + clan_id + " and utente_3 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente3 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente4 == userplayer){
					String sql = "Update clan set utente_4 = NULL where id_clan = " + clan_id + " and utente_4 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente4 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente5 == userplayer){
					String sql = "Update clan set utente_5 = NULL where id_clan = " + clan_id + " and utente_5 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente5 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente6 == userplayer){
					String sql = "Update clan set utente_6 = NULL where id_clan = " + clan_id + " and utente_6 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente6 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente7 == userplayer){
					String sql = "Update clan set utente_7 = NULL where id_clan = " + clan_id + " and utente_7 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente7 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente8 == userplayer){
					String sql = "Update clan set utente_8 = NULL where id_clan = " + clan_id + " and utente_8 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente8 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente9 == userplayer){
					String sql = "Update clan set utente_9 = NULL where id_clan = " + clan_id + " and utente_9 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente9 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente10 == userplayer){
					String sql = "Update clan set utente_10 = NULL where id_clan = " + clan_id + " and utente_10 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente10 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente11 == userplayer){
					String sql = "Update clan set utente_11 = NULL where id_clan = " + clan_id + " and utente_11 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente11 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente12 == userplayer){
					String sql = "Update clan set utente_12 = NULL where id_clan = " + clan_id + " and utente_12 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente12 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente13 == userplayer){
					String sql = "Update clan set utente_13 = NULL where id_clan = " + clan_id + " and utente_13 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente13 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente14 == userplayer){
					String sql = "Update clan set utente_14 = NULL where id_clan = " + clan_id + " and utente_14 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente14 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente15 == userplayer){
					String sql = "Update clan set utente_15 = NULL where id_clan = " + clan_id + " and utente_15 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente15 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente16 == userplayer){
					String sql = "Update clan set utente_16 = NULL where id_clan = " + clan_id + " and utente_16 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente16 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente17 == userplayer){
					String sql = "Update clan set utente_17 = NULL where id_clan = " + clan_id + " and utente_17 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente17 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente18 == userplayer){
					String sql = "Update clan set utente_18 = NULL where id_clan = " + clan_id + " and utente_18 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente18 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente19 == userplayer){
					String sql = "Update clan set utente_19 = NULL where id_clan = " + clan_id + " and utente_19 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente19 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else if(utente20 == userplayer){
					String sql = "Update clan set utente_20 = NULL where id_clan = " + clan_id + " and utente_20 = " + userplayer;
					trace(sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente20 eliminato dal clan");
				
					success.putUtfString("success", "utente eliminato dal clan");
					//success.putSFSArray("daticlan", arr);
					send("exitclan", success, user);
					// createRoom(user,params);
					break;
				}else{
					trace("impossibile cancellare lo user");
					ISFSObject error = new SFSObject();
					error.putUtfString("error", "sql problem error");
					send("exitclan", error, user);
					break;
				}
			}
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			trace("vediamo cosa contiene l'array" + error.toString());
			error.putUtfString("error", "MySQL error");
			send("userdetail" , error, user);
			ex.printStackTrace();
	}
	}
}
