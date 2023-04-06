package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;
import com.google.gson.Gson;

public class API {
	
	static ArrayList<String> countriesList = new ArrayList<String>();
	static ArrayList <Countries> countries = new ArrayList<Countries>();
	
	public static void APICountriesName() {
        String apiUrl = "https://restcountries.com/v3.1/all";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder json = new StringBuilder();
            
            String line;
            while ((line = br.readLine()) != null) {
                json.append(line);
            }
            
            conn.disconnect();
            
            Gson gson = new Gson();
            Countries[] countriesArr = gson.fromJson(json.toString(), Countries[].class);
            
            // Do something with the countries array
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

	
	public static void APICountries() {
		String apiUrl = "https://restcountries.com/v3.1/all";
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuilder json = new StringBuilder();

			while ((output = br.readLine()) != null) {
				json.append(output);
			}

			conn.disconnect();

			Gson gson = new Gson();
			Countries countriesArr[] = gson.fromJson(json.toString(), Countries[].class);
			
			

			// Use myObj for further processing
			for (int i = 0; i < countriesArr.length; i++) {
			    System.out.println("NAME:");
			    Countries newCountry = countriesArr[i];
			
			    System.out.println("  Common Name:   " + newCountry.name.common);
			    System.out.println("  Official Name: " + newCountry.name.official);

			    System.out.println("  Native Names:");
			    if (newCountry.name.nativeName != null && !newCountry.name.nativeName.isEmpty()) {
			        for (Entry<String, NativeName> entry : newCountry.name.nativeName.entrySet()) {
			            String key = entry.getKey();
			            NativeName value = entry.getValue();
			            System.out.println("  *" + key + "*");
			            System.out.println("    " + "Official Name: " + value.official);
			            System.out.println("    " + "Common Name:   " + value.common);
			        }
			    }
			
			    if (newCountry.tld != null) {
			        System.out.println("  Top Level Domains (TLDs):");
			        for (String tld : newCountry.tld) {
			            System.out.println("    " + tld);
			        }
			    }
			
			    System.out.println("  ISO 3166-1 Alpha-2 Code: " + newCountry.cca2);
			    System.out.println("  ISO 3166-1 Numeric Code: " + newCountry.ccn3);
			    System.out.println("  ISO 3166-1 Alpha-3 Code: " + newCountry.cca3);
			    System.out.println("  International Olympic Committee Code: " + newCountry.cioc);
			    System.out.println("  Independent: " + newCountry.independent);
			    System.out.println("  Status: " + newCountry.status);
			    System.out.println("  United Nations Member: " + newCountry.unMember);

			    System.out.println("  Currencies: ");
			    if (newCountry.currencies != null && !newCountry.currencies.isEmpty()) {
			        for (Entry<String, Currencies> entry : newCountry.currencies.entrySet()) {
			            String key = entry.getKey();
			            Currencies value = entry.getValue();
			            System.out.println("  *" + key + "*");
			            System.out.println("    " + "Name: " + value.name);
			            System.out.println("    " + "Symbol:   " + value.symbol);
			        }
			    }

			    System.out.println("  International Direct Dialing (IDD) Codes:");
			    if (newCountry.idd != null) {
			        System.out.println("    " + "Root: " + newCountry.idd.root);
			        if (newCountry.idd.suffixes != null) {
			            for (String idd : newCountry.idd.suffixes) {
			                System.out.println("    " + "Suffixes: " + idd);
			            }
			        }
			    }

			    if (newCountry.capital != null) {
			        System.out.println("  Capital(s):");
			        for (String capital : newCountry.capital) {
			            System.out.println("    " + capital);
			        }
			    }

			    if (newCountry.altSpellings != null) {
			        System.out.println("  Alternative Spellings:");
			        for (String altSpellings : newCountry.altSpellings) {
			            System.out.println("    " + altSpellings);
			        }
			    }

			    System.out.println("  Region: " + newCountry.region);
			    System.out.println("  Sub-Region: " + newCountry.subregion);
			
			
			 // Print the country's official languages and their names
			    System.out.println("Languages:");
			    if (newCountry.languages != null && !newCountry.languages.isEmpty()) {
			      for (Entry<String, String> entry : newCountry.languages.entrySet()) {
			        String key = entry.getKey();
			        String value = entry.getValue();
			        System.out.println("  " + key + ": " + value);
			      }
			    }

			    // Print the country's translations
			    System.out.println("Translations:");
			    if (newCountry.translation != null && !newCountry.translation.isEmpty()) {
			      for (Entry<String, Translation> entry : newCountry.translation.entrySet()) {
			        String key = entry.getKey();
			        Translation value = entry.getValue();
			        System.out.println("  " + key + ":");
			        System.out.println("    Official: " + value.official);
			        System.out.println("    Common: " + value.common);
			      }
			    }

			    // Print the country's latitude and longitude
			    if (newCountry.latlng != null) {
			      System.out.println("Latitude and Longitude:");
			      System.out.println("  Latitude: " + newCountry.latlng[0]);
			      System.out.println("  Longitude: " + newCountry.latlng[1]);
			    }
			    
			 // Check if the country's latitude and longitude are available, and if so, print them out
			    if (newCountry.latlng != null) {
			        System.out.println("Latitude and longitude:");
			        for (double latlng : newCountry.latlng) {
			            System.out.println("  " + latlng);
			        }
			    }

			    // Display whether the country is landlocked or not
			    System.out.println("Is the country landlocked? " + (newCountry.landlocked ? "Yes" : "No"));

			    // Check if the country shares borders with any other countries, and if so, print them out
			    if (newCountry.borders != null) {
			        System.out.println("Borders:");
			        for (String border : newCountry.borders) {
			            System.out.println("  " + border);
			        }
			    }

			    // Display the area of the country
			    System.out.println("Area: " + newCountry.area + " square kilometers");

			    // Display the demonyms (words used to describe people from the country) and their corresponding female and male forms
			 // Display the demonyms (words used to describe people from the country) and their corresponding female and male forms
			    System.out.println("Demonyms:");

			    // Check if the new country has any demonyms
			    if (newCountry.demonyms != null && !newCountry.demonyms.isEmpty()) {
			        // Loop through the demonyms of the new country
			        for (Entry<String, Demonyms> entry : newCountry.demonyms.entrySet()) {
			            // Get the key (demonym type) and value (Demonyms object)
			            String key = entry.getKey();
			            Demonyms value = entry.getValue();

			            // Print the key and the corresponding values
			            System.out.println("Demonyms for " + newCountry.name + " (" + key + "):");
			            System.out.println("* Female: " + value.f);
			            System.out.println("* Male: " + value.m);
			        }
			    }

			    
//			    if (newCountry.latlng != null) {
//					System.out.println("  Latlng: ");
//					for (double latlng : newCountry.latlng) {
//						System.out.println("    " + latlng);
//					}
//				}
				// Display information about a country
				System.out.println("Country information:");

				// Display map information using Google Maps and OpenStreetMaps
				System.out.println("  Map:");
				System.out.println("    Google Maps: " + newCountry.maps.googleMaps);
				System.out.println("    OpenStreetMaps: " + newCountry.maps.openStreetMaps);

				// Display the population of the country
				System.out.println("  Population: " + newCountry.population);

				// Display the FIFA code of the country
				System.out.println("  FIFA Code: " + newCountry.fifa);

				// Check if the country has car sign data and display it if available
				if (newCountry.car.signs != null) {
				  System.out.println("  Car:");
				  System.out.println("    Signs:");
				  
				  // Display all car signs available
				  for (String sign : newCountry.car.signs) {
				    System.out.println("      " + sign);
				  }
				  
				  // Display the side of the car
				  System.out.println("    Side: " + newCountry.car.side);
				}

				// Check if the country has time zone data and display it if available
				if (newCountry.timezones != null) {
				  System.out.println("  Time Zones:");
				  
				  // Display all time zones available
				  for (String timezone : newCountry.timezones) {
				    System.out.println("    " + timezone);
				  }
				}

				// If the country belongs to continents, print them
				if (newCountry.continents != null) {
				    System.out.println("Continents:");
				    for (String continent : newCountry.continents) {
				        System.out.println("- " + continent);
				    }
				}

				// Print the country's flags
				System.out.println("Flags:");
				System.out.println("- PNG: " + newCountry.flags.png);
				System.out.println("- SVG: " + newCountry.flags.svg);
				System.out.println("- ALT: " + newCountry.flags.alt);

				// Print the country's coat of arms
				System.out.println("Coat of Arms:");
				System.out.println("- PNG: " + newCountry.coatOfArms.png);
				System.out.println("- SVG: " + newCountry.coatOfArms.svg);

				// Print the country's start of week
				System.out.println("Start of Week: " + newCountry.startOfWeek);

				// Print the country's capital information
				System.out.println("Capital Information:");
				if (newCountry.capitalInfo.latlng != null) {
				    System.out.println("- Latlng:");
				    for (double latlng : newCountry.capitalInfo.latlng) {
				        System.out.println("  - " + latlng);
				    }
				}
				
				if (newCountry.postalCode != null) {
					System.out.println("  Postal Code: ");
					System.out.println("    Format: " + newCountry.postalCode.format);
					System.out.println("    Regex: " + newCountry.postalCode.regex);
				}
				
				countries.add(newCountry);
				
				// Update the countries table in the database
				JDBC.countriesTable();
				System.out.println("Could not add country details. Please try again.");
			}
		}
			
			catch (Exception e) {
	            e.printStackTrace();
	        }
		} 
}
		
	

		
	
