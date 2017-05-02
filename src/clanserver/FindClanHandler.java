package clanserver;

import java.sql.Connection;
import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class FindClanHandler extends BaseClientRequestHandler {
	
	Object obj = null;
    private Connection connection;
	@Override
	public void handleClientRequest(User user, ISFSObject params) {
		trace("Sto chiedendo al server di trovare un clan con i miei parametri");
		
		String name = params.getUtfString("text");
		String location = params.getUtfString("location");
		int maxUsers = params.getInt("maxUsers");
		int minUsers = params.getInt("minUsers");
		int minTrophy = params.getInt("minTrophy");
		int usertrophy = params.getInt("user_trophy");
		//boolean entry = params.getBool("can_entry");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		connection = null;
		//if (entry == true){
		try{
			trace("Ho fatto l'accesso per richiedere al server la mia query");
			connection = dbmanager.getConnection();
		
				trace("query con boolean = true");
			//obj = dbmanager.executeQuery("SELECT * FROM guesswho.Clan Limit 100 ", new Object[] {}); 
			ISFSArray arr = dbmanager.executeQuery("SELECT * FROM guesswho.Clan where clan_name "
					+ "Like ? and position Like ? and maxUsers <= ? and minUsers >= ? and min_trofei >= ? "
					+ "and min_trofei <= " + usertrophy + "  and (tipo = 'Public' or tipo = 'Invito') "
					+ "order by trofei_total desc, clan_name", 
					new Object[] {"%"+ name + "%", "%"+ location + "%", maxUsers, minUsers, minTrophy});
			
			
			if (arr.size() > 0)
			{
			  SFSObject result = new SFSObject();
			  result.putSFSArray("success", arr);
			  send("findclan", result, user);
			}
			else {
				SFSObject result2 = new SFSObject();
				  result2.putSFSArray("nosuccess", arr);
				  send("findclan", result2, user);
			}
			
		}catch (SQLException ex) {
			ISFSObject error = new SFSObject();
			error.putUtfString("error", "MySQL error");
			send("findclan" , error, user);
	}
		finally{
			try{
				connection.close();
			}catch (SQLException e){
        		trace("A SQL Error occurred: " + e.getMessage());
        	}
        
		}
		
		
	//}
		/*
		else if(entry == false){
			try{
				trace("Ho fatto l'accesso per richiedere al server la mia query");
				
			
					trace("query con boolean = false");
				//obj = dbmanager.executeQuery("SELECT * FROM guesswho.Clan Limit 100 ", new Object[] {}); 
				ISFSArray arr2 = dbmanager.executeQuery("SELECT * FROM guesswho.Clan where clan_name "
						+ "Like ? and position = ? and maxUsers <= ? and minUsers >= ? and min_trofei = ? "
						+ "and (tipo = 'pubblico' or tipo = 'invito') "
						+ "order by trofei_total desc, clan_name", 
						new Object[] {name, location, maxUsers, minUsers, usertrophy});
				if (arr2.size() > 0)
				{
				  SFSObject result = new SFSObject();
				  result.putSFSArray("success", arr2);
				  send("findclan", result, user);
				}
			
	}catch (SQLException ex) {
		ISFSObject error = new SFSObject();
		error.putUtfString("error", "MySQL error");
		send("findclan" , error, user);
		}
		}
		}
		*/
	}}
	
