package registerserver;

public class SendEmailException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	
	/**
	 * Instantiate a new <code>ExistingUsernameException</code> 
	 * @param s 
	 */
	public SendEmailException(String s){
		super(s);
		msg = s;
	}
	
	/**
	 * @return A string representing the exception
	 */
	public String toString() {
		return msg;
	}

}