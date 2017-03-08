package gameserver;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class RestartHandler extends BaseClientRequestHandler
{
	@Override
	public void handleClientRequest(User user, ISFSObject params)
	{
		GuessWhoExtension gameExt = (GuessWhoExtension) getParentExtension();
		
		// Checks if two players are available and start game
		if (gameExt.getGameRoom().getSize().getUserCount() == 2)
			gameExt.startGame();
	}
}
