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
			
			
			String ssql = "select count(guesswho.clan_users.id_user) as membri "
					  +	"from guesswho.clan_users " 
					+ "left join guesswho.clan on guesswho.clan.id_clan = guesswho.clan_users.id_clan "
					+ "left join guesswho.users on guesswho.users.id_user = guesswho.clan_users.id_user "
					+ "where guesswho.clan_users.id_clan = " + clan_id ;	
			
			PreparedStatement stmt4 = connection.prepareStatement(ssql);
					
			ResultSet q = stmt4.executeQuery();
			while(q.next()){
				
				int membri = q.getInt("membri");
				
				trace(membri + ": membri");
				
				
				
				String ssql2 = "select guesswho.clan_users.id_user, guesswho.clan_users.ruolo, guesswho.clan_users.id_clan, guesswho.clan.clan_name "
						+	"from guesswho.clan_users " 
						+ "left join guesswho.clan on guesswho.clan.id_clan = guesswho.clan_users.id_clan "
						+ "left join guesswho.users on guesswho.users.id_user = guesswho.clan_users.id_user "
						+ "where guesswho.clan_users.id_clan = " + clan_id + " and guesswho.clan_users.id_user = " + userplayer;
				
				trace(ssql2);
				
				PreparedStatement stmt1 = connection.prepareStatement(ssql2);
				ResultSet rs = stmt1.executeQuery();
				
				trace(rs);
				
				while(rs.next()){
					int utente = rs.getInt("clan_users.id_user");
					String clan_name = rs.getString("clan_name");
					String ruolo = rs.getString("ruolo");
					
					trace(clan_name + ": clan_name");
					trace(utente + ": id_user");
					trace(ruolo + ": ruolo");
					
				
				Statement stmt5;
				SFSObject success = new SFSObject();
				
				if(utente == userplayer && ruolo.equals("CAPO") && membri > 1){
					
					//dobbiamo fare i vari update per ruolo(parire da co-capo a recluta)
					
					
					//String role = "";
					String query = "";
					
					if(CoCapoExists(clan_id) == true){
						
						trace("entro se c'è co-capo");
					
					//query con co-capo(forse dobbiamo vedere prima se c'è un co-capo??)
					query = "select guesswho.users.id_user, max(trofei) as numerotrofei from Users "
							+  "left join guesswho.clan_users on guesswho.users.id_user = guesswho.clan_users.id_user "
							+ "left join guesswho.clan on guesswho.clan.id_clan = guesswho.clan_users.id_clan "				
							+ "where guesswho.clan_users.id_clan = " + clan_id + " and guesswho.users.id_user <> " + userplayer + " and guesswho.clan_users.ruolo = 'CO-CAPO' group by guesswho.Users.id_user order by numerotrofei desc limit 1 ";
					
					trace("stampiamo la query" + query);
					PreparedStatement stmt = connection.prepareStatement(query);
					ResultSet r = stmt.executeQuery();
					
					trace(r);
					
					while(r.next()){
					int id_utente = r.getInt("id_user");
					trace("id dell'utente con + trofei " + id_utente);
					
			
					String update = "Update clan_users set ruolo = 'CAPO' where id_user = " + id_utente + " and id_user <> " + userplayer + " and id_clan = " + clan_id;
					trace("stampiamo la query di update" + update);
					
					stmt4 = connection.prepareStatement(update);
					stmt4.executeUpdate();
					
					//qui cancelliamo il capo di quel clan
					
					String sql = "delete from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer;
					trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente eliminato dal clan");
					
					String ssql3 = "Insert into chat_general(id_user, id_clan, message, datamex, type_not) Values (1, " + clan_id + ", 'E uscito del clan lo user " + userplayer + "', Now(), 2)";
					
					stmt4 = connection.prepareStatement(ssql3);
					
					stmt4.executeUpdate();
					
					
				
					success.putUtfString("success", "utente eliminato dal clan");
					
					send("exitclan", success, user);
					
					
					break;
						}} else if(CoCapoExists(clan_id) == false && AnzianoExists(clan_id) == true){
							
							trace("entro se sono anziano");
							
							query = "select guesswho.users.id_user, max(trofei) as numerotrofei from Users "
									+  "left join guesswho.clan_users on guesswho.users.id_user = guesswho.clan_users.id_user "
									+ "left join guesswho.clan on guesswho.clan.id_clan = guesswho.clan_users.id_clan "				
									+ "where guesswho.clan_users.id_clan = " + clan_id + " and guesswho.users.id_user <> " + userplayer + " and guesswho.clan_users.ruolo = 'ANZIANO' group by guesswho.Users.id_user order by numerotrofei desc limit 1 ";
							trace("stampiamo la query" + query);
							PreparedStatement stmt = connection.prepareStatement(query);
							ResultSet r = stmt.executeQuery();
							
							trace(r);
							
							while(r.next()){
							int id_utente = r.getInt("id_user");
							trace("id dell'utente con + trofei " + id_utente);
							
					
							String update = "Update clan_users set ruolo = 'CAPO' where id_user = " + id_utente + " and id_user <> " + userplayer + " and id_clan = " + clan_id;
							trace("stampiamo la query di update" + update);
							
							stmt4 = connection.prepareStatement(update);
							stmt4.executeUpdate();
							
							//qui cancelliamo il capo di quel clan
							
							String sql = "delete from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer;
							trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
							stmt5 = connection.createStatement();
							stmt5.executeUpdate(sql);
							trace("utente eliminato dal clan");
							
							String ssql3 = "Insert into chat_general(id_user, id_clan, message, datamex, type_not) Values (1, " + clan_id + ", 'E uscito del clan lo user " + userplayer + "', Now(), 2)";
							
							stmt4 = connection.prepareStatement(ssql3);
							
							stmt4.executeUpdate();
							
							
						
							success.putUtfString("success", "utente eliminato dal clan");
							
							send("exitclan", success, user);
							
							
							break;}
						}else {
							
							trace("entro se sono recluta");
							
							query = "select guesswho.users.id_user, max(trofei) as numerotrofei from Users "
									+  "left join guesswho.clan_users on guesswho.users.id_user = guesswho.clan_users.id_user "
									+ "left join guesswho.clan on guesswho.clan.id_clan = guesswho.clan_users.id_clan "				
									+ "where guesswho.clan_users.id_clan = " + clan_id + " and guesswho.users.id_user <> " + userplayer + " and guesswho.clan_users.ruolo = 'RECLUTA' group by guesswho.Users.id_user order by numerotrofei desc limit 1 ";
						
							trace("stampiamo la query" + query);
							PreparedStatement stmt = connection.prepareStatement(query);
							ResultSet r = stmt.executeQuery();
							
							trace(r);
							
							while(r.next()){
							int id_utente = r.getInt("id_user");
							trace("id dell'utente con + trofei " + id_utente);
							
					
							String update = "Update clan_users set ruolo = 'CAPO' where id_user = " + id_utente + " and id_user <> " + userplayer + " and id_clan = " + clan_id;
							trace("stampiamo la query di update" + update);
							
							stmt4 = connection.prepareStatement(update);
							stmt4.executeUpdate();
							
							//qui cancelliamo il capo di quel clan
							
							String sql = "delete from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer;
							trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
							stmt5 = connection.createStatement();
							stmt5.executeUpdate(sql);
							trace("utente eliminato dal clan");
							
							String ssql3 = "Insert into chat_general(id_user, id_clan, message, datamex, type_not) Values (1, " + clan_id + ", 'E uscito del clan lo user " + userplayer + "', Now(),2)";
							
							stmt4 = connection.prepareStatement(ssql3);
							
							stmt4.executeUpdate();
							
							
						
							success.putUtfString("success", "utente eliminato dal clan");
							
							send("exitclan", success, user);
							
							
							break;
						}}
					
					
					
					}
				
				
				else if(utente == userplayer && membri > 1 && ! ruolo.equals("CAPO")){
					
					String sql = "delete from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer;
					trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
					stmt5 = connection.createStatement();
					stmt5.executeUpdate(sql);
					trace("utente eliminato dal clan");
					
					String ssql3 = "Insert into chat_general(id_user, id_clan, message, datamex, type_not) Values (1, " + clan_id + ", 'E uscito del clan lo user " + userplayer + "', Now(), 2)";
					
					stmt4 = connection.prepareStatement(ssql3);
					
					stmt4.executeUpdate();
					
					
				
					success.putUtfString("success", "utente eliminato dal clan");
					
					send("exitclan", success, user);
					
					
					break;
					
							
					}
				
				else if(utente == userplayer && membri == 1 && ruolo.equals("CAPO")){
						String sql = "delete from clan_users where id_clan = " + clan_id + " and id_user = " + userplayer;
						trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
						stmt5 = connection.createStatement();
						stmt5.executeUpdate(sql);
						trace("utente eliminato dal clan");
						
						
					
						
						
							String sql3 = "delete from clan where id_clan = " + clan_id ;
							trace("Stampiamo la query che elimina il clan da clan " + sql3);
							stmt5 = connection.createStatement();
							stmt5.executeUpdate(sql3);
							trace("Clan eliminato");
							
							
							
							success.putUtfString("success", "utente eliminato dal clan");
							
							send("exitclan", success, user);
							break;
					}
				
				else{
					trace("impossibile cancellare lo user");
					ISFSObject error = new SFSObject();
					error.putUtfString("error", "sql problem error");
					send("exitclan", error, user);
					break;
				}
				}
				
			}
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			trace("vediamo cosa contiene l'array" + error.toString());
			error.putUtfString("error", "MySQL error");
			send("userdetail" , error, user);
			ex.printStackTrace();
	}
		finally {
        	try{
        		connection.close();
        	}catch (SQLException e){
        		trace("A SQL Error occurred: " + e.getMessage());
        	}
        }
	}
	
	
	public boolean CoCapoExists(int clan_id) throws SQLException{
		Connection connection = null;
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		try {
			connection = dbmanager.getConnection();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		String sql = "select count(ruolo) as numero from clan_users where ruolo = 'CO-CAPO' and id_clan = " + clan_id;
		trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
		PreparedStatement stmt4 = connection.prepareStatement(sql);
		
		ResultSet q = stmt4.executeQuery();
		while(q.next()){
			
			int membri = q.getInt("numero");
		
		trace("membri co-capo" + membri);
		return true;
		}
		return false;
	}
	
	public boolean AnzianoExists(int clan_id) throws SQLException{
		Connection connection = null;
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		try {
			connection = dbmanager.getConnection();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		String sql = "select count(ruolo) as numero from clan_users where ruolo = 'ANZIANO' and id_clan = " + clan_id;
		trace("Stampiamo la query che elimina l'utente da quel clan " + sql);
		PreparedStatement stmt4 = connection.prepareStatement(sql);
		
		ResultSet q = stmt4.executeQuery();
		while(q.next()){
			
			int membri = q.getInt("numero");
		
		trace("membri anziani" + membri);
		return true;
		}
		return false;
		
	}
	
	
}
