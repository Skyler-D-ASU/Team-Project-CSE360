package passwordRecognizer;

/*******

 * <p> Title: PasswordRecognizer Class </p>
 *
 * <p> Description: This class validates passwords entered into the
 * application. A valid password must satisfy the password requirements
 * established for the application.</p>
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Nicholas Yeremin
 *
 * @version 1.00 2026-09-17 Initial version
 */

public class PasswordRecognizer {

	public static String passwordErrorMessage = ""; //Error Message
	public static String passwordInput = ""; //
	public static int passwordIndexofError = -1; //
	public static boolean foundUpperCase = false; // UpperCase condition
	public static boolean foundLowerCase = false; // LowerCase conditon
	public static boolean foundNumericDigit = false; // NumericDigit condition
	public static boolean foundSpecialChar = false; // SpecialCharacter condition
	public static boolean foundLongEnough = false; // LongEnough condition
	private static String inputLine = ""; //
	private static char currentChar; // Current character
	private static int currentCharNdx; // Current character index
	private static boolean running; //running
	
	private static final int MAX_PASSWORD_LENGTH = 32;
	
	
	public static String evaluatePassword(String input) {
		// The following are the local variable used to perform the Directed Graph simulation
		passwordErrorMessage = "";
		passwordIndexofError = 0;			// Initialize the IndexofError
		inputLine = input;					// Save the reference to the input line as a global
		currentCharNdx = 0;					// The index of the current character
		
		if(input.length() <= 0) {
			return "*** Error *** The password is empty!";
		}
		
		if (input.length() > MAX_PASSWORD_LENGTH) {
			return "*** Error *** The password cannot exceed " +
		MAX_PASSWORD_LENGTH + " characters.";
		}
		
		// The input is not empty, so we can access the first character
		currentChar = input.charAt(0);		// The current character from the above indexed position

		// The Directed Graph simulation continues until the end of the input is reached or at some 
		// state the current character does not match any valid transition to a next state.  This
		// local variable is a working copy of the input.
		passwordInput = input;				// Save a copy of the input
		
		// The following are the attributes associated with each of the requirements
		foundUpperCase = false;				// Reset the Boolean flag
		foundLowerCase = false;				// Reset the Boolean flag
		foundNumericDigit = false;			// Reset the Boolean flag
		foundSpecialChar = false;			// Reset the Boolean flag
		foundNumericDigit = false;			// Reset the Boolean flag
		foundLongEnough = false;			// Reset the Boolean flag
		
		// This flag determines whether the directed graph (FSM) loop is operating or not
		running = true;						// Start the loop

		// The Directed Graph simulation continues until the end of the input is reached or at some
		// state the current character does not match any valid transition
		while (running) {
			// The cascading if statement sequentially tries the current character against all of
			// the valid transitions, each associated with one of the requirements
			if (currentChar >= 'A' && currentChar <= 'Z') {
				foundUpperCase = true;
			} else if (currentChar >= 'a' && currentChar <= 'z') {
				foundLowerCase = true;
			} else if (currentChar >= '0' && currentChar <= '9') {
				foundNumericDigit = true;
			} else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) {
				foundSpecialChar = true;
			} else {
				passwordIndexofError = currentCharNdx;
			}
			if (currentCharNdx >= 7) {
				foundLongEnough = true;
			}
			
			// Go to the next character if there is one
			currentCharNdx++;
			if (currentCharNdx >= inputLine.length())
				running = false;
			else
				currentChar = input.charAt(currentCharNdx);
		}
		
		// Construct a String with a list of the requirement elements that were found.
		String errMessage = "";
		if (!foundUpperCase)
			errMessage += "Password must contain at least one uppercase letter; \n";
		
		if (!foundLowerCase)
			errMessage += "Password must contain at least one lowercase letter; \n";
		
		if (!foundNumericDigit)
			errMessage += "Password must contain at least one numeric digit; \n";
			
		if (!foundSpecialChar)
			errMessage += "Password must contain at least one special character; \n";
			
		if (!foundLongEnough)
			errMessage += "Password must be at least 7 characters long; \n";
		
		if (errMessage == "")
			return "";
		
		// If it gets here, there something was not found, so return an appropriate message
		passwordIndexofError = currentCharNdx;
		return errMessage + "conditions were not satisfied";
	}
}
