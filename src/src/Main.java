package src;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	static Scanner scan = new Scanner(System.in);
	public static String databaseName;
	static String tableName1;

	public static void main(String[] args) throws SQLException {
		boolean menue = true;
		System.out.print("Enter Database Name:      ");
		databaseName = scan.next();

		while (menue) {
			System.out.println("|--------------------|");
			System.out.println("|[1] backup          |");
			System.out.println("|[2] Removing Table  |");
			System.out.println("|[3] Print Countries |");
			System.out.println("|[4] Search          |");
			System.out.println("|--------------------|");
			System.out.print("Enter Number:  ");
			String option = scan.next();

			switch (option) {
			case "1":
				JDBC.backup();
				break;

			case "2":
				try {  
					
					// Establish a connection to the database
					String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + databaseName + ";"
							+ "encrypt=true;" + "trustServerCertificate=true";

			        String user = "sa";
			        String pass = "root";
			        
			        
			        Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	                DriverManager.registerDriver(driver);
			        
					Connection conn = DriverManager.getConnection(url ,user, pass);

					// Get all tables in the database and store their names in a list
					DatabaseMetaData metadata = conn.getMetaData();
					String[] types = { "TABLE" };
					ResultSet resultSet = metadata.getTables(null, null, "%", types);

					while (resultSet.next()) {
						String tableName = resultSet.getString("TABLE_NAME");
						if (!tableName.equalsIgnoreCase("trace_xe_action_map")
								&& !tableName.equalsIgnoreCase("trace_xe_event_map")) {
							JDBC.table.add(tableName);

						}
					}

					// Close the connection
					conn.close();
				} catch (Exception ex) {
				}

				// If there are no tables in the database, display a message and exit the
				// program
				if ((JDBC.table).isEmpty()) {
					System.out.println("There are no tables in the database.");
					break;
				}

				// Otherwise, display a list of tables and prompt the user to enter a table name
				// to delete
				JDBC.SearchTable();
				System.out.print("Enter the name of the table you want to delete: ");
				String tableName = scan.next();

				// If the user entered an invalid table name, display an error message and exit
				// the program
				if (!(JDBC.table).contains(tableName)) {
					System.out.println("Invalid table name. Please try again.");
					break;
				}

				// Otherwise, delete the table from the database and remove its name from the
				// list of tables
				JDBC.deleteTable(tableName);
				JDBC.table.remove(tableName);
				System.out.println(tableName + " has been deleted from the database.");
				break;

			case "3":
				API.APICountries();
				break;

			case "4":
				// Call the API method to get the list of countries
				API.APICountriesName();

				// Print the list of countries
				System.out.println("List of countries:");
				for (String countryName : API.countriesList) {
					System.out.println(countryName);
				}

				// Ask the user to enter the name of the country
				System.out.println("Enter the name of the country you want to view details of:");
				String countryName = scan.next();

				// Check if the user entered a two-word country name and replace spaces with "+"
				if (countryName.contains(" ")) {
					countryName = countryName.replace(" ", "+");
					System.out.println("Note: For two-word country names, use '+' instead of spaces");
				}

				// Initialize the countryFound variable to false
				boolean countryFound = false;

				// Iterate through the list of countries
				for (int i = 0; i < API.countries.size(); i++) {
					// Check if the name of the current country matches the user's input
					if (API.countries.get(i).name.official.equalsIgnoreCase(countryName)) {
						// Print out the details of the country
						System.out.println("Official Country Name: " + API.countries.get(i).name.official);
						System.out.println("Common Country Name: " + API.countries.get(i).name.common);
						System.out.print("Top-Level Domain (tld): ");
						for (int j = 0; j < API.countries.get(i).tld.length; j++) {
							System.out.print(API.countries.get(i).tld[j] + ", ");
						}
						System.out.println("\nCountry Code Alpha-2 (CCA2): " + API.countries.get(i).cca2);
						System.out.println("Country Code Numeric-3 (CCN3): " + API.countries.get(i).ccn3);
						System.out.println("Country Code Alpha-3 (CCA3): " + API.countries.get(i).cca3);
						System.out.println("Country Code for International Olympic Committee (CIOC): "
								+ API.countries.get(i).cioc);
						System.out.println("Independent: " + API.countries.get(i).independent);
						System.out.println("Status: " + API.countries.get(i).status);
						System.out.println("UN Member: " + API.countries.get(i).unMember);
						System.out.print("Currencies: ");
						for (int j = 0; j < API.countries.get(i).currencies.size(); j++) {
							System.out.print(API.countries.get(i).currencies.get(j).name + ", ");
						}
						System.out.println(
								"\n-------------------------------------------------------------------------------------------");
						// Set the countryFound variable to true and break out of the loop
						countryFound = true;
						break;
					}
				}
				// If the country was not found, print an error message
				if (!countryFound) {
					System.out.println("Country not found. Please enter a valid country name.");
				}
				break;

			}

		}
	}

}
