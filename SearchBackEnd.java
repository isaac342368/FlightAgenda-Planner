// --== SearchBackEnd.java ==--
// Name: Jake Murawski
// Email: jmurawski@wisc.edu
// Team: AU
// TA: Cameron
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.LinkedList;
import java.util.List;
import java.util.Hashtable;
// interface (implemented with proposal)
interface SearchBackEndInterface {
    // Adds flight object to the graph
    public void addFlight(FlightData flight);
    // Returns all flights attached to the current destination
    public List<FlightData> adjacentPaths();
    // Returns a list of flights, in order, which is the shortest path to a given destination
    public List<FlightData> shortestPath(String destination);
    //Converts a FlightData into a String
    public String toString(FlightData flight);
    //Creates a list of airports and citites for a user to read
    public List<String> airportAndCities();
    //Allows user to change the current airport
    public void setCurrentAirport(String location);
}

/**
 * This class is used to add FlightData to a graph, and perform calculations with it.
 * Note that FlightData is not directly stored in the graph. Instead, the information 
 * within the FlightData is read, and the airports are stored in the graph along with the
 * distances. The Cities are stored in a Hash-table, where the key is simply the airport
 * 
 * @author Jake Murawski
 */
public class SearchBackEnd implements SearchBackEndInterface {
   Graph<String> graph = new Graph<>();
   Hashtable<String, String> airportAndCity = new Hashtable<String, String>();
   String currentAirport = null;
   
   /**
    * A method which can be used to change the current airport at which the user 
    * is at 
    * 
    * @param location: the airport which shall become the new current one
    */
   public void setCurrentAirport(String location) {
	   currentAirport = location;
   }

   /**
    * Adds a flight to the graph of flights. If the origin or destination have not 
    * yet been added to the graph, adds those. Adds both directions of travel between
    * the two nodes. 
    */
   @Override
   public void addFlight(FlightData flight) {
      boolean newLocation = graph.insertVertex(flight.getOriginAirport());
      boolean newDestination = graph.insertVertex(flight.getDestinationAirport());
      boolean newFlight = graph.insertEdge(flight.getDestinationAirport(), flight.getOriginAirport(), flight.getDistance());
      boolean reverseFlight = graph.insertEdge(flight.getOriginAirport(), flight.getDestinationAirport(), flight.getDistance());
      airportAndCity.put(flight.getOriginAirport(), flight.getOriginCity());
      airportAndCity.put(flight.getDestinationAirport(), flight.getDestinationCity());
   }

   /**
    * Returns a list of all flights which are directly attached to the current location
    */
   @Override
   @SuppressWarnings("rawtypes")
   public List<FlightData> adjacentPaths() {
      List<FlightData> adjacentPaths = new LinkedList();
      if (currentAirport != null) {
         for (Graph.Edge e : graph.getVertex(currentAirport).edgesLeaving) {
            String des = (String) e.target.data;
	         FlightData temp = new FlightData(currentAirport, des, airportAndCity.get(currentAirport),
			    airportAndCity.get(des), e.weight);
	         adjacentPaths.add(temp);
	         }
      }
      else {
         return null;
      }
      return adjacentPaths;
   }

   /**
    * Returns to the user a list containing all flights, in order, to get from the current
    * airport to a destination airport. 
    * 
    * @param destinationAirport: the airport to find a shortest path to
    * @return the ordered list of flights which takes the shortest path
    */
   @Override
   public List<FlightData> shortestPath(String destinationAirport) {
	   List<FlightData> shortestPath = new LinkedList();
	   List<String> airports = graph.shortestPath(currentAirport, destinationAirport);
	   for (int i = 0; i < airports.size() - 1; i++) {
		   FlightData temp = new FlightData(airports.get(i), airports.get(i+1), 
				   airportAndCity.get(airports.get(i)), airportAndCity.get(airports.get(i+1)), 
				   graph.getPathCost(airports.get(i), airports.get(i+1)));
		   shortestPath.add(temp);
	   }
	   return shortestPath;
   }
   
   /**
    * Takes a flight and converts it to a sentence with all the information
    * 
    * @param flight: the flight to be put into a legible statement
    * @return the String version of a flight
    */
   public String toString(FlightData flight) {
	   String s = "Flight from " + flight.getOriginAirport() + " in " + flight.getOriginCity();
	   s += " to " + flight.getDestinationAirport() + " in " + flight.getDestinationCity();
	   s += " is " + flight.getDistance() + " miles.";
	   return s;
   }
   
   /**
    * Iterates through the hashtable to make a list containing Strings of each 
    * airport + city combination in no particular order. 
    * 
    * @return a list of Strings of the form: airport (city)
    */
   public List<String> airportAndCities() {
	   List<String> places = new LinkedList();
	   airportAndCity.entrySet().forEach( entry -> {
		   places.add(entry.getKey() + " (" + entry.getValue() + ")");
	   });
	   return places;
   }
}

