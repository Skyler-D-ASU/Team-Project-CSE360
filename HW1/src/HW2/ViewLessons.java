package HW2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.List;
import java.util.Optional;

import database.Database;
import entityClasses.LessonsLearned;
import entityClasses.User;


/*******
 * <p> Title: ViewLessons Class. </p>
 * 
 * <p> Description: The Java/FX-based Contributor page for editting, viewing, and deleting
 * 	   their own lesson posts from the database.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter, Skyler DeLongchamp
 * 
 * @version 1.00		2025-08-20 Initial version
 * @version 1.01		2026-10-23 Expanded Functionality
 *  
 */

public class ViewLessons {
	
	/*-*******************************************************************************************

	Attributes
	
	 */
	
	// These are the application values required by the user interface
	/** Sets Window Width*/
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	/** Sets Window Height*/
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;


	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	/** label for the page title */
	protected static Label label_PageTitle = new Label();
	/** label for user details */
	protected static Label label_UserDetails = new Label();
	/** button for updating account */
	protected static Button button_UpdateThisUser = new Button("Account Update");
	
	/** This is a separator and it is used to partition the GUI for various tasks */
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);

	/** GUI ARea 2: This is a stub, so there are no widgets here.  For an actual role page, this are
	    would contain the widgets needed for the user to play the assigned role. */
	protected static Button button_NewPost = new Button("New Post");
	
	// Post display area
	/** Box for all lessons */
	private VBox LessonsBox = new VBox(15);
	/** scroll pane to allow storing all lessons boxes */
	private ScrollPane LessonsScroll = new ScrollPane(LessonsBox);
	/** Is the visuals updating */
	private boolean currentlyUpdating = false;
	
	// Self post
	/** title for self post */
	private TextArea PostTitle = new TextArea();
	/** text body of self post */
	private TextArea PostBody = new TextArea();
	/** text Area for time taken */
	private TextArea timeTakenArea = new TextArea();
	
	// Alert for posts
	/** Invalid post alert */
	protected static Alert alertPostInvalid = new Alert(AlertType.INFORMATION);
	/** delete post alert */
	protected static Alert alertDeletePost = new Alert(AlertType.CONFIRMATION);
	
	/** This is a separator and it is used to partition the GUI for various tasks */
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application, 
	/** Returning to the user's home page. */
	protected static Button button_ReturnToHomePage = new Button("Return");
	/** button for logging out. */
	protected static Button button_Logout = new Button("Logout");
	/** Button for quitting application. */
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	/**Used to determine if instantiation of the class is needed*/
	private static ViewLessons theView;

	/** Reference for the in-memory database so this package has access */
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	public LessonsLearned LessonsLearned = new LessonsLearned();
	
	/** The Stage that JavaFX has established for us	*/
	protected static Stage theStage;			
	/** The Pane that holds all the GUI widgets*/
	protected static Pane theRootPane;	
	/** The current logged in User*/
	protected static User theUser;				
	
	/** The shared Scene each invocation populates*/
	private static Scene theViewContributorHomeScene;	
	/** Admin: 1; Contributor: 2; Role2: 3 */
	protected static final int theRole = 2;		

	/**********
	 * <p> Method: displayLessonsHome(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the Role1 Home page to be displayed.
	 * 
	 * It first sets up every shared attributes so we don't have to pass parameters.
	 * 
	 * It then checks to see if the page has been setup.  If not, it instantiates the class, 
	 * initializes all the static aspects of the GIUI widgets (e.g., location on the page, font,
	 * size, and any methods to be performed).
	 * 
	 * After the instantiation, the code then populates the elements that change based on the user
	 * and the system's current state.  It then sets the Scene onto the stage, and makes it visible
	 * to the user.
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param user specifies the User for this GUI and it's methods
	 * 
	 */
	public static void displayLessonsHome(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		Database theDatabase = applicationMain.FoundationsMain.database;
		
		// If not yet established, populate the static aspects of the GUI
		theView = new ViewLessons();		// Instantiate singleton if needed
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());
				
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Contributor Lessons Page");
		theStage.setScene(theViewContributorHomeScene);
		theStage.show();
		
	}
	
	/**********
	 * <p> Method: ViewLessons() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object.</p>
	 * 
	 * <p> This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayRole2Home method.</p>
	 * 
	 */	
	private ViewLessons() {

		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theViewContributorHomeScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Populate the window with the title and other common widgets and set their static state
		
		theRootPane.setStyle("-fx-background-color: #1A1A1A");
		line_Separator1.setStroke(Color.WHITE);
		
		// GUI Area 1
		
		// Set the title for the window
		label_PageTitle.setText("Lessons Home Page");
		setupLabelUI(label_PageTitle, "Franklin Gothic Heavy", 28, width, Pos.CENTER, 0, 10, "white");

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Franklin Gothic Demi", 20, width, Pos.BASELINE_LEFT, 20, 55, "#9885FF");
		
		setupButtonUI(button_UpdateThisUser, "Franklin Gothic Demi", 18, 170, Pos.CENTER, 610, 45, "white", "#0080FF");
		button_UpdateThisUser.setOnAction((_) -> {ControllerLessons.performUpdate(); });
		
		// GUI Area 2
		
		// Will contain fields of threads etc
		
		setupButtonUI(button_NewPost, "Franklin Gothic Demi", 18, 125, Pos.CENTER, 110, 450, "white", "#008537");
		
		button_NewPost.setOnAction((_) -> {LessonsBox = LessonsLearned.Publish(PostTitle, PostBody, timeTakenArea, LessonsBox); 
										   LessonsLearned.refreshFeed(LessonsBox);});
		
		// Display Area
		LessonsBox.setPadding(new Insets(8, 8, 8, 0));
		LessonsScroll.setLayoutX(350);
		LessonsScroll.setLayoutY(105);
		LessonsScroll.setMinWidth(400);
		LessonsScroll.setMaxWidth(400);
		LessonsScroll.setMinHeight(400);
		LessonsScroll.setMaxHeight(400);
		LessonsScroll.setFitToWidth(true);
		LessonsScroll.setStyle("-fx-background: #1A1A1A;");
		
		// Input Area
		SetupPostBoxUI(PostTitle, PostBody, timeTakenArea);
		
		PostTitle.setFont(Font.font("Franklin Gothic Book", 16));
		PostTitle.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		
		PostBody.setFont(Font.font("Franklin Gothic Book", 16));
		PostBody.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		
		timeTakenArea.setFont(Font.font("Franklin Gothic Book", 16));
		timeTakenArea.setStyle("-fx-prompt-text-fill: grey; -fx-text-fill: white;"
				+ " -fx-control-inner-background: #1A1A1A; -fx-border-color: grey; "
				+ "-fx-border-width: 2px;");
		
		// GUI Area 3
		
		line_Separator4.setStroke(Color.WHITE);
		
		setupButtonUI(button_ReturnToHomePage, "Franklin Gothic Demi", 18, 200, Pos.CENTER, 50, 540, "white", "#3B3B3B");
		button_ReturnToHomePage.setOnAction((_) -> {ControllerLessons.goToUserHomePage(theStage, theUser);}	);
		
        setupButtonUI(button_Logout, "Franklin Gothic Demi", 18, 200, Pos.CENTER, 300, 540, "white", "#3B3B3B");
        button_Logout.setOnAction((_) -> {ControllerLessons.performLogout(); });
        
        setupButtonUI(button_Quit, "Franklin Gothic Demi", 18, 200, Pos.CENTER, 550, 540, "white", "#3B3B3B");
        button_Quit.setOnAction((_) -> {ControllerLessons.performQuit(); });

		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
         theRootPane.getChildren().addAll(
			label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
	        line_Separator4, button_ReturnToHomePage, button_Logout, button_Quit, 
	        button_NewPost, LessonsBox, LessonsScroll, PostBody, PostTitle, timeTakenArea);
         LessonsLearned.refreshFeed(LessonsBox);
	}	
	

	
	/*********************************************************************************************

	Helper methods to reduce code length

	 *********************************************************************************************/

	/**********
	 * Private local method to initialize the standard fields for a label
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * @param c		the text fill style
	 */
	
	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y, String c){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);	
		l.setStyle("-fx-text-fill: " + c);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * @param tc	The text color of the button
	 * @param bc	The background color of the button
	 */
	private static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y, String tc, String bc){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
		b.setStyle("-fx-text-fill: " + tc + "; -fx-background-color: " + bc + "; -fx-background-radius: 10px;");
	}
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 * 
	 * @param t		The textField Object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * @param e 	The text is editable (True/False)
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
		t.setStyle("-fx-text-fill: white; -fx-background-color: #1A1A1A;"
				+ " -fx-border-color: grey; -fx-border-width: 2px; -fx-border-radius: 10px;");
	}
	
	/********** 
	 * Private local method to initialize the Post box UI
	 * 
	 * @param Title title of post
	 * @param Body body of post
	 * @param timeTakenArea time taken area of post
	 */
	private static void SetupPostBoxUI(TextArea Title, TextArea Body, TextArea timeTakenArea ){
		Title.setMinWidth(100);
		Title.setMaxWidth(250);
		Title.setMinHeight(25);
		Title.setMaxHeight(50);
		Title.setLayoutX(50);
		Title.setLayoutY(105);
		Title.setPrefRowCount(8);
		Title.setWrapText(true);
		Title.setPromptText("<Insert Title Information here>");
			
		Body.setMinWidth(100);
		Body.setMaxWidth(250);
		Body.setMinHeight(100);
		Body.setMaxHeight(125);
		Body.setLayoutX(50);
		Body.setLayoutY(165);
		Body.setPrefRowCount(8);
		Body.setWrapText(true);
		Body.setPromptText("<Insert lessons learned body information here>");
		
		timeTakenArea.setMinWidth(100);
		timeTakenArea.setMaxWidth(250);
		timeTakenArea.setMinHeight(25);
		timeTakenArea.setMaxHeight(50);
		timeTakenArea.setLayoutX(50);
		timeTakenArea.setLayoutY(300);
		timeTakenArea.setPrefRowCount(8);
		timeTakenArea.setWrapText(true);
		timeTakenArea.setPromptText("<Insert time taken data here in hours>");
	}
	
}
