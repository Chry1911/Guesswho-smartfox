package clanserver;

import com.smartfoxserver.v2.extensions.SFSExtension;

public class ClanExtension extends SFSExtension{

	
	
	
	public void init(){
		addRequestHandler("clan", ClanRegistrationHandler.class);
		
		addRequestHandler("findclan", FindClanHandler.class);
		
	    addRequestHandler("topclans", TopClanHandler.class);
	    
	    addRequestHandler("updaterole", UpdateRoleHandler.class);
	    
	    addRequestHandler("topusers", TopUsersHandler.class);
	    
	    addRequestHandler("returndeck", ReturnDeckHandler.class);
	    
	    addRequestHandler("clanmembers", ClanMemberHandler.class);
	    
	    addRequestHandler("userdetail", UserDetailHandler.class);
	    
	    addRequestHandler("clandetail", ClanDetailHandler.class);
	    
	    addRequestHandler("savingchat", ChatRegistrationHandler.class);
	    
	    addRequestHandler("returnchat", ReturnChatHandler.class);
	    
	    addRequestHandler("enterclan", EnterClanHandler.class);
	    
	    addRequestHandler("exitclan", ExitClanHandler.class);
	    
	    addRequestHandler("changedeckname", ChangeDeckNameHandler.class);
	    
	    addRequestHandler("returncollection", ReturnCollectionHandler.class);
	    
	    addRequestHandler("expulsionmember", ExpulsionUserHandler.class);
	}

	public void onDestroy(){
		super.destroy();
		
	}
	
	
	}
