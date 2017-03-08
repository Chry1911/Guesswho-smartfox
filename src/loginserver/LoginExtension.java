package loginserver;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;



public class LoginExtension extends SFSExtension{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		addEventHandler(SFSEventType.USER_LOGIN, LoginEventHandler.class);
		
		
	}
	
	public void onDestroy(){
		super.destroy();
		
	}

}
