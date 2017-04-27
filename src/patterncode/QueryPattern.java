package patterncode;

public class QueryPattern {
	
	/**
	 * 
	 * @param str
	 */
	
	private String query_pattern = null;
	private String query_pattern2 = null;
	

	public QueryPattern(){
		query_pattern = "or guesswho.users.id_user = guesswho.clan.utente_21 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_22 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_23 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_24 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_25 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_26 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_27 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_28 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_29 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_30 ";
		
		query_pattern2 = "or guesswho.users.id_user = guesswho.clan.utente_31 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_32 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_33 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_34 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_35 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_36 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_37 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_38 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_39 "
				+ "or guesswho.users.id_user = guesswho.clan.utente_40 ";
		
	}
	/**
	 * metod that return the string of str
	 * @param str
	 */
	public String toString() {
		return query_pattern;
	}
	
	/**
	 * metod that return the string of str
	 * @param str
	 */
	public String toString2() {
		return query_pattern2;
	}
}
