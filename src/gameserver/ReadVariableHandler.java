package gameserver;



import java.util.*;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class ReadVariableHandler  extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		 User sender = (User) event.getParameter(SFSEventParam.USER);
	     //String dettaglio = (String)event.getParameter(SFSEventParam.VARIABLES);
	     
	     Map<String, UserVariable> varsMap = (Map<String ,UserVariable>) event.getParameter(SFSEventParam.VARIABLES_MAP);
	     
	     trace("il mio dato " + varsMap.toString());
		
	}

}
