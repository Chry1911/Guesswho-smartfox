package gameserver;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import com.smartfoxserver.v2.extensions.SFSExtension;

public class GuessWhoExtension extends SFSExtension{

	private User whoseTurn;
	private volatile boolean gameStarted;
	private LastGameEndResponse lastGameEndResponse;
	private int moveCount;
	
	private final String version = "1.0";
	ISFSObject params;
	String dettaglio = "";
	@Override
	public void init() {
		// TODO Auto-generated method stub
		trace("GuessWho Extension for SFS2X started, rel. " + version);
		
		moveCount = 0;
		
		//handlers 
		  addEventHandler(SFSEventType.USER_VARIABLES_UPDATE, ReadVariableHandler.class);
		  addRequestHandler("ready", ReadyHandler.class);
		
		  addEventHandler(SFSEventType.USER_DISCONNECT, OnUserGoneHandler.class);
		  addEventHandler(SFSEventType.USER_LEAVE_ROOM, OnUserGoneHandler.class);
		  addEventHandler(SFSEventType.SPECTATOR_TO_PLAYER, OnSpectatorToPlayerHandler.class);
		  
		
	}
	
	//method on destroy
	@Override
	public void destroy() 
	{
		super.destroy();
		trace("GuessWho game destroyed!");
	}
	
	//dice il turno di chi deve giocare
	User getWhoseTurn()
    {
	    return whoseTurn;
    }
	
	//setta il turno
	void setTurn(User user)
	{
		whoseTurn = user;
	}
	
	//aggiorna il turno
	void updateTurn()
	{
		whoseTurn = getParentRoom().getUserByPlayerId( whoseTurn.getPlayerId() == 1 ? 2 : 1 );
	}
	
	//conta le mosse
	public int getMoveCount()
    {
	    return moveCount;
    }
	
	//aggiorna la mossa
	public void increaseMoveCount()
	{
		++moveCount;
	}
	
	//partita iniziata
	boolean isGameStarted()
	{
		return gameStarted;
	}
	
	//inizia la partita
	void startGame()
	{
		if (gameStarted)
			throw new IllegalStateException("Game is already started!");
		
		lastGameEndResponse = null;
		gameStarted = true;
		//gameBoard.reset();  // resetta il campo di gioco
		
		User player1 = getParentRoom().getUserByPlayerId(1);
		User player2 = getParentRoom().getUserByPlayerId(2);
		
		// No turn assigned? Let's start with player 1
		if (whoseTurn == null)
			whoseTurn = player1;
		
		// Send START event to client
		ISFSObject resObj = new SFSObject();
		resObj.putInt("t", whoseTurn.getPlayerId());
		resObj.putUtfString("p1n", player1.getName());
		resObj.putInt("p1i", player1.getId());
		resObj.putUtfString("p2n", player2.getName());
		resObj.putInt("p2i", player2.getId());
		
		send("start", resObj, getParentRoom().getUserList());
	}
	
	//stoppa la partita
	void stopGame()
	{
		stopGame(false);
	}
	
	//ferma
	void stopGame(boolean resetTurn)
	{
		gameStarted = false;
		moveCount = 0;
		whoseTurn = null;
	}
	
	//da la stanza di gioco
	Room getGameRoom()
	{
		return this.getParentRoom();
	}
	
	//risposta dal gioco
	LastGameEndResponse getLastGameEndResponse()
    {
	    return lastGameEndResponse;
    }
	
	void setLastGameEndResponse(LastGameEndResponse lastGameEndResponse)
    {
	    this.lastGameEndResponse = lastGameEndResponse;
    }
	
	//aggiorna chi sta giocando
	void updateSpectator(User user)
	{
		ISFSObject resObj = new SFSObject();
		
		User player1 = getParentRoom().getUserByPlayerId(1);
		User player2 = getParentRoom().getUserByPlayerId(2);
		
		resObj.putInt("t", whoseTurn == null ? 0 : whoseTurn.getPlayerId());
		resObj.putBool("status", gameStarted);
		//resObj.putSFSArray("board", gameBoard.toSFSArray());  //replace with our board
		
		if (player1 == null)
			resObj.putInt("p1i", 0); // <--- indicates no P1
		else
		{
			resObj.putInt("p1i", player1.getId());
			resObj.putUtfString("p1n", player1.getName());
		}
		
		if (player2 == null)
			resObj.putInt("p2i", 0); // <--- indicates no P2
		else
		{
			resObj.putInt("p2i", player2.getId());
			resObj.putUtfString("p2n", player2.getName());
			
		}
		
		send("specStatus", resObj, user);
	}

}
