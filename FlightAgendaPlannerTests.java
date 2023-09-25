// --== CS400 File Header Information ==--
// Name: Sarah Nelson
// Email: sgnelson3@wisc.edu
// Team: AU
// TA: Cameron
// Lecturer: Gary Dahl
// Notes to Grader: Sources: last project

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.LinkedList;
import java.lang.invoke.MethodHandles;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;

public class FlightAgendaPlannerTests {

    public static void main(String[] args) throws Exception {
	String className = MethodHandles.lookup().lookupClass().getName();
    	String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
    	String[] arguments = new String[] {
   	   "-cp",
   	   classPath,
   	   "--include-classname=.*",
   	   "--select-class=" + className };
 	ConsoleLauncher.main(arguments);
    }

    // Data Wrangler Code Tests
    /**
     * This test method confirms if flightLoader.java correctly grabs the correct distance from the
     * sorted flightData.csv file.
     *
     * @author Joseph Neimon (Data Wrangler)
     */
    @Test
    public void dataWranglerTest_TestOriginAirport() {
        String originAirport = "GYY";
        FlightLoader testLoader = new FlightLoader();
        try {
            List<FlightDataInterface> testList = testLoader.loadFile("flightData.csv");
            assertEquals(originAirport, testList.get(3).getOriginAirport());
        } catch (FileNotFoundException e) {
            fail("TEST FAILED: dataWranglerTest_TestOriginAirport failed to locate file");
        }
    }

    /**
     * This test method confirms if flightLoader.java correctly grabs the correct origin airport
     * from the sorted flightData.csv file.
     *
     * @author Joseph Neimon (Data Wrangler)
     */
    @Test
    public void dataWranglerTest_TestOriginCity() {
        String originCity = "Gary";
        FlightLoader testLoader = new FlightLoader();
        try {
            List<FlightDataInterface> testList = testLoader.loadFile("flightData.csv");
            assertEquals(originCity, testList.get(0).getOriginCity());
        } catch (FileNotFoundException e) {
            fail("TEST FAILED: dataWranglerTest_TestOriginCity failed to locate file");
        }
    }

    /**
     * This test method confirms if flightLoader.java correctly grabs the correct origin city from
     * the sorted flightData.csv file.
     *
     * @author Joseph Neimon (Data Wrangler)
     */
    @Test
    public void dataWranglerTest_TestDestinationAirport() {
        String destinationAirport = "MIA";
        FlightLoader testLoader = new FlightLoader();
        try {
            List<FlightDataInterface> testList = testLoader.loadFile("flightData.csv");
            assertEquals(destinationAirport, testList.get(55).getDestinationAirport());
        } catch (FileNotFoundException e) {
            fail("TEST FAILED: dataWranglerTest_TestDestinationAirport failed to locate file");
        }
    }

    /**
     * This test method confirms if flightLoader.java correctly grabs the correct destination
     * airport from the sorted flightData.csv file.
     *
     * @author Joseph Neimon (Data Wrangler)
     */
    @Test
    public void dataWranglerTest_TestDestinationCity() {
        String destinationCity = "Newark";
        FlightLoader testLoader = new FlightLoader();
        try {
            List<FlightDataInterface> testList = testLoader.loadFile("flightData.csv");
            assertEquals(destinationCity, testList.get(739).getDestinationCity());
        } catch (FileNotFoundException e) {
            fail("TEST FAILED: dataWranglerTest_TestDestinationCity failed to locate file");
        }
    }

    /**
     * This test method confirms if flightLoader.java correctly grabs the correct destination city
     * from the sorted flightData.csv file.
     *
     * @author Joseph Neimon (Data Wrangler)
     */
    @Test
    public void dataWranglerTest_TestDistance() {
        int distance = 679;
        FlightLoader testLoader = new FlightLoader();
        try {
            List<FlightDataInterface> testList = testLoader.loadFile("flightData.csv");
            assertEquals(distance, testList.get(1299).getDistance());
        } catch (FileNotFoundException e) {
            fail("TEST FAILED: dataWranglerTest_TestDistance failed to locate file");
        }
    }

