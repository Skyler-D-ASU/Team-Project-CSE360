package guiAdminHome;

import database.Database;
import javafx.scene.control.Button;
import entityClasses.User;
import guiNewAccount.ViewNewAccount;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import guiTools.EmailAddressRecognizer;
import guiTools.DummyAccountGeneration;
/*******
 * <p> Title: GUIAdminHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Admin Home Page.  This class provides the controller actions
 * basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page contains a number of buttons that have not yet been implemented.  WHen those buttons
 * are pressed, an alert pops up to tell the user that the function associated with the button has
 * not been implemented. Also, be aware that What has been implemented may not work the way the
 * final product requires and there maybe defects in this code.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation *  
 */

public class ControllerAdminHome {
	
	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/
	
	/**
	 * Default constructor is not used.
	 */
	public ControllerAdminHome() {
	}
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/**********
	 * <p> 
	 * 
	 * Title: performInvitation () Method. </p>
	 * 
	 * <p> Description: Protected method to send an email inviting a potential user to establish
	 * an account and a specific role. </p>
	 */
	protected static void performInvitation () {
		// Verify that the email address is valid - If not alert the user and return
		String emailAddress = ViewAdminHome.text_InvitationEmailAddress.getText();
		if (invalidEmailAddress(emailAddress)) {
			return;
		}
		String errStr = EmailAddressRecognizer.checkEmailAddress(emailAddress);
		if (errStr.compareTo("") != 0) {
			System.out.println(errStr);
			ViewAdminHome.alertEmailError.setTitle("Password Error");
			ViewAdminHome.alertEmailError.setHeaderText(errStr);
			ViewAdminHome.alertEmailError.setContentText("Correct the Email and try again.");
			ViewAdminHome.alertEmailError.showAndWait();
			return;
		}
		
		
		// Check to ensure that we are not sending a second message with a new invitation code to
		// the same email address.  
		if (theDatabase.emailaddressHasBeenUsed(emailAddress)) {
			ViewAdminHome.alertEmailError.setContentText(
					"An invitation has already been sent to this email address.");
			ViewAdminHome.alertEmailError.showAndWait();
			return;
		}
		
		// Inform the user that the invitation has been sent and display the invitation code
		String theSelectedRole = (String) ViewAdminHome.combobox_SelectRole.getValue();
		String invitationCode = theDatabase.generateInvitationCode(emailAddress,
				theSelectedRole);
		String msg = "Code: " + invitationCode + " for role " + theSelectedRole + 
				" was sent to: " + emailAddress;
		System.out.println(msg);
		ViewAdminHome.alertEmailSent.setContentText(msg);
		ViewAdminHome.alertEmailSent.showAndWait();
		
		// Update the Admin Home pages status
		ViewAdminHome.text_InvitationEmailAddress.setText("");
		ViewAdminHome.label_NumberOfInvitations.setText("Number of outstanding invitations: " + 
				theDatabase.getNumberOfInvitations());
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: manageInvitations () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void manageInvitations () {
		System.out.println("\n*** WARNING ***: Manage Invitations Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.setTitle("*** WARNING ***");
		ViewAdminHome.alertNotImplemented.setHeaderText("Manage Invitations Issue");
		ViewAdminHome.alertNotImplemented.setContentText("Manage Invitations Not Yet Implemented");
		ViewAdminHome.alertNotImplemented.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: setOnetimePassword () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void setOnetimePassword() {
		guiSetOneTimePassword.ViewSetOneTimePassword.displaySetOneTimePassword(
				ViewAdminHome.theStage,
				ViewAdminHome.theUser);
	}

	/**********
	 * <p> 
	 * 
	 * Title: deleteUser () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void deleteUser() {
		// Builds Stage for user confirming they want to remove the specified userw
		
		Stage DeleteUserStage = new Stage();
		DeleteUserStage.setTitle("Are You Sure?");
		
		ViewAdminHome.RemovingUserPrompt.setTitle("User Removal Window");
		ViewAdminHome.RemovingUserPrompt.setHeaderText("Delete User Issue");
		ViewAdminHome.RemovingUserPrompt.getEditor().clear(); // Clears textbox for next input
		
		// Display GUI Elements
		ViewAdminHome.RemovingUserPrompt.setContentText("Enter User to be deleted");
		ViewAdminHome.RemovingUserPrompt.showAndWait();
			
		// Get user input result
		String username = ViewAdminHome.RemovingUserPrompt.getResult();
		
		if (username == null) { // user clicked cancel
		}else if (theDatabase.getCurrentUsername().equals(username) ) { // Username is the same as currently logged in Admin
			ViewAdminHome.alertCannotDeleteUser.setHeaderText("User cannot be the same as current Admin");
			ViewAdminHome.alertCannotDeleteUser.showAndWait();
	
		} else if ( !theDatabase.doesUserExist(username) ) { // username does not exist
			ViewAdminHome.alertCannotDeleteUser.setHeaderText("User is not found in system");
			ViewAdminHome.alertCannotDeleteUser.showAndWait();

		} else {
			//DummyAccountGeneration.createDummyUsers(); // generates dummy account names for testing (see DummyAccountGeneration.java for my info)
			
			// Build and display UI for a user that is allowed to be deleted
			Text confirmationTxt = new Text("Are You Sure You Would Like To Remove " + username);
			
			Button confirmButton = new Button("Yes");
			confirmButton.setOnAction(event -> {theDatabase.deleteUser(username); 
												DeleteUserStage.hide();} );
			
			Button denyButton = new Button("No");
			denyButton.setOnAction(event -> {DeleteUserStage.hide();} );
			
			HBox layout = new HBox(10);
			layout.getChildren().addAll(confirmationTxt, confirmButton, denyButton);
			layout.setPadding(new Insets(15));
	
			Scene scene = new Scene(layout, 400, 100);
			DeleteUserStage.setScene(scene);
			DeleteUserStage.show();
			}
		}

	/**********
	 * <p> 
	 * 
	 * Title: listUsers () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	@SuppressWarnings("unchecked")
	protected static void listUsers() {
		
		//Create window
		Stage userListStage = new Stage();
		userListStage.setTitle("User List");
		
		//Create user table to display
		TableView<User> userTable = new TableView<>();
		
		//Create columns for usernames, names, emails, and roles in that order
		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
		usernameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUserName()));
		
		TableColumn<User, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(data -> new SimpleStringProperty(formatName(data.getValue())));

		TableColumn<User, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmailAddress()));

		TableColumn<User, String> rolesColumn = new TableColumn<>("Role");
		rolesColumn.setCellValueFactory(data -> new SimpleStringProperty(formatRoles(data.getValue())));
		
		//Add columns to table
		userTable.getColumns().addAll(usernameColumn, nameColumn, emailColumn, rolesColumn);
		
		//Load users from database
		userTable.getItems().addAll(theDatabase.getAllUsers());
		
		//Close button
		Button closeButton = new Button("Close");
		closeButton.setOnAction(event -> userListStage.close());
		
		VBox layout = new VBox(10);
		
		layout.getChildren().addAll(userTable, closeButton);
		
		layout.setPadding(new Insets(15));
		
		//Create scene and display window
		Scene scene = new Scene(layout, 750, 450);
		
		
		
		userListStage.setScene(scene);
		userListStage.show();
		
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: addRemoveRoles () Method. </p>
	 * 
	 * <p> Description: Protected method that allows an admin to add and remove roles for any of
	 * the users currently in the system.  This is done by invoking the AddRemoveRoles Page. There
	 * is no need to specify the home page for the return as this can only be initiated by and
	 * Admin.</p>
	 */
	protected static void addRemoveRoles() {
		guiAddRemoveRoles.ViewAddRemoveRoles.displayAddRemoveRoles(ViewAdminHome.theStage, 
				ViewAdminHome.theUser);
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: invalidEmailAddress () Method. </p>
	 * 
	 * <p> Description: Protected method that is intended to check an email address before it is
	 * used to reduce errors.  The code currently only checks to see that the email address is not
	 * empty.  In the future, a syntactic check must be performed and maybe there is a way to check
	 * if a properly email address is active.</p>
	 * 
	 * @param emailAddress	This String holds what is expected to be an email address
	 */
	protected static boolean invalidEmailAddress(String emailAddress) {
		if (emailAddress.length() == 0) {
			ViewAdminHome.alertEmailError.setContentText(
					"Correct the email address and try again.");
			ViewAdminHome.alertEmailError.showAndWait();
			return true;
		}
		return false;
	}
	
	
	/***********
	 * <p>
	 * 
	 * Title: formatRoles () Method. </p>
	 * 
	 * <p> Description: Private method to format roles from booleans to descriptive string format. </p>
	 * 
	 * @param user  Holds User object to format roles for
	 * @return
	 */
	private static String formatRoles(User user) {
		//Start StringBuilder
		StringBuilder roles = new StringBuilder();
		
		//Append roles if applicable.
		if(user.getAdminRole()) {
			roles.append("Admin");
		}
		
		if(user.getNewRole1()) {
			if (roles.length() > 0) {
				roles.append(", ");
			}
			
			roles.append("Student");
			
		}
		
		if(user.getNewRole2()) {
			if (roles.length() > 0) {
				roles.append(", ");
			}
			
			roles.append("Reviewer");
			
		}
		
		//Return roles formatted to descriptive string
		return roles.toString();
	}
	
	/*********
	 * <p>
	 * 
	 * Title: formatName () Method. </p>
	 * 
	 * <p> Description: Private method to format names into a displayable full name string format. </p>
	 * 
	 * @param user   Holds User object to format names for
	 * @return
	 */
	private static String formatName(User user) {
		
		//String starting with first name
		String name = user.getFirstName();
		
		//If the user has a middle name and it isn't blank, add it to string
		if(user.getMiddleName() != null && !user.getMiddleName().isBlank()) {
			
			name += " " + user.getMiddleName();
			
		}
		
		//Add last name
		name += " " + user.getLastName();		
		
		//Return full name
		return name;
	
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: performLogout () Method. </p>
	 * 
	 * <p> Description: Protected method that logs this user out of the system and returns to the
	 * login page for future use.</p>
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewAdminHome.theStage);
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: performQuit () Method. </p>
	 * 
	 * <p> Description: Protected method that gracefully terminates the execution of the program.
	 * </p>
	 */
	protected static void performQuit() {
		System.exit(0);
	}
}
