package guiAddRemoveRoles;

import database.Database;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;

/*******
 * <p> Title: ControllerAddRemoveRoles Class. </p>
 * 
 * <p> Description: The Java/FX-based Add Remove Roles Page.  This class provides the controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page has one of the more complex Controller Classes due to the fact that the changing the
 * values of widgets changes the layout of the page.  It is up to the Controller to determine what
 * to do and it involves the proper elements from View Class for this GUI page.
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

public class ControllerAddRemoveRoles {
	
	/*-********************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	 */

	/**
	 * Default constructor is not used.
	 */
	public ControllerAddRemoveRoles() {
	}
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;		

	/**********
	 * <p> Method: doSelectUser() </p>
	 * 
	 * <p> Description: This method uses the ComboBox widget, fetches which item in the ComboBox
	 * was selected (a user in this case), and establishes that user and the current user, setting
	 * easily accessible values without needing to do a query. </p>
	 * 
	 */
	protected static void doSelectUser() {
		ViewAddRemoveRoles.theSelectedUser = 
				(String) ViewAddRemoveRoles.combobox_SelectUser.getValue();
		theDatabase.getUserAccountDetails(ViewAddRemoveRoles.theSelectedUser);
		setupSelectedUser();
	}
	
	
	/**********
	 * <p> Method: repaintTheWindow() </p>
	 * 
	 * <p> Description: This method determines the current state of the window and then establishes
	 * the appropriate list of widgets in the Pane to show the proper set of current values. </p>
	 * 
	 */
	protected static void repaintTheWindow() {
		// Clear what had been displayed
		ViewAddRemoveRoles.theRootPane.getChildren().clear();
		
		// Determine which of the two views to show to the user
		if (ViewAddRemoveRoles.theSelectedUser.compareTo("<Select a User>") == 0) {
			// Only show the request to select a user to be updated and the ComboBox
			ViewAddRemoveRoles.theRootPane.getChildren().addAll(
					ViewAddRemoveRoles.label_PageTitle, ViewAddRemoveRoles.label_UserDetails, 
					ViewAddRemoveRoles.button_UpdateThisUser, ViewAddRemoveRoles.line_Separator1,
					ViewAddRemoveRoles.label_SelectUser, ViewAddRemoveRoles.combobox_SelectUser, 
					ViewAddRemoveRoles.line_Separator4, ViewAddRemoveRoles.button_Return,
					ViewAddRemoveRoles.button_Logout, ViewAddRemoveRoles.button_Quit);
		}
		else {
			// Show all the fields as there is a selected user (as opposed to the prompt)
			ViewAddRemoveRoles.theRootPane.getChildren().addAll(
					ViewAddRemoveRoles.label_PageTitle, ViewAddRemoveRoles.label_UserDetails,
					ViewAddRemoveRoles.button_UpdateThisUser, ViewAddRemoveRoles.line_Separator1,
					ViewAddRemoveRoles.label_SelectUser,
					ViewAddRemoveRoles.combobox_SelectUser, 
					ViewAddRemoveRoles.label_CurrentRoles,
					ViewAddRemoveRoles.label_SelectRoleToBeAdded,
					ViewAddRemoveRoles.combobox_SelectRoleToAdd,
					ViewAddRemoveRoles.button_AddRole,
					ViewAddRemoveRoles.label_SelectRoleToBeRemoved,
					ViewAddRemoveRoles.combobox_SelectRoleToRemove,
					ViewAddRemoveRoles.button_RemoveRole,
					ViewAddRemoveRoles.line_Separator4, 
					ViewAddRemoveRoles.button_Return,
					ViewAddRemoveRoles.button_Logout,
					ViewAddRemoveRoles.button_Quit);
		}
		
		// Add the list of widgets to the stage and show it
		
		// Set the title for the window
		ViewAddRemoveRoles.theStage.setTitle("CSE 360 Foundation Code: Admin Operations Page");
		ViewAddRemoveRoles.theStage.setScene(ViewAddRemoveRoles.theAddRemoveRolesScene);
		ViewAddRemoveRoles.theStage.show();
	}
	
	
	/**********
	 * <p> Method: setupSelectedUser() </p>
	 * 
	 * <p> Description: This method fetches the current values for the widgets whose values change
	 * based on which user has been selected and any actions that the admin takes. </p>
	 * 
	 */
	private static void setupSelectedUser() {
		System.out.println("*** Entering setupSelectedUser");
		
		// Create the list of roles that could be added for the currently selected user (e.g., Do
		// not show a role to add that the user already has!)
		ViewAddRemoveRoles.addList.clear();
		ViewAddRemoveRoles.addList.add("<Select a role>");
		if (!theDatabase.getCurrentAdminRole())
			ViewAddRemoveRoles.addList.add("Admin");
		if (!theDatabase.getCurrentNewRole1())
			ViewAddRemoveRoles.addList.add("Contributor"); // Role1 = Contributor
		if (!theDatabase.getCurrentNewRole2())
			ViewAddRemoveRoles.addList.add("Viewer"); // Role2 = Viewer

		// Create the list of roles that could be removed for the currently selected user (e.g., Do
		// not show a role to remove that the user does not have!)
		ViewAddRemoveRoles.removeList.clear();
		ViewAddRemoveRoles.removeList.add("<Select a role>");
		if (theDatabase.getCurrentAdminRole())
			ViewAddRemoveRoles.removeList.add("Admin");
		if (theDatabase.getCurrentNewRole1())
			ViewAddRemoveRoles.removeList.add("Contributor");
		if (theDatabase.getCurrentNewRole2())
			ViewAddRemoveRoles.removeList.add("Viewer");
		
		// Create the list or roles that the user currently has with proper use of a comma between
		// items
		boolean notTheFirst = false;
		String theCurrentRoles = "";
		
		// Admin role - It can only be at the head of a list
		if (theDatabase.getCurrentAdminRole()) {
			theCurrentRoles += "Admin";
			notTheFirst = true;
		}
		
		// Roles 1 - It could be at the head of the list or later in the list
		if (theDatabase.getCurrentNewRole1()) {
			if (notTheFirst)
				theCurrentRoles += ", Contributor"; 
			else {
				theCurrentRoles += "Contributor";
				notTheFirst = true;
			}
		}

		// Roles 2 - It could be at the head of the list or later in the list
		if (theDatabase.getCurrentNewRole2()) {
			if (notTheFirst)
				theCurrentRoles += ", Viewer"; 
			else {
				theCurrentRoles += "Viewer";
				notTheFirst = true;
			}
		}

		// Given the above actions, populate the related widgets with the new values
		ViewAddRemoveRoles.label_CurrentRoles.setText("This user's current roles: " + 
				theCurrentRoles);		
		ViewAddRemoveRoles.setupComboBoxUI(ViewAddRemoveRoles.combobox_SelectRoleToAdd, "Dialog",
				16, 150, 280, 205);
		ViewAddRemoveRoles.combobox_SelectRoleToAdd.setItems(FXCollections.
				observableArrayList(ViewAddRemoveRoles.addList));
		ViewAddRemoveRoles.combobox_SelectRoleToAdd.getSelectionModel().clearAndSelect(0);		
		ViewAddRemoveRoles.setupButtonUI(ViewAddRemoveRoles.button_AddRole, "Dialog", 16, 150, 
				Pos.CENTER, 460, 205);
		ViewAddRemoveRoles.setupComboBoxUI(ViewAddRemoveRoles.combobox_SelectRoleToRemove, "Dialog",
				16, 150, 280, 275);
		ViewAddRemoveRoles.combobox_SelectRoleToRemove.setItems(FXCollections.
				observableArrayList(ViewAddRemoveRoles.removeList));
		ViewAddRemoveRoles.combobox_SelectRoleToRemove.getSelectionModel().select(0);

		// Debugging info
		if (ViewAddRemoveRoles.theSelectedUser.compareTo("<Select a User>") != 0) {
			System.out.println("Currently selected user: " + ViewAddRemoveRoles.theSelectedUser);
			System.out.println("Current roles: " + theCurrentRoles);
		}
		
		// Repaint the window showing this new values
		repaintTheWindow();

	}
	
	
	/**********
	 * <p> Method: performAddRole() </p>
	 * 
	 * <p> Description: This method adds a new role to the list of role in the ComboBox select
	 * list. </p>
	 * 
	 */
	protected static void performAddRole() {
		
		// Determine which item in the ComboBox list was selected
		ViewAddRemoveRoles.theAddRole =
				(String) ViewAddRemoveRoles.combobox_SelectRoleToAdd.getValue();
		
		// Debugging info
		System.out.println("Role to be added: " + ViewAddRemoveRoles.theAddRole);
		
		// If the selection is the list header (e.g., "<Select a role>") don't do anything
		if (ViewAddRemoveRoles.theAddRole.compareTo("<Select a role>") != 0) {
			
			// If an actual role was selected, update the database entry for that user for the role
			if (ViewAddRemoveRoles.theAddRole.compareTo("Admin") == 0) { 
				if (theDatabase.updateUserRole(ViewAddRemoveRoles.theSelectedUser,
						ViewAddRemoveRoles.theAddRole, "true") ) {
					ViewAddRemoveRoles.combobox_SelectRoleToAdd = new ComboBox <String>();
					ViewAddRemoveRoles.combobox_SelectRoleToAdd.setItems(FXCollections.
						observableArrayList(ViewAddRemoveRoles.addList));
					ViewAddRemoveRoles.combobox_SelectRoleToAdd.getSelectionModel().clearAndSelect(0);		
					setupSelectedUser();
				}
				// Debugging info
				System.out.println("Add role successful!");
			}
			else if (ViewAddRemoveRoles.theAddRole.compareTo("Contributor") == 0) { 
				// Contributor = Role1
				if (theDatabase.updateUserRole(ViewAddRemoveRoles.theSelectedUser, "Role1", "true") ) {
					ViewAddRemoveRoles.combobox_SelectRoleToAdd = new ComboBox <String>();
					ViewAddRemoveRoles.combobox_SelectRoleToAdd.setItems(FXCollections.
						observableArrayList(ViewAddRemoveRoles.addList));
					ViewAddRemoveRoles.combobox_SelectRoleToAdd.getSelectionModel().clearAndSelect(0);		
					setupSelectedUser();
				}
				// Debugging info
				System.out.println("Add role successful!");
			}
			else if (ViewAddRemoveRoles.theAddRole.compareTo("Viewer") == 0) { 
				// Viewer = Role2
				if (theDatabase.updateUserRole(ViewAddRemoveRoles.theSelectedUser, "Role2", "true") ) {
					ViewAddRemoveRoles.combobox_SelectRoleToAdd = new ComboBox <String>();
					ViewAddRemoveRoles.combobox_SelectRoleToAdd.setItems(FXCollections.
						observableArrayList(ViewAddRemoveRoles.addList));
					ViewAddRemoveRoles.combobox_SelectRoleToAdd.getSelectionModel().clearAndSelect(0);		
					setupSelectedUser();
				}
				// Debugging info
				System.out.println("Add role successful!");
			}
		}
	}
	
	
	/**********
	 * <p> Method: performRemoveRole() </p>
	 * 
	 * <p> Description: This method removes an existing role to the list of role in the ComboBox
	 * select list. </p>
	 * 
	 */
	protected static void performRemoveRole() {
		
		// Determine which item in the ComboBox list was selected
		ViewAddRemoveRoles.theRemoveRole = (String) ViewAddRemoveRoles.
				combobox_SelectRoleToRemove.getValue();
		
		// Debugging info
				System.out.println("Role to be removed: " + ViewAddRemoveRoles.theRemoveRole);
		
		// Check how many roles a user has assigned to them before removing anything
		int numRoles = 0;
		if (theDatabase.getCurrentAdminRole()) {
			numRoles++;
		}
		if (theDatabase.getCurrentNewRole1()) {
			numRoles++;
		}
		if (theDatabase.getCurrentNewRole2()) {
			numRoles++;
		}
		
		// If the selection is the list header (e.g., "<Select a role>") don't do anything
		if (ViewAddRemoveRoles.theRemoveRole.compareTo("<Select a role>") != 0) {
			
			// If the Admin performing this function selects self as the user to remove roles from,
			// do not allow them to remove their own Admin role
			if ((ViewAddRemoveRoles.theUser.getUserName().compareTo(ViewAddRemoveRoles.theSelectedUser) == 0)
					&& (ViewAddRemoveRoles.theRemoveRole.compareTo("Admin") == 0)) {
				// Debugging info
				System.out.println("Remove role failed - can't remove Admin role from self.");
				
				// Issue alert message
				ViewAddRemoveRoles.alertAdminRole.setTitle("Error: Role Not Removed");
				ViewAddRemoveRoles.alertAdminRole.setHeaderText("Cannot remove the Admin role from self.");
				ViewAddRemoveRoles.alertAdminRole.setContentText("Try removing a different role or return.");
				ViewAddRemoveRoles.alertAdminRole.showAndWait();
			}
			// Do not remove a role from a user if it is the only role they have assigned
		    else if (numRoles == 1) {
		    	// Debugging info
				System.out.println("Remove role failed - user only has a single role.");
		    	
				// Issue alert message
				ViewAddRemoveRoles.alertSingleRole.setTitle("Error: Role Not Removed");
				ViewAddRemoveRoles.alertSingleRole.setHeaderText("Cannot remove the only role a user has assigned to them.");
				ViewAddRemoveRoles.alertSingleRole.setContentText("Try adding a different role first or return.");
				ViewAddRemoveRoles.alertSingleRole.showAndWait();
			}
		    else {
		    	// If an actual role was selected, update the database entry for that user for the role
		    	if (ViewAddRemoveRoles.theRemoveRole.compareTo("Admin") == 0) { 
		    		if (theDatabase.updateUserRole(ViewAddRemoveRoles.theSelectedUser, 
							ViewAddRemoveRoles.theRemoveRole, "false") ) {
						ViewAddRemoveRoles.combobox_SelectRoleToRemove = new ComboBox <String>();
						ViewAddRemoveRoles.combobox_SelectRoleToRemove.setItems(FXCollections.
							observableArrayList(ViewAddRemoveRoles.addList));
						ViewAddRemoveRoles.combobox_SelectRoleToRemove.getSelectionModel().
							clearAndSelect(0);		
						setupSelectedUser();
					}
		    		// Debugging info
					System.out.println("Remove role successful!");
				}
				else if (ViewAddRemoveRoles.theRemoveRole.compareTo("Contributor") == 0) { 
					// Contributor = Role1
					if (theDatabase.updateUserRole(ViewAddRemoveRoles.theSelectedUser, "Role1", "false") ) {
						ViewAddRemoveRoles.combobox_SelectRoleToRemove = new ComboBox <String>();
						ViewAddRemoveRoles.combobox_SelectRoleToRemove.setItems(FXCollections.
							observableArrayList(ViewAddRemoveRoles.addList));
						ViewAddRemoveRoles.combobox_SelectRoleToRemove.getSelectionModel().
							clearAndSelect(0);		
						setupSelectedUser();
					}
					// Debugging info
					System.out.println("Remove role successful!");
				}
				else if (ViewAddRemoveRoles.theRemoveRole.compareTo("Viewer") == 0) { 
					// Viewer = Role2
					if (theDatabase.updateUserRole(ViewAddRemoveRoles.theSelectedUser, "Role2", "false") ) {
						ViewAddRemoveRoles.combobox_SelectRoleToRemove = new ComboBox <String>();
						ViewAddRemoveRoles.combobox_SelectRoleToRemove.setItems(FXCollections.
							observableArrayList(ViewAddRemoveRoles.addList));
						ViewAddRemoveRoles.combobox_SelectRoleToRemove.getSelectionModel().
							clearAndSelect(0);		
						setupSelectedUser();
					}
					// Debugging info
					System.out.println("Remove role successful!");
				}
		    }
		}
	}
	
	
	/**********
	 * <p> Method: performReturn() </p>
	 * 
	 * <p> Description: This method returns the user (who must be an Admin as only admins are the
	 * only users who have access to this page) to the Admin Home page. </p>
	 * 
	 */
	protected static void performReturn() {
		guiAdminHome.ViewAdminHome.displayAdminHome(ViewAddRemoveRoles.theStage,
				ViewAddRemoveRoles.theUser);
	}
	
	
	/**********
	 * <p> Method: performLogout() </p>
	 * 
	 * <p> Description: This method logs out the current user and proceeds to the normal login
	 * page where existing users can log in or potential new users with a invitation code can
	 * start the process of setting up an account. </p>
	 * 
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewAddRemoveRoles.theStage);
	}
	
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */
	protected static void performQuit() {
		System.exit(0);
	}
}