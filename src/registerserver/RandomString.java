package registerserver;

public class RandomString {
	
	/**
	 * 
	 * @param str
	 */
	private String str = null;
	
	
	/**
	 * metod randomString create a String alpahnumerics to send Via Email by the server to confirm the account
	 * @param alphaNumerics
	 */
	public RandomString() {
		 String alphaNumerics = "qwertyuiopasdfghjklzxcvbnm1234567890";
		    str = "";
		    for (int i = 0; i < 8; i++) {
		    str += alphaNumerics.charAt((int) (Math.random() * alphaNumerics.length()));
				}
	}
	
	/**
	 * metod that return the string of str
	 * @param str
	 */
	public String toString() {
		return str;
	}

}