    //Back End Developer Code Tests
    
    /**
     * This method checks the functionality of the graph within the the back end engine. Adds
     * multiple points into a graph, and checks if a vertex was added for each point. Then checks
     * to make sure that all edges were added, and that no edges were added where they shouldn't
     * be
     * 
     * @author Jake Murawski (Back End Developer)
     */
    @Test
    public void backEndTest_TestGraphFunctionality() {
    	SearchBackEnd engine = new SearchBackEnd();
    	FlightData flightA = new FlightData("AirportA", "AirportB", "CityA", "CityB", 5);
        FlightData flightB = new FlightData("AirportB", "AirportC", "CityB", "CityC", 10);
        FlightData flightC = new FlightData("AirportC", "AirportD", "CityC", "CityD", 15);
        FlightData flightD = new FlightData("AirportD", "AirportA", "CityD", "CityA", 25);
        engine.addFlight(flightA);
        engine.addFlight(flightB);
        engine.addFlight(flightC);
        engine.addFlight(flightD);
        assertEquals(true, engine.graph.containsVertex("AirportA"));
        assertEquals(true, engine.graph.containsVertex("AirportB"));
        assertEquals(true, engine.graph.containsVertex("AirportC"));
        assertEquals(true, engine.graph.containsVertex("AirportD"));      
        assertEquals(true, engine.graph.containsEdge("AirportA", "AirportB"));
        assertEquals(true, engine.graph.containsEdge("AirportB", "AirportC"));
        assertEquals(true, engine.graph.containsEdge("AirportC", "AirportD"));
        assertEquals(true, engine.graph.containsEdge("AirportD", "AirportA"));
        assertEquals(false, engine.graph.containsEdge("AirportA", "AirportC"));
        assertEquals(false, engine.graph.containsEdge("AirportB", "AirportD"));
    }
    /**
     * This method checks the capability of finding the shortest path between airports.
     * The graph shown below is used for testing, with the shortest path being F-E-A-B-C
     * Checks if that is in fact the path which is found, and then makes sure it has the 
     * correct weight. 
     *  
     *                    B
     *                   / \
     *	   	            /   \
     *     F --- E --- A     C
     *	                \   /
     *	                 \ /
     *                    D   
     *                    
     * @author Jake Murawski                   
     */  		      
    @Test
    public void backEndTest_TestShortestPath() {
    	SearchBackEnd engine = new SearchBackEnd();
    	FlightData flightA = new FlightData("AirportA", "AirportB", "CityA", "CityB", 5);
        FlightData flightB = new FlightData("AirportB", "AirportC", "CityB", "CityC", 10);
        FlightData flightC = new FlightData("AirportC", "AirportD", "CityC", "CityD", 15);
        FlightData flightD = new FlightData("AirportD", "AirportA", "CityD", "CityA", 25);
        FlightData flightE = new FlightData("AirportE", "AirportA", "CityE", "CityA", 10);
        FlightData flightF = new FlightData("AirportF", "AirportE", "CityF", "CityE", 20);
        engine.addFlight(flightA);
        engine.addFlight(flightB);
        engine.addFlight(flightC);
        engine.addFlight(flightD);
        engine.addFlight(flightE);
        engine.addFlight(flightF);
        engine.currentAirport = "AirportF";
        List<FlightData> shortestPath = engine.shortestPath("AirportC");
        String s = "";
        for (FlightData flight : shortestPath) {
        	s += flight.originAirport + ", ";
        }
        s += shortestPath.get(shortestPath.size() - 1).destinationAirport;
        String actualPath = "AirportF, AirportE, AirportA, AirportB, AirportC";
        int actualPathCost = 45;
        assertEquals(s, actualPath);
        assertEquals(45, engine.graph.getPathCost("AirportF", "AirportC"));
    }
    
