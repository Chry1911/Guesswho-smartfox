package gameserver;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class ReadyHandler extends BaseClientRequestHandler
{
	@Override
	public void handleClientRequest(User user, ISFSObject params)
	{
		GuessWhoExtension gameExt = (GuessWhoExtension) getParentExtension();
		String dettaglio = "";
	
		if (user.isPlayer())
		{
			// Checks if two players are available and start game
			if (gameExt.getGameRoom().getSize().getUserCount() == 2)
				
				dettaglio = params.getUtfString("nome");
			UserVariable avatarPic = new SFSUserVariable("pic", dettaglio);
			  trace("Parametro passato : " + avatarPic.toString());
				gameExt.startGame();
		}
		
		else
		{
			gameExt.updateSpectator(user);
			
			LastGameEndResponse endResponse = gameExt.getLastGameEndResponse();
			
			// If game has ended send the outcome
			if (endResponse != null)
				send(endResponse.getCmd(), endResponse.getParams(), user);
		}
	}
}