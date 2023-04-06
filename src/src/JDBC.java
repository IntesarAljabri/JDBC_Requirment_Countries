package src;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map.Entry;

public class JDBC {

	static HashSet<String> table = new HashSet<String>();

	public static void backup() throws SQLException {

		try {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";"
					+ "encrypt=true;" + "trustServerCertificate=true";

			String user = "sa";
			String pass = "root";

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			Connection con = DriverManager.getConnection(url, user, pass);

			String sql = "BACKUP DATABASE " + Main.databaseName
					+ " TO DISK='C:\\Users\\Lenovo\\eclipse-workspace\\JDBC_Requirments_Countries\\database_name.bak'";

			PreparedStatement statement = con.prepareStatement(sql);
			statement.executeUpdate();

			statement.close();
			con.close();

			System.out.println("Backup Successfully :)");
		} catch (Exception ex) {
			System.err.println(ex);
		}

	}

	// ------------------------------------------------------------------------------------------------

	public static void deleteTable(String tableName) {

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";" + "encrypt=true;"
				+ "trustServerCertificate=true";

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url);

			String sql = "IF EXISTS (SELECT * FROM sys.tables WHERE name = '" + Main.tableName1 + "')\r\n" + "BEGIN\r\n"
					+ "Drop table " + Main.tableName1 + "\r\n" + "END\r\n";

			if (Main.tableName1.equals("Countries")) {
				table.remove(0);
			} else if (Main.tableName1.equals("Languages")) {
				table.remove(1);
			} else if (Main.tableName1.equals("Currencies")) {
				table.remove(2);
			} else if (Main.tableName1.equals("Translations")) {
				table.remove(3);
			} else if (Main.tableName1.equals("Demonyms")) {
				table.remove(4);
			} else if (Main.tableName1.equals("Flag")) {
				table.remove(5);
			}

			PreparedStatement statement = con.prepareStatement(sql);

			statement.executeUpdate();
			statement.close();
			con.close();

			System.out.println("Deleted Successfully :)");

		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	// ----------------------------------------------------------------------------------------------------

