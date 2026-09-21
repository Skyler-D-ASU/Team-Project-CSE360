package guiSetNewPassword;

import applicationMain.FoundationsMain;
import entityClasses.User;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * </p> Title: ViewSetNewPassword Class </p>
 *
 * <p> Description: This class provides the JavaFX user interface for a user
 * to create a new password. </p>
 * 
 *  @author Nicholas Yeremin
 *  
 *  @version 1.00		2026-09-17 Initial Version
 */

public class ViewSetNewPassword {
	//Window Dimensions
		private static double width =
				applicationMain.FoundationsMain.WINDOW_WIDTH;
		private static double height =
				applicationMain.FoundationsMain.WINDOW_HEIGHT;
		
		//Currently logged in user.
		protected static User theUser;
		
		//The label used to tell the Admin the Title of the page.
		protected static Label label_titlePage = new Label("Set a New Password Page");
		
		//The label used the display the User's details.
		protected static Label label_UserDetails = new Label();
		
		//The label used to display a new password.
		protected static Label label_NewPassword = new Label("New Password:");
		
		//The field where you enter your new password.
		protected static PasswordField text_NewPassword =  new PasswordField();
		
		//The label that displays your confirmed password.
		protected static Label label_ConfirmPassword = new Label("Confirm Password:");
		
		//The field where you confirm your password.
		protected static PasswordField text_ConfirmPassword = new PasswordField();
		
		//The button that sets your new password.
		protected static Button button_SetPassword = new Button("Set Password");
		
		//Alert the used if an error occurs.
		protected static Alert alertPasswordError = new Alert(AlertType.ERROR);
		
		//Seperator Lines to keep the UI consistent.
		protected static Line line1 = new Line();
		protected static Line line2 = new Line();
		
		protected static Stage theStage;
		protected static Pane theRootPane = new Pane();
		protected static Scene theSetNewPasswordScene = new Scene(theRootPane, width, height);
		protected static Button returnButton = new Button("Return");
		
		public static void displaySetNewPassword(Stage ps, User user) {
			
			theStage = ps;
			theUser = user;
			
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
			
			//New Password
			setupLabelUI(label_NewPassword, "Arial",
			        20, 250, Pos.BASELINE_LEFT, 20, 160);

			text_NewPassword.setStyle("-fx-font: 16 Dialog;");
			text_NewPassword.setMinWidth(250);
			text_NewPassword.setLayoutX(280);
			text_NewPassword.setLayoutY(155);

			//Confirm Password
			setupLabelUI(label_ConfirmPassword, "Arial",
			        20, 250, Pos.BASELINE_LEFT, 20, 220);
			
			text_ConfirmPassword.setStyle("-fx-font: 16 Dialog;");
			text_ConfirmPassword.setMinWidth(250);
			text_ConfirmPassword.setLayoutX(280);
			text_ConfirmPassword.setLayoutY(215);

			//Set Password Button
			button_SetPassword.setFont(Font.font("Dialog", 18));
			button_SetPassword.setMinWidth(250);
			button_SetPassword.setAlignment(Pos.CENTER);
			button_SetPassword.setLayoutX(280);
			button_SetPassword.setLayoutY(270);
			
			button_SetPassword.setOnAction(e -> {
				ControllerSetNewPassword.performSetPassword();
				});
			
			//Password Error Handling
			alertPasswordError.setTitle("Password Error");
			alertPasswordError.setHeaderText("Unable to Set New Password");
			
			//Line 2
			line2.setStartX(20);
			line2.setStartY(525);
			line2.setEndX(width - 20);
			line2.setEndY(525);
			
			theRootPane.getChildren().addAll(label_titlePage, label_UserDetails, line1, 
					label_NewPassword, text_NewPassword, label_ConfirmPassword,
					text_ConfirmPassword, button_SetPassword, line2);
			
			theStage.setTitle("Set New Password");
			theStage.setScene(theSetNewPasswordScene);
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
