package guiTools;

import java.sql.SQLException;

import database.Database;
import entityClasses.User;

/*******
 * <p> Title: DummyAccountGeneration Class. </p>
 * 
 * <p> Description: DummyAccountGeneration is a class made up of a single method with public variables
 * 					allowing programmers to speed up feature testing with automated data generation 
 * 					for the database.</p>
 * 
 * <p> Copyright: Skyler DeLongchamp © 2026 </p>
 * 
 * @author Skyler DeLongchamp
 * 
 * @version 1.00		2026-09-18 Initial version
 *  
 */


public class DummyAccountGeneration {
	/**
	 * Default Constructor
	 */
	public DummyAccountGeneration() {}
	/** Default Setup for database */
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	/** static list of names to create new users with */
	public static String[] dummyUserList = {"Fred", "Johnny", "Marilin", "Kindle", "John_Rover", "Admin", "Kim", "Jeremy", "Matthew", "Nicholas"};
	/** Is this user an admin? */
	public static boolean admin = false;
	
	/**********
	 * <p> Method: dummyUserList() </p>
	 * 
	 * <p> Description: This method is only ran to generate a list of dummy account 
	 * to speed up feature testing</p>
	 * 
	 */
	
	public static void eacrteDummyUsers() {
		
			for (int i = 0; i < dummyUserList.length; i++) {
				
				User user = new User(dummyUserList[i], "" , "", "", "", "", "", admin, false, 
						false);
				
				try {
					theDatabase.register(user);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	};