    /**
     * Using the same graph as the previous question, this method checks the adjacent cities
     * of every single vertex via the adjacentPath() method. 
     * 
     * @author Jake Murawski
     */
    @Test
    public void backEndTest_TestAdjacentPaths() {
    	SearchBackEnd engine = new SearchBackEnd();
    	FlightData flightA = new FlightData("AirportA", "AirportB", "CityA", "CityB", 5);
        FlightData flightB = new FlightData("AirportB", "AirportC", "CityB", "CityC", 10);
        FlightData flightC = new FlightData("AirportC", "AirportD", "CityC", "CityD", 15);
        FlightData flightD = new FlightData("AirportD", "AirportA", "CityD", "CityA", 25);
        FlightData flightE = new FlightData("AirportE", "AirportA", "CityE", "CityA", 10);
        FlightData flightF = new FlightData("AirportF", "AirportE", "CityF", "CityE", 20);
        engine.addFlight(flightA);
        engine.addFlight(flightB);
        engine.addFlight(flightC);
        engine.addFlight(flightD);
        engine.addFlight(flightE);
        engine.addFlight(flightF);
        engine.currentAirport = "AirportA";
        List<FlightData> fromA = engine.adjacentPaths();
        engine.currentAirport = "AirportB";
        List<FlightData> fromB = engine.adjacentPaths();
        engine.currentAirport = "AirportC";
        List<FlightData> fromC = engine.adjacentPaths();
        engine.currentAirport = "AirportD";
        List<FlightData> fromD = engine.adjacentPaths();
        engine.currentAirport = "AirportE";
        List<FlightData> fromE = engine.adjacentPaths();
        engine.currentAirport = "AirportF";
        List<FlightData> fromF = engine.adjacentPaths();
        String adjacentA = "";
        String adjacentB = "";
        String adjacentC = "";
        String adjacentD = "";
        String adjacentE = "";
        String adjacentF = "";
        for (FlightData f : fromA) { adjacentA += f.getDestinationCity() + " "; }
        for (FlightData f : fromB) { adjacentB += f.getDestinationCity() + " "; }
        for (FlightData f : fromC) { adjacentC += f.getDestinationCity() + " "; }
        for (FlightData f : fromD) { adjacentD += f.getDestinationCity() + " "; }
        for (FlightData f : fromE) { adjacentE += f.getDestinationCity() + " "; }
        for (FlightData f : fromF) { adjacentF += f.getDestinationCity() + " "; }
        assertEquals(adjacentA, "CityB CityD CityE ");
        assertEquals(adjacentB, "CityA CityC ");
        assertEquals(adjacentC, "CityB CityD ");
        assertEquals(adjacentD, "CityC CityA ");
        assertEquals(adjacentE, "CityA CityF ");
        assertEquals(adjacentF, "CityE ");
    }

