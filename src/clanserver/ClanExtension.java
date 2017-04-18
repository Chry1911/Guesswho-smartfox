package clanserver;

import com.smartfoxserver.v2.extensions.SFSExtension;

public class ClanExtension extends SFSExtension{

	
	
	
	public void init(){
		addRequestHandler("clan", ClanRegistrationHandler.class);
		
		addRequestHandler("findclan", FindClanHandler.class);
		
	    addRequestHandler("topclans", TopClanHandler.class);
	    
	    addRequestHandler("topclansnation", TopClanNationHandler.class);
	    
	    addRequestHandler("topusers", TopUsersHandler.class);
	    
	    addRequestHandler("topusersnation", TopUsersNationHandler.class);
	    
	    addRequestHandler("clanmembers", ClanMemberHandler.class);
	    
	    addRequestHandler("userdetail", UserDetailHandler.class);
	    
	    addRequestHandler("clandetail", ClanDetailHandler.class);
	    
	    addRequestHandler("savingchat", ChatRegistrationHandler.class);
	    
	    addRequestHandler("returnchat", ReturnChatHandler.class);
	    
	  
	}

	public void onDestroy(){
		super.destroy();
		
	}
	
	
	}
