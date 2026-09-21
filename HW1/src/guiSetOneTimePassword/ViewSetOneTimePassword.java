package guiSetOneTimePassword;

import applicationMain.FoundationsMain;
import database.Database;
import entityClasses.User;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * </p> Title: ViewSetOneTimePassword Class </p>
 *
 * <p> Description: This class provides the JavaFX user interface for an administrator
 * to set a one-time password for an existing user.
 * 
 * The administrator selects a user from a ComboBox and can then generate and
 * assign a one-time password to that user's account. </p>
 * 
 *  @author Nicholas Yeremin
 *  
 *  @version 1.00		2026-09-14 Initial Version
 */

public class ViewSetOneTimePassword {
	
	//Window Dimensions
	private static double width =
			applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height =
			applicationMain.FoundationsMain.WINDOW_HEIGHT;

	//Reference to the database.
	private static Database theDatabase = FoundationsMain.database;
	
	//Currently logged in user.
	protected static User theUser;
	
	//The username selected from the drop-box.
	protected static String theSelectedUser = "";
	
	//The label used to tell the Admin to select a user.
	protected static Label label_SelectUser = new Label("Select a user:");
	
	//The label used to tell the Admin the Title of the page.
	protected static Label label_titlePage = new Label("Set a One-Time Password Page");
	
	//The label used the display the User's details.
	protected static Label label_UserDetails = new Label();
	
	//The label used for the OneTimePassword button.
	protected static Label label_OneTimePassword = new Label("One-Time Password");
	
	//Where the admin enters the one-time password.
	protected static TextField text_OneTimePassword = new TextField();
	
	//Button used to assign OTP
	protected static Button button_SetOneTimePassword = new Button("Set a One-Time Password");
	
	//Alert message for password error.
	protected static Alert alertPasswordError = new Alert(AlertType.ERROR);
	
	//Seperator Lines to keep the UI consistent.
	protected static Line line1 = new Line();
	protected static Line line2 = new Line();
	
	//Drop-box containing the users in the database.
	protected static ComboBox<String> combobox_SelectUser = new ComboBox<String>();
	
	//Setting Up Drop-box to display a list of all users.
	public static void setupUserComboBox() {
		List<String> userList = theDatabase.getUserList();
		
		//Removing the admin from the drop-box
		userList.remove(theUser.getUserName());
		
		combobox_SelectUser.setItems(FXCollections.observableArrayList(userList));
		
		combobox_SelectUser.getSelectionModel().select(0);
		
		combobox_SelectUser.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> {
			ControllerSetOneTimePassword.doSelectUser();
		});
	}
	
	protected static Stage theStage;
	protected static Pane theRootPane = new Pane();
	protected static Scene theSetOneTimePasswordScene = new Scene(theRootPane, width, height);
	protected static Button returnButton = new Button("Return");
	
	public static void displaySetOneTimePassword(Stage ps, User user) {
		
		theStage = ps;
		theUser = user;
		
		setupUserComboBox();
		
		theRootPane.getChildren().clear();
		
		//Title Page
		setupLabelUI(label_titlePage, "Arial", 28, width, Pos.CENTER, 0, 5);
		
		//Display Username
		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
		
		//Line 1
		line1.setStartX(20);
		line1.setStartY(95);
		line1.setEndX(width - 20);
		line1.setEndY(90);
		
		//Select User
		setupLabelUI(label_SelectUser, "Arial", 20, 300, Pos.BASELINE_LEFT, 20, 130);
		
		//Dropbox Selection
		combobox_SelectUser.setStyle("-fx-font: 16 Dialog;");
		combobox_SelectUser.setMinWidth(250);
		combobox_SelectUser.setLayoutX(280);
		combobox_SelectUser.setLayoutY(125);
		
		//One-Time Password
		setupLabelUI(label_OneTimePassword, "Arial",
				20, 250, Pos.BASELINE_LEFT,20,220);
		
		text_OneTimePassword.setStyle("-fx-font: 16 Dialog;");
		text_OneTimePassword.setMinWidth(250);
		text_OneTimePassword.setLayoutX(280);
		text_OneTimePassword.setLayoutY(215);
		
		//Button to set OTP
		button_SetOneTimePassword.setFont(Font.font("Dialog", 18));
		button_SetOneTimePassword.setMinWidth(250);
		button_SetOneTimePassword.setAlignment(Pos.CENTER);
		button_SetOneTimePassword.setLayoutX(280);
		button_SetOneTimePassword.setLayoutY(260);
		
		button_SetOneTimePassword.setOnAction(e ->
		{ControllerSetOneTimePassword.performSetOneTimePassword();});
		
		//Line 2
		line2.setStartX(20);
		line2.setStartY(525);
		line2.setEndX(width - 20);
		line2.setEndY(525);
		
		//Password Error Handling
		alertPasswordError.setTitle("One-Time Password Error");
		alertPasswordError.setHeaderText("Unable to Set One-Time Password");
		
		//Return Button
		returnButton.setFont(Font.font("Dialog", 18));
		returnButton.setMinWidth(210);
		returnButton.setAlignment(Pos.CENTER);
		returnButton.setLayoutX(20);
		returnButton.setLayoutY(540);
		
		returnButton.setOnAction(e -> {ControllerSetOneTimePassword.
			performReturn();});
	
		theRootPane.getChildren().addAll(label_titlePage, label_UserDetails, line1, 
				label_SelectUser, combobox_SelectUser, label_OneTimePassword, 
				text_OneTimePassword, button_SetOneTimePassword, line2, returnButton);
		
		theStage.setTitle("Set One-Time Password");
		theStage.setScene(theSetOneTimePasswordScene);
		theStage.show();
	}
	
	private static void setupLabelUI(Label l, String ff, double f,
			double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}
}