     //Front End Developer Tests
    	/**
	 *This test emthod tests the directFlight in the FrontEnd.java
	 *It adds a a bunch of flights to the graph
	 *It then sets an origin city(assuming the user picks that origin city)
	 *It cheks that all directly connected path to the origin are displayed(flights go both ways)
	 *@author Isaac Odedoyin
	 */
    	 @Test
	public void frontEndTest_directFlight() {
		{
		SearchBackEnd tester = new SearchBackEnd();
		FrontEnd tester1 = new FrontEnd();
		FlightData newFlight = new FlightData("ORD", "LAX", "Chicago", "Los Angeles", 2015);// this is a flight from Chicago to LA
		tester.addFlight(newFlight);
		FlightData newFlight1 = new FlightData("BUR", "LAX", "Orlando", "Los Angeles" ,1000);//this flight is not connected to Chicago
		FlightData newFlight2 = new FlightData("ORR", "ORD", "Burnham", "Chicago", 4320);//this is a flight that connects to Chicago, but not from Chicago
		tester.addFlight(newFlight1);
		tester.addFlight(newFlight2);
		tester.setCurrentAirport("ORD");//origin airport is ORD in Chicago
		String result = "Flight from ORD in Chicago to LAX in Los Angeles is 2015 miles." + "\n" + 
			"Flight from ORD in Chicago to ORR in Burnham is 4320 miles." + "\n";  
		assertEquals(result,tester1.direct(tester));
		//System.out.println(tester1.direct(tester));
		}

		//checks with multiple flights and flights that are not connected
		{
		SearchBackEnd tester = new SearchBackEnd();
		FrontEnd tester1 = new FrontEnd();
		tester.setCurrentAirport("ORD");
		//flights to origin city
		FlightData newFlight = new FlightData("CNA", "ORD", "Nashville", "Chicago", 474);
		FlightData newFlight1 = new FlightData("JFK", "ORD", "New York", "Chicago", 120);
		FlightData newFlight2 = new FlightData("BNA", "ORD", "New Jersey", "Chicago", 31);
		//flights from orgin city
		FlightData newFlight3 = new FlightData("ORD", "NBA", "Chicago", "New Jersey", 300);
		//flight not connected to origin city
		FlightData newFlight4 = new FlightData("ABA", "GGA", "Mississppi", "Las Vegas", 100);
		String result = "Flight from ORD in Chicago to CNA in Nashville is 474 miles." + "\n" +
			"Flight from ORD in Chicago to JFK in New York is 120 miles." + "\n" + 
			"Flight from ORD in Chicago to BNA in New Jersey is 31 miles." + "\n" +
			"Flight from ORD in Chicago to NBA in New Jersey is 300 miles." + "\n";
		tester.addFlight(newFlight);
		tester.addFlight(newFlight1);
		tester.addFlight(newFlight2);
		tester.addFlight(newFlight3);
		tester.addFlight(newFlight4);
		assertEquals(result, tester1.direct(tester));
		}
		
		

		}
	 /**
	  *This test method checks the shortestPath of the FrontEnd.java
	  *This method adds a bunch of flights to the graph
	  * It makes sure that the correct shortest path is returned to the user
	  * @author Isaac Odedoyin
	  */
	 @Test
	 public void frontEndTest_shortestPath() {
		//find shortest path between any two airports
		//checks to make sure shortest path is returned when there are multiple paths to the same location
		 {
		 SearchBackEnd tester = new SearchBackEnd();
		 FrontEnd tester1 = new FrontEnd();
		 //path cost from Chicago is 25 (10 +15)
		 FlightData newFlight = new FlightData("ORD", "JFK", "Chicago", "New York", 10);
		 FlightData newFlight1 = new FlightData("JFK", "LAX", "New York", "Los angeles", 15);
		 //path cost from Chicago is 155(100 +55)
		 FlightData newFlight2 = new FlightData("ORD", "BNA", "Chicago", "New Jersey", 55);
		 FlightData newFlight3 = new FlightData("BNA", "LAX", "Mississippi", "Los Angeles", 100);

		 tester.setCurrentAirport("ORD");
		 String result = "Flight from ORD in Chicago to JFK in New York is 10 miles." + "\n" + 
			 "Flight from JFK in New York to LAX in Los Angeles is 15 miles." + "\n";
		 tester.addFlight(newFlight);
		 tester.addFlight(newFlight1);
		 tester.addFlight(newFlight2);
		 tester.addFlight(newFlight3);
		 List<FlightData>result1 = tester.shortestPath("LAX");
		 String result2 = "";
		 for (int i =0; i< result1.size(); i++) {
			 result2  += tester.toString(result1.get(i)) + "\n";
		 }
		 assertEquals(result,result2 );
	}
		//when no path exists
		{
		SearchBackEnd tester = new SearchBackEnd();
		FrontEnd tester1 = new FrontEnd();
		FlightData newFlight = new FlightData("ORD", "JFK", "Chicago", "New York", 15);
		FlightData newFlight1 = new FlightData("LAX" ,"NBA", "Los angeles", "Nashville", 10);
		FlightData newFlight2 = new FlightData("DSA", "OBA", "New Jersey", "Madison", 32);
		tester.setCurrentAirport("ORD");
		tester.addFlight(newFlight);
		tester.addFlight(newFlight1);
		tester.addFlight(newFlight2);
		String result = "No such path" + "\n";
			try{
			List<FlightData>result1 = tester.shortestPath("NBA");
			//nullpointer should be thrown
			assertEquals(1,0);
			} catch (Exception e) {
			assertEquals(1,1);
			}
		
	 }	
	 }
	 /**
	  *This test method tests the addFlight in the FrontEnd.java
	  *It adds flights to the graph(assuming the user enters these inputs)
	  *Checks to make sure taht the newly added flights are now part of th menu option
	  *@author Isaac Odedoyin
	  */
	 @Test
	 public void frontEndTest_addFlight() {
		 {
	         SearchBackEnd tester = new SearchBackEnd();
		 FrontEnd tester1 = new FrontEnd();
		 //assuming these ar the user's inputs
		 FlightData newFlight = new FlightData("ORD","JFK","Chicago", "New York", 100);
		 FlightData newFlight1 = new FlightData("ODD","NYY", "New Jersey", "Miami", 150);
		 tester.addFlight(newFlight);
		 tester.addFlight(newFlight1);
		 String result = "ODD (New Jersey)" + "\n" + "NYY (Miami)" + "\n" + "JFK (New York)" + "\n" + "ORD (Chicago)" + 
			 "\n";
		 //newly added flight should be added to the list of options to chose from
		assertEquals(result,tester1.intro(tester));



		 }
	 }
		

		 

