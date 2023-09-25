// --== CS400 Project Three File Header ==--
// // Name: Isaac Odedoyin
// // Email: oodedoyin@wisc.edu
// // Team: blue
// // Group: AU
// // TA: Cameron Ruggles
// // Lecturer: Gary Dah
// // Notes to Grader: <optional extra notes>
import java.util.Scanner;
import java.util.List;

interface FrontEndInterface {
		public void run(SearchBackEndInterface searchEngine);
	//	Here is an exmaple of the optio the user will have to chose from :
	//		1. Find out the distance from your airport(or city) to another airport
	//		2. Find out which airports have a direct flight
	//		3. Find out which city you can travel to with the largest population.
	}


	/**
	 *This class takes care of the user interaface by providing the user with options to chose from
	 *It displays the correct output based on the user's decisions
	 */
	public class FrontEnd implements FrontEndInterface {
		Scanner scnr = new Scanner(System.in);
		boolean looper = true;
		int userInt;//user's int inputs
		String startCity;//user's origin city
		String endCity;//user's destination city
		String acro1;//user's origin airport
		String acro2;//user's destination airport
		String scity;//user's origin city when adding their own flight
		String dcity;//user's destination city when adding their own flight
		int distance;//user's distance between flight

		/**
		 *This is the central method that keeps the app running. This method provides option for the user to chose from
		 *Based on the user's inputs, the correct outputs are printed to the screen by running the backend objects
		 */
		@Override
		public void run(SearchBackEndInterface engine) {
		System.out.println(logo());

		while(looper) {
		printMenu();
		userInt = scrInt(scnr);
	        
		switch (userInt) {
			case 1:
				line();
				//print menu options
				System.out.println(intro(engine));
				System.out.println("Pick an origin airport acronym from the list above by typing in the corresponding acronyms for that airport");
				startCity = scnr.nextLine();
				//check if user's input is valid
				if (validInput(startCity))
						engine.setCurrentAirport(startCity.toUpperCase());
				line();
                        System.out.println("Pick a destination airport acronym from the list below");
			System.out.println();
            		System.out.println(intro(engine));         	
                        endCity = scnr.nextLine();
                       if( validInput(endCity))
                        line();
		       //run backend's shortest path algorithm
                        System.out.println(shortestPath(engine));
                        line();
                        continue;
                    case 2:
			 System.out.println(intro(engine));
			 System.out.println("Pick an origin airport acronym from the list above by typing in the corresponding acronyms for that airport");
			 //declare user's input as their origin city
                         startCity = scnr.nextLine();
                         if (validInput(startCity))
                         engine.setCurrentAirport(startCity.toUpperCase());

                        line();
			//run backend's adjacentPaths algorithm
                        System.out.println(direct(engine));
                        line();
                        continue;
                    case 3:
                        line();
			System.out.println("Option 3 selected : Add a flight!");
                        System.out.println("Enter the acronym of the airport of the flight you want to add");
			//save user's origin airport
                        acro1 = scnr.nextLine();
                        validInput(acro1);
                        System.out.println("Enter the city this airport is in");
			//save user's origin city
                        scity = scnr.nextLine();
                        System.out.println("Enter the acronyms of the airport this flight will go to");
			//save user's destination airport
                        acro2 = scnr.nextLine();
                        validInput(acro2);
                        System.out.println("Enter the city this flight will land in");
			//save user's destination city
                        dcity = scnr.nextLine();
                        System.out.println("Enter the distance between the two airports");
			//make sure user inputs a number
                        distance = scrInt(scnr);
				while(distance == 0){
					System.out.println("Invalid input, please enter an integer(in miles).");
					distance = scrInt(scnr);
				}
			//run backend's addflight algorithm
                        addFlight(engine);
                        System.out.println("Flight added!");
                        line();
                        continue;
                    case 4:
                        line();
                        System.out.println("Have a nice day!");
                        System.out.println("Shutting down");
                        looper = false;
                        continue;
                    default:
                        line();
                        System.out.println("Please enter a valid number (1-4)");
                        line();
                        continue;
                }

            }
		}
		/**
		 *This method tells the user which options are available to them using this app
		 */
		public void printMenu() {
		
			System.out.println("1. Find the path and distance to your destination airport.");
			System.out.println("2. Find airports with direct flights to your aiport");
			System.out.println("3. Add a flight");
			System.out.println("4. Quit");
			
        }
		/**
		 *This method helps style the app by creating space between outputs
		 */			
		public void line(){
			System.out.println();
			String s = "";
			for ( int i = 0; i < 60; i++) {
				s+= "-";
			}
			System.out.println(s);
			System.out.println();
		}
		/**
		 *This method prints a logo when the app begins running
		 */
		public String logo() {
		     String s = "";
                s += "|--------------------------------------------------------| \n";
                s += "     |-----------------------------------------------| \n";
                s += "\n";       

                s += "     |-----------------------------------------------|\n";
                s += "|--------------------------------------------------------|\n";
                return s;

               


            }
	    /**
	     *This method makes sure all user's string inputs are valid
	     */
	    public boolean validInput(String city){
		    boolean valid = true;
		    if (city.length() != 3)
			    valid = false;

		    while(!valid) {
			    System.out.println("Invalid input, type in one of the acronyms for an origin airport");
			    city = scnr.nextLine();
			    if (city.length() == 3) {
				    valid = true;
			    }
		    }
		    return valid;
	    
	    }
	    /**
	     *This method prints out all the available airports/cities this app offers
	     */
	    public String intro(SearchBackEndInterface engine) {
		    //backend's airportAndCity returns all the airports and cities in the csv file as a list
		    List<String> result = engine.airportAndCities();
		    String s = "";
		    for (int i = 0; i < result.size(); i ++) {
			    s += (result.get(i)) + "\n";
		    }
	 	return s;   
	    }
	    /**
	     *This method returns the shortest path from startCity to endCity
	     */
	    public String shortestPath(SearchBackEndInterface engine) {
		 	List<FlightData>result = null;
		    try {
			    //run backend's shortestPath 
			 result = engine.shortestPath(endCity.toUpperCase());
			 //exception is return if there is no such path
		 } catch(Exception e) {
			 String s = "No such path";
			 return s;
		 }
		 String s = "";
		 for ( int i = 0;i < result.size(); i ++) {
			 s += engine.toString(result.get(i)) + "\n";
		 }
		 return s;
	    }
	    /**
	     *This method return a string which contains all the flights connected the the startCity
	     */
	    public String direct (SearchBackEndInterface engine) {
		    List<FlightData> result = null;
		    try {
			    //run backend's adjacent path (startCity is set)
                result = engine.adjacentPaths();
		    } catch (Exception e) {
			    String s = "Airport does not exist";
			    return s;
		    }
                String s = "";
                for (int i = 0; i < result.size(); i++) {

                    s += engine.toString(result.get(i))+ "\n";
                    
                }
                return s;
            }
		/**
		 *This method adds the user's flight if they chose option 3
		 */
	     public void addFlight (SearchBackEndInterface engine) {
		     //create new flight based on user's inputs
                FlightData newFlight = new FlightData(acro1, acro2, scity, dcity, distance);
		//add it to the graph
                engine.addFlight(newFlight);
            }
	    /**
	     *This method makes sure all the user's interger inputs are valid
	     */
	    public int scrInt(Scanner scr) {
		    int number;
		    if (scr.hasNextInt()) {
			    number = scr.nextInt();
			    scr.nextLine();
			    return number;
		    }
		    else {
			    scr.nextLine();
			    return 0;
		    }
	    }

		
}

	


//	class FrontEndPlaceholder implements FrontEndIterface {
//		public void run(SearchBackEndInterface searchEngine) {
//			System.out.println("placeholder for fornt end");
//		}
//	}