	public static void countriesTable() {

		try {

			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";"
					+ "encrypt=true;" + "trustServerCertificate=true";
			String user = "sa";
			String pass = "root";

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			Connection con = DriverManager.getConnection(url, user, pass);

			String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Countries')" + "BEGIN"
					+ "    CREATE TABLE Countries (" + " id INT NOT NULL PRIMARY KEY IDENTITY,"
					+ "        name_common VARCHAR(MAX)," + " name_official VARCHAR(MAX)," + "        tld VARCHAR(MAX),"
					+ " cca2 VARCHAR(MAX)," + "        ccn3 VARCHAR(MAX)," + " cca3 VARCHAR(MAX),"
					+ "        cioc VARCHAR(MAX)," + " independent TINYINT," + "        status VARCHAR(MAX),"
					+ " un_member TINYINT," + "        idd_root VARCHAR(MAX)," + "  idd_suffixes VARCHAR(MAX),"
					+ "        capital VARCHAR(MAX)," + " alt_spellings VARCHAR(MAX)," + "        region VARCHAR(MAX),"
					+ "  subregion VARCHAR(MAX)," + "		   latlng VARCHAR(MAX)," + " landlocked TINYINT,"
					+ "		   borders VARCHAR(MAX)," + " area VARCHAR(MAX)," + "		   flag VARCHAR(MAX),"
					+ "	 maps VARCHAR(MAX)," + "		   population INT," + " fifa VARCHAR(MAX),"
					+ "		   car_Signs VARCHAR(MAX)," + "	 car_Side VARCHAR(MAX),"
					+ "		   timezones VARCHAR(MAX)," + "	 continents VARCHAR(MAX)" + "  );" + "END;"
					+ "INSERT INTO Countries (name_common, name_official, tld, cca2, ccn3, cca3, cioc, independent, status, un_member, idd_root, idd_suffixes, capital, alt_spellings, region, subregion, latlng, "
					+ "landlocked, borders, area, flag, maps, population, fifa, car_Signs, car_Side, timezones, continents)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			table.add("Countries");

			String insertLangSql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Languages')" + "BEGIN"
					+ "    CREATE TABLE Languages (" + "        country_Name VARCHAR(MAX),"
					+ "        Language_Key VARCHAR(MAX)," + "        Language_Value VARCHAR(MAX)" + "    );"
					+ "END;" + "INSERT INTO Languages (country_Name, Language_Key, Language_Value)"
					+ "VALUES (?, ?, ?);";

			table.add("Languages");

			String insertCurrSql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Currencies')" + "BEGIN"
					+ "    CREATE TABLE Currencies (" + " country_Name VARCHAR(MAX),"
					+ "        Currencies_Key VARCHAR(MAX)," + "  Name VARCHAR(MAX)," + "        Symbol VARCHAR(MAX)"
					+ "  );" + "END;" + "INSERT INTO Currencies (country_Name, Currencies_Key, Name, Symbol)"
					+ "VALUES (?, ?, ?, ?);";

			table.add("Currencies");

			String insertTranSql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Translations')"
					+ "BEGIN" + "    CREATE TABLE Translations (" + "        country_Name VARCHAR(MAX),"
					+ "        Translations_Key VARCHAR(MAX)," + "        Official VARCHAR(MAX),"
					+ "        Common VARCHAR(MAX)" + "    );" + "END;"
					+ "INSERT INTO Translations (country_Name, Translations_Key, Official, Common)"
					+ "VALUES (?, ?, ?, ?);";

			table.add("Translations");

			String insertDemonymsSql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Demonyms')"
					+ "BEGIN" + "    CREATE TABLE Demonyms (" + "        country_Name VARCHAR(MAX),"
					+ "        Demonyms_Key VARCHAR(MAX)," + "        f VARCHAR(MAX),"
					+ "        m VARCHAR(MAX)" + "    );" + "END;"
					+ "INSERT INTO Demonyms (country_Name, Demonyms_Key, f, m)" + "VALUES (?, ?, ?, ?);";

			table.add("Demonyms");

			String insertFlafSql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Flag')" + "BEGIN"
					+ "    CREATE TABLE Flag (" + "        country_Name VARCHAR(MAX),"
					+ "        png VARCHAR(MAX)," + "        svg VARCHAR(MAX)," + "        alt VARCHAR(MAX)"
					+ "    );" + "END;" + "INSERT INTO Flag (country_Name, png, svg, alt)"
					+ "VALUES (?, ?, ?, ?);";

			table.add("Flag");

			PreparedStatement statement = con.prepareStatement(sql);
			PreparedStatement langStatement = con.prepareStatement(insertLangSql);
			PreparedStatement currenciesStatement = con.prepareStatement(insertCurrSql);
			PreparedStatement TranslationsStatement = con.prepareStatement(insertTranSql);
			PreparedStatement DemonymsStatement = con.prepareStatement(insertDemonymsSql);
			PreparedStatement FlagStatement = con.prepareStatement(insertFlafSql);

			statement.setString(1, API.countries.get(API.countries.size() - 1).name.common);
			statement.setString(2, API.countries.get(API.countries.size() - 1).name.official);

			String tldCombined = "";
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).tld.length; i++) {
				tldCombined = tldCombined + "(" + API.countries.get(API.countries.size() - 1).tld[i] + ") " + "/ ";

			}

			statement.setString(3, tldCombined);
			statement.setString(4, API.countries.get(API.countries.size() - 1).cca2);
			statement.setInt(5, API.countries.get(API.countries.size() - 1).ccn3);
			statement.setString(6, API.countries.get(API.countries.size() - 1).cca3);
			statement.setString(7, API.countries.get(API.countries.size() - 1).cioc);
			statement.setBoolean(8, API.countries.get(API.countries.size() - 1).independent);
			statement.setString(9, API.countries.get(API.countries.size() - 1).status);
			statement.setBoolean(10, API.countries.get(API.countries.size() - 1).unMember);