	//	}
	//}

	

	 /**
         * Tests the work of the Data Wrangler. Test to make sure that flightLoader correctly
	 * reads in the csv file and returns the correct origin airport. If not an exception is 
	 * thrown.
         *
         * @author Sarah Nelson
         */	
   	 @Test
    	public void intergrationManagerTest_testDataWrangler() {
        String start  = "PHL";
        FlightLoader flightLoader = new FlightLoader();
        try {
            List<FlightDataInterface> list  = flightLoader.loadFile("flightData.csv");
            assertEquals(start, list.get(1).getOriginAirport());
        } catch (FileNotFoundException e) {
            fail("failed to locate file");
        }
   	}

     	 /**
         * Tests the work of the Front End Developer. Test to make sure that aftering using
	 * the addFlight() method front end is able to correctly use the elements.
	 * This test check to ensure the correct direct flight is found and printed out.
	 *
         * @author Sarah Nelson
         */
   	@Test
	public void intergrationManagerTest_testFrontEnd(){
  	SearchBackEnd engine = new SearchBackEnd();
      	FrontEnd front = new FrontEnd();
	FlightData flightA = new FlightData("MSN", "MEX", "Madison", "Mexico", 10);
	FlightData flightB = new FlightData("MEX", "JSD", "Mexico", "Jefferson", 10);
	FlightData flightC = new FlightData("JSD", "LRK", "ABC", "Lark", 25);
	engine.addFlight(flightA);
	engine.addFlight(flightB);
	engine.addFlight(flightC);
	engine.setCurrentAirport("MSN");
	String result = "Flight from MSN in Madison to MEX in Mexico is 10 miles." +"\n"; 
		assertEquals(result,front.direct(engine));
	}

	/**
	 * Tests the work of the Back End Developer. Test to make sure that the addFlight() 
	 * method correctly added each element of the flight. Then check to make sure that
	 * getTotalCost() method returns the correct values of the flights
	 * of every single vertex via the adjacentPath() method.
	 *
	 * @author Sarah Nelson
	 */
	@Test
	public void intergrationManagerTest_testBackEnd(){
        SearchBackEnd engine = new SearchBackEnd();

	FlightData flightA = new FlightData("MSN", "MEX", "Madison", "Mexico", 10); 
	FlightData flightB = new FlightData("MEX", "JSD", "LakeMills", "Jefferson", 10); 
	FlightData flightC = new FlightData("JSD", "LRK", "ABC", "Madison", 25); 
	engine.addFlight(flightA); 
	engine.addFlight(flightB); 
	engine.addFlight(flightC); 
	engine.currentAirport = "MSN"; 
	List<FlightData> shortestPath = engine.shortestPath("LRK"); 
	String s = ""; for (FlightData flight : shortestPath) { 
	s += flight.originAirport + ", "; } 
	s += shortestPath.get(shortestPath.size() - 1).destinationAirport; 
	String actualPath = "MSN, MEX, JSD, LRK"; 
	int actualPathCost = 45; 
	assertEquals(s, actualPath); 
	assertEquals(45, engine.graph.getPathCost("MSN", "LRK"));

	}

}


