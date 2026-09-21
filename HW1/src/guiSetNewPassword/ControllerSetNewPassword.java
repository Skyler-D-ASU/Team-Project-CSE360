package guiSetNewPassword;

import applicationMain.FoundationsMain;
import database.Database;
import passwordRecognizer.PasswordRecognizer;

/**
 * </p> Title: ControllerSetNewPassword Class </p>
 *
 * <p> Description: This class provides the functionally of creating
 * a new password and return the user back to the login page. </p>
 * 
 *  @author Nicholas Yeremin
 *  
 *  @version 1.00		2026-09-17 Initial Version
 */

public class ControllerSetNewPassword {
	 
	private static Database theDatabase = FoundationsMain.database;

	    protected static void performSetPassword() {
	        String newPassword =
	                ViewSetNewPassword.text_NewPassword.getText();

	        String confirmPassword =
	                ViewSetNewPassword.text_ConfirmPassword.getText();

	        // Make sure both fields have input
	        if (newPassword.isBlank() || confirmPassword.isBlank()) {
	        	ViewSetNewPassword.alertPasswordError.setContentText(
	                    "Please enter and confirm your new password.");
	            ViewSetNewPassword.alertPasswordError.showAndWait();
	            return;
	        }

	        // Validate the new password
	        String passwordError =
	                PasswordRecognizer.evaluatePassword(newPassword);
	        if (!passwordError.isEmpty()) {
	            System.out.println(passwordError);
	            return;
	        }

	        // Make sure the passwords match
	        if (!newPassword.equals(confirmPassword)) {
	        	ViewSetNewPassword.alertPasswordError.setContentText(
	                    "The passwords do not match. Try again.");
	            ViewSetNewPassword.alertPasswordError.showAndWait();
	            return;
	        }

	        // Update the password
	        boolean success = theDatabase.setPermanentPassword(
	                ViewSetNewPassword.theUser.getUserName(),
	                newPassword);

	        if (success) {
	            System.out.println("Password successfully changed.");
	            guiUserLogin.ViewUserLogin.
	            displayUserLogin(ViewSetNewPassword.theStage);
	        } else {
	        	ViewSetNewPassword.alertPasswordError.setContentText(
	                    "Unable to change the password. Try again.");
	        }
	    }
}