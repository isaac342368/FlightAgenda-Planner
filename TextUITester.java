// --== CS400 Project Three File Header ==--
// Name: Isaac odedoyin
// Email: oodedoyin@wisc.edu
// Team: blue
// Group: AU
// TA: Cameron Ruggles
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.List;
/**
 * This class can be used to test text based user interactions by 1) specifying
 * a String of text input (that will be fed to System.in as if entered by the user),
 * and then 2) capturing the output printed to System.out and System.err in String
 * form so that it can be compared to the expect output.
 * @author dahl
 * @date 2021.10
 */
public class TextUITester {

    /**
     * This main method demonstrates the use of a TextUITester object to check
     * the behavior of the following run method.
     * @param args from the commandline are not used in this example
     */
    public static void main(String[] args) {

        // 1. Create a new TextUITester object for each test, and
        // pass the text that you'd like to simulate a user typing as only argument.
        TextUITester tester = new TextUITester("1\nCHI\nLAX\n2\nCHI\n3\nMYA\nNashville\nAAU\nMiami\na\n100\n1\n"
			+ "CHI\nLAX\n4");

        // 2. Run the code that you want to test here:
        
	
	//run(); // (this code should read from System.in and write to System.out)
	FlightLoader f1 = new FlightLoader();
	try {
		List<FlightDataInterface> flights = f1.loadFile("flightData.csv");
		FrontEnd ui = new FrontEnd();
		SearchBackEnd engine = new SearchBackEnd();
		for (int i = 0; i < flights.size(); i ++) {
			engine.addFlight((FlightData)(flights.get(i)));
		}
		ui.run(engine);
	} catch (Exception e) {
		System.out.println("error occured");
	}
        // 3. Check whether the output printed to System.out matches your expectations.
        String output = tester.checkOutput();
       // if(output.startsWith("Welcome to the run program.") &&
        if  (output.contains("CHI (Chicago)\nDCA (Washington)\nEWR (Newark)\n")//check that menu options are printed when user inputs 1
		   //check that correct ouput is returned when user choses CHI to LAX
		   && output.contains("Flight from CHI in Chicago to LAX in Los Angeles is 1751 miles.")
		   //check that correct output is returned when user choses option 2 and sets CHI as origin airport
		   && output.contains("Flight from CHI in Chicago to DFW in Dallas is 802 miles.")
		   //check that prompt is printed when user types in incorrect input
		   && output.contains("Invalid input, please enter an integer")
		   //check that new flight added by user in on menu options
		   && output.contains("MYA (Nashville)\n")){

            System.out.println("Test passed.");
		   }
        else
            System.out.println("Test FAILED.");
    }   
    
    /**
     * This is the code being tested by the main method above.
     * It 1) prints out a welcome message, 
     *    2) reads a String, a double, and a character from System.in, and then
     *    3) prints out the string followed by a number that is one greater than that double,
     *       if the character that it read in was a (lower case) 'q'.
     */
    public static void run() {
        // Note to avoid instantiating more than one Scanner reading from System.in in your code!
        Scanner in = new Scanner(System.in); 
        System.out.println("Welcome to the run program.");
        System.out.println("Enter a string, a double, and then q to quit: ");
        String s = in.nextLine();
        double d = in.nextDouble(); in.nextLine(); // read newline after double
        char c = in.nextLine().charAt(0);
        if(c == 'q')
            System.out.println(s + (d + 1.0));
        in.close();
    }

    // Below is the code that actually implements the redirection of System.in and System.out,
    // and you are welcome to ignore this code: focusing instead on how the constructor and
    // checkOutput() method is used int he example above.

    private PrintStream saveSystemOut; // store standard io references to restore after test
    private PrintStream saveSystemErr;
    private InputStream saveSystemIn;
    private ByteArrayOutputStream redirectedOut; // where output is written to durring the test
    private ByteArrayOutputStream redirectedErr;

    /**
     * Creates a new test object with the specified string of simulated user input text.
     * @param programInput the String of text that you want to simulate being typed in by the user.
     */
    public TextUITester(String programInput) {
        // backup standard io before redirecting for tests
        saveSystemOut = System.out;
        saveSystemErr = System.err;
        saveSystemIn = System.in;    
        // create alternative location to write output, and to read input from
        System.setOut(new PrintStream(redirectedOut = new ByteArrayOutputStream()));
        System.setErr(new PrintStream(redirectedErr = new ByteArrayOutputStream()));
        System.setIn(new ByteArrayInputStream(programInput.getBytes()));
    }

    /**
     * Call this method after running your test code, to check whether the expected
     * text was printed out to System.out and System.err.  Calling this method will 
     * also un-redirect standard io, so that the console can be used as normal again.
     * 
     * @return captured text that was printed to System.out and System.err durring test.
     */
    public String checkOutput() {
        try {
            String programOutput = redirectedOut.toString() + redirectedErr.toString();
            return programOutput;    
        } finally {
            // restore standard io to their pre-test states
            System.out.close();
            System.setOut(saveSystemOut);
            System.err.close();
            System.setErr(saveSystemErr);
            System.setIn(saveSystemIn);    
        }
    }
}
