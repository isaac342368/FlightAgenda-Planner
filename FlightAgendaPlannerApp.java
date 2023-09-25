// --== CS400 File Header Information ==--
// Name: Sarah Nelson
// Email: sgnelson3@wisc.edu
// Team: AU
// TA: Cameron
// Lecturer: Gary Dahl
// Notes to Grader: Sources: last project
import java.util.List;
public class FlightAgendaPlannerApp {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Fight Agenda Planner");
        List<FlightDataInterface> flights = new FlightLoader().loadFile("flightData.csv");
        SearchBackEndInterface engine = new SearchBackEnd();
        FrontEndInterface ui = new FrontEnd();
       	for(FlightDataInterface flight : flights){	
		engine.addFlight( (FlightData) flight);
	}
        ui.run(engine);
    }

}

