package registerserver;

import com.smartfoxserver.v2.extensions.SFSExtension;



public class RegisterExtension extends SFSExtension{

	
	
	
	public void init(){
		addRequestHandler("register", UserRegistrationHandler.class);
		
		
		
		//questo evento parte quando viene richiamata la perdita della password
	    //addRequestHandler("passwordmissing", PasswordMissingHandler.class);
	    
	   // addRequestHandler("updatepassword", UpdatePasswordHandler.class);
	    
	    // addRequestHandler("updatecaptcha", UpdateCaptchaHandler.class);
	    
	    
		
		//initSignUpAssistant();
		//initLoginAssistant();
		//addEventHandler(SFSEventType.USER_LOGIN, LoginEventHandler.class);
	}

	public void onDestroy(){
		super.destroy();
		
	}
	
	
	}
