package registerserver;

import java.sql.SQLException;

import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;


public class UserRegistrationHandler extends BaseClientRequestHandler {
	
	Object obj = null;
	public void handleClientRequest(User user, ISFSObject params){

		trace("Sto chiedendo di registrare uno user al server");
		
		String name = params.getUtfString("name");
        String password = params.getUtfString("password");
		String email = params.getUtfString("email");
		int trofei = params.getInt("trofei");
		String nation = params.getUtfString("nation");
		
		IDBManager dbmanager = getParentExtension().getParentZone().getDBManager();
		
		
		//String sql2 = "Select username from Users where email = " + email ;
		//trace(sql2);
		
			try {
				trace("sono entrato nel primo try");
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
					
					String sql = "INSERT into Users(username, password, email, trofei, position) values (?, ?, ?, ?, ?)";

					try{
						trace("sono entrato nel secondo try");
					
				          obj = dbmanager.executeInsert(sql,
				                     new Object[] {name, password, email, trofei, nation});
				          
				          ISFSObject success = new SFSObject();
				      	success.putUtfString("success" ,"User successfully registrated");
				      	send("register", success, user);
					} 
					
		catch(SQLException e) {
				
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL update error");
				send("register" , error, user);
			

			
	}
					
				}
			} catch (SQLException e1) {
				ISFSObject error = new SFSObject();
				error.putUtfString("error", "MySQL error");
				send("register" , error, user);
			}
			
				}
	}

	
