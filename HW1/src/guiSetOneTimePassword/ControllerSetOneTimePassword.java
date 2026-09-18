package guiSetOneTimePassword;

import database.Database;
import passwordRecognizer.PasswordRecognizer;
/*******
 * <p> Title: ControllerSetOneTimePassword. </p>
 * 
 * <p>
 * Description: This class controls the Set One-Time Password functionality
 * available to administrators.
 * 
 * The administrator selects an existing user and assigns a one-time password
 * to that user's account. The one-time password temporarily replaces the
 * user's existing password. After the user successfully logs in using the
 * one-time password, the system requires the user to establish a new
 * password.
 *
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared as "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * @author Nicholas Yeremin
 * 
 * @version 1.00		2026-09-14 Initial version
 */

public class ControllerSetOneTimePassword {

	//Default Constructor is not used.
	public ControllerSetOneTimePassword() {
	}
	
	//Reference to the database.
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	protected static void doSelectUser() {
		ViewSetOneTimePassword.theSelectedUser =
				ViewSetOneTimePassword.combobox_SelectUser.getValue();
		
		if (ViewSetOneTimePassword.theSelectedUser == null) {
			return;
		}
		
		if (ViewSetOneTimePassword.theSelectedUser.compareTo("<Select a User>") == 0) {
			return;
		}
		
		theDatabase.getUserAccountDetails(ViewSetOneTimePassword.theSelectedUser);
	}
	
	protected static void performSetOneTimePassword() {
		
		//Grag selcted user
		String selectedUser = ViewSetOneTimePassword.
				combobox_SelectUser.getValue();
		
		//Getting OTP entered by admin
		String oneTimePassword = ViewSetOneTimePassword.
				text_OneTimePassword.getText();
		
		//Making sure the admin has selcted a user.
		if (selectedUser == null || selectedUser.equals("<Select a User>")) {
			 ViewSetOneTimePassword.alertPasswordError.setContentText("Please select a user.");
			    ViewSetOneTimePassword.alertPasswordError.showAndWait();
			return;
		}
		
		//Making sure the admin has entered a one-time password.
		if (oneTimePassword == null || oneTimePassword.isBlank()) {
			ViewSetOneTimePassword.alertPasswordError.setContentText("Please enter a one-time password.");
			ViewSetOneTimePassword.alertPasswordError.showAndWait();
			return;
		}
		
		//Validating the one-time password.
		String passwordError = PasswordRecognizer.
				evaluatePassword(oneTimePassword);
		
		if (!passwordError.isEmpty()) {
			System.out.println(passwordError);
			return;
		}
		
		//Temp test
		boolean success = theDatabase.
				setOneTimePassword(selectedUser, oneTimePassword);
		
		if (success) {
			System.out.println("One-time password successfully set for "
					+ selectedUser);
		}
		else {
			System.out.println("Unable to set one-time password.");
		}
	}
	
	protected static void performReturn() {
		guiAdminHome.ViewAdminHome.displayAdminHome(
				ViewSetOneTimePassword.theStage,
				ViewSetOneTimePassword.theUser);
	}
}