			String key = null;
			String name = null;
			String symbol = null;

			if (API.countries.get(API.countries.size() - 1).currencies != null
					&& !API.countries.get(API.countries.size() - 1).currencies.isEmpty()) {
				for (Entry<String, Currencies> entry : API.countries.get(API.countries.size() - 1).currencies
						.entrySet()) {
					key = entry.getKey();
					Currencies value = entry.getValue();
					name = value.name;
					symbol = value.symbol;
					currenciesStatement.setString(1, API.countries.get(API.countries.size() - 1).name.common);
					currenciesStatement.setString(2, key);
					currenciesStatement.setString(3, name);
					currenciesStatement.setString(4, symbol);
					currenciesStatement.executeUpdate();
				}

			}

			statement.setString(11, API.countries.get(API.countries.size() - 1).idd.root);

			String suffixesCombined = "";
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).idd.suffixes.length; i++) {
				suffixesCombined = suffixesCombined + "(" + API.countries.get(API.countries.size() - 1).idd.suffixes[i]
						+ ") " + "/ ";

			}
			statement.setString(12, suffixesCombined);

			String capitalCombined = "";
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).capital.length; i++) {
				capitalCombined = capitalCombined + "(" + API.countries.get(API.countries.size() - 1).capital[i] + ") "
						+ "/ ";

			}
			statement.setString(13, capitalCombined);

			String altSpellingsCombined = "";
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).altSpellings.length; i++) {
				altSpellingsCombined = altSpellingsCombined + "("
						+ API.countries.get(API.countries.size() - 1).altSpellings[i] + ") " + "/ ";
			}
			statement.setString(14, altSpellingsCombined);

			statement.setString(15, API.countries.get(API.countries.size() - 1).region);
			statement.setString(16, API.countries.get(API.countries.size() - 1).subregion);

			if (API.countries.get(API.countries.size() - 1).languages != null
					&& !API.countries.get(API.countries.size() - 1).languages.isEmpty()) {
				for (Entry<String, String> entry : API.countries.get(API.countries.size() - 1).languages.entrySet()) {
					String langKey = entry.getKey();
					String langValue = entry.getValue();
					langStatement.setString(1, API.countries.get(API.countries.size() - 1).name.common);
					langStatement.setString(2, langKey);
					langStatement.setString(3, langValue);
					langStatement.executeUpdate();
				}
			}

			String transKey = null;
			String transOfficial = null;
			String transCommon = null;

			if (API.countries.get(API.countries.size() - 1).translation != null
					&& !API.countries.get(API.countries.size() - 1).translation.isEmpty()) {
				for (Entry<String, Translation> entry : API.countries.get(API.countries.size() - 1).translation
						.entrySet()) {
					transKey = entry.getKey();
					Translation value = entry.getValue();
					transOfficial = value.official;
					transCommon = value.common;
					TranslationsStatement.setString(1, API.countries.get(API.countries.size() - 1).name.common);
					TranslationsStatement.setString(2, transKey);
					TranslationsStatement.setString(3, transOfficial);
					TranslationsStatement.setString(4, transCommon);
					TranslationsStatement.executeUpdate();
				}

			}

			String latlngCombined = "";
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).latlng.length; i++) {
				latlngCombined = latlngCombined + "(" + API.countries.get(API.countries.size() - 1).latlng[i] + ") "
						+ "/ ";
			}
			statement.setString(17, latlngCombined);

			statement.setBoolean(18, API.countries.get(API.countries.size() - 1).landlocked);

			String bordersCombined = "";
			if (API.countries.get(API.countries.size() - 1).borders != null) {
				for (int i = 0; i < API.countries.get(API.countries.size() - 1).borders.length; i++) {
					bordersCombined = bordersCombined + "(" + API.countries.get(API.countries.size() - 1).borders[i]
							+ ") " + "/ ";
				}
			}
			statement.setString(19, bordersCombined);

			statement.setDouble(20, API.countries.get(API.countries.size() - 1).area);

			String demonymsKey = null;
			String f = null;
			String m = null;
			if (API.countries.get(API.countries.size() - 1).demonyms != null
					&& !API.countries.get(API.countries.size() - 1).demonyms.isEmpty()) {
				for (Entry<String, Demonyms> entry : API.countries.get(API.countries.size() - 1).demonyms.entrySet()) {
					demonymsKey = entry.getKey();
					Demonyms value = entry.getValue();
					f = value.f;
					m = value.m;
					DemonymsStatement.setString(1, API.countries.get(API.countries.size() - 1).name.common);
					DemonymsStatement.setString(2, demonymsKey);
					DemonymsStatement.setString(3, f);
					DemonymsStatement.setString(4, m);
					DemonymsStatement.executeUpdate();
				}

			}

			statement.setString(21, API.countries.get(API.countries.size() - 1).flag);

			String mapsCombined = "";
			mapsCombined = mapsCombined + "(" + API.countries.get(API.countries.size() - 1).maps.googleMaps + ") "
					+ "/ ";
			mapsCombined = mapsCombined + "(" + API.countries.get(API.countries.size() - 1).maps.openStreetMaps + ") "
					+ "/ ";
			statement.setString(22, mapsCombined);

			statement.setLong(23, API.countries.get(API.countries.size() - 1).population);

			statement.setString(24, API.countries.get(API.countries.size() - 1).fifa);

			String carSignsCombined = "";

			// Loop through each car sign in the last country's car sign array
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).car.signs.size(); i++) {

				// Enclose the car sign in parentheses and add it to the combined string
				// carSignsCombined = carSignsCombined + "(" +
				// API.countries.get(API.countries.size() - 1).car.signs[i] + ") " + "/ ";
			}
			statement.setString(25, carSignsCombined);
			statement.setString(26, API.countries.get(API.countries.size() - 1).car.side);

			String timeCombined = "";
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).timezones.length; i++) {
				timeCombined = timeCombined + "(" + API.countries.get(API.countries.size() - 1).timezones[i] + ") "
						+ "/ ";
			}
			statement.setString(27, timeCombined);

			String continentsCombined = "";
			for (int i = 0; i < API.countries.get(API.countries.size() - 1).continents.length; i++) {
				continentsCombined = continentsCombined + "("
						+ API.countries.get(API.countries.size() - 1).continents[i] + ") " + "/ ";
			}
			statement.setString(28, continentsCombined);

			FlagStatement.setString(1, API.countries.get(API.countries.size() - 1).name.common);
			FlagStatement.setString(2, API.countries.get(API.countries.size() - 1).flags.png);
			FlagStatement.setString(3, API.countries.get(API.countries.size() - 1).flags.svg);
			FlagStatement.setString(4, API.countries.get(API.countries.size() - 1).flags.alt);
			FlagStatement.executeUpdate();

			statement.executeUpdate();

			// Close the PreparedStatement object
			statement.close();
			currenciesStatement.close();
			langStatement.close();
			FlagStatement.close();

			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	// -------------------------------------------------------------------------------------------------------

	public static void SearchTable() {
		try {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=" + Main.databaseName + ";"
					+ "encrypt=true;" + "trustServerCertificate=true";

			Connection conn = DriverManager.getConnection(url);

			DatabaseMetaData metadata = conn.getMetaData();
			String[] types = { "TABLE" };
			ResultSet resultSet = metadata.getTables(null, null, "%", types);

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				if (!tableName.equalsIgnoreCase("trace_xe_action_map")
						&& !tableName.equalsIgnoreCase("trace_xe_event_map")) {
					System.out.println("Table Name:  " + tableName);
					table.add(tableName);
				}
			}

			// Close the connection
			conn.close();
		} catch (Exception ex) {
		}
	}

}