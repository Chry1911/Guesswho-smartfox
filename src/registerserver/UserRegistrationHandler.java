package registerserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;

import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;


public class UserRegistrationHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	Date date;
	private Connection connection;
	public void handleClientRequest(User user, ISFSObject params){

		trace("Sto chiedendo di registrare uno user al server");
		
		
		String first_name = params.getUtfString("first_name");
		String last_name = params.getUtfString("last_name");
		String birth_day = params.getUtfString("date");
		String name = params.getUtfString("name");
        String password = params.getUtfString("password");
		String email = params.getUtfString("email");
		int trofei = params.getInt("trofei");
		int gemme = params.getInt("gems");
		int gold = params.getInt("gold");
		String nation = params.getUtfString("nation");
		
		
		RandomString captcha = new RandomString();
	    String code = captcha.toString();
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
		connection = null;
		
			try {
				trace("sono entrato nel primo try");
				connection = dbmanager.getConnection();
				//obj = dbmanager.executeQuery(sql2, new Object[] {1});
				obj = dbmanager.executeQuery("SELECT * FROM Users WHERE  email=? or username =?", new Object[] {email,name}); 
				
				trace(obj.toString());
				
				SFSArray ar = (SFSArray) obj;
				
				//trace(condition.toString() + "condizione");
				
				if(ar.size() >= 1){
					trace("Errore email già presente nel sistema");
					ISFSObject error = new SFSObject();
					error.putUtfString("error", "email già registrata nel database");
					send("register" , error, user);
					//return;
				}else {
					trace("registriamo l'utente nel nostro database");
					
				
					try{
						trace("sono entrato nel secondo try");
						
					    DateFormat readFormat = new SimpleDateFormat( "DD/mm/yyyy");

					    DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
					    
					  
					       date = readFormat.parse( birth_day );
					   

					    String formattedDate = "";
					    if( date != null ) {
					    formattedDate = writeFormat.format( date );
					    }
					    
					    if(trofei >= 0){
					    	trofei = 0;
					    }
					    
					    if(gemme >=0){
					    	gemme = 100;
					    }
					    
					    if(gold >= 0){
					    	gold = 0;
					    }
					    
					    trace("stampiamo la data " + formattedDate);

						String sql = "INSERT into Users(first_name, last_name, date_of_birth, username, password, email, trofei,gems,gold, position, captcha) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				          obj = dbmanager.executeInsert(sql,
				                     new Object[] {first_name,last_name, date, name, password, email, trofei, gemme, gold, nation, code});
				          
				          ISFSObject success = new SFSObject();
				      	success.putUtfString("success" ,"User successfully registrated");
				      	send("register", success, user);
					} 
					
		catch(SQLException e) {
				
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL update error");
				send("register" , error, user);
			

			
	} catch (ParseException e) {
			trace("vediamo l'errore dato " + date);
			e.printStackTrace();
		}
					
				}
			} catch (SQLException e1) {
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL error");
				send("register" , error, user);
			}
			
			finally {
	        	try{
	        		connection.close();
	        	}catch (SQLException e){
	        		trace("A SQL Error occurred: " + e.getMessage());
	        	}
	        }
			
			
			
				}
	}

	
