// --== CS400 File Header Information ==--
// Name: Joseph Neimon
// Email: neimon@wisc.edu
// Team: AU
// TA: Cameron
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;

// interface (implemented with proposal)

interface FlightLoaderInterface {
    public List<FlightDataInterface> loadFile(String csvFilePath) throws FileNotFoundException;
}

// public class (implemented primarily in final app week)


public class FlightLoader implements FlightLoaderInterface {

    /**
     * Extracts contents from a CSV. Specific if statements are added to focus on the flightData.CSV
     * inputs. CSV files put into the following method should have the following pattern per line:
     * String originAirport, String destinationAirport, String originCity, String destinationCity,
     * int distance, int Origin_population, int Destination_population. One extra note, to lower the
     * number of options for the user, this method does not add paths that have a
     * Destination_population below a cutoff threshold.
     *
     * @throws FileNotFoundException if method can not extract or process data in CSV file
     * @param csvFilePath is the path to the CSV file data is being extracted from
     * @return List<FlightDataInterface> contains contents of CSV file stores as Paths
     */
    @Override
    public List<FlightDataInterface> loadFile(String csvFilePath) throws FileNotFoundException {
        List<FlightDataInterface> fileList = new LinkedList<>();
        File file = new File(csvFilePath);

        if (!file.exists()) { // Determines if the file is available
            throw new FileNotFoundException("The File you are looking for does not exist");
        }
        try {
            Scanner scnr = new Scanner(file); // Scanner will iterate through the file
            String[] flightData; // Stores elements that will go into Path variable
            String currLine;

            if (csvFilePath.contains("flightData.csv")) {
                currLine = scnr.nextLine(); // Skips the first line of flightData.csv as it does not
                                            // contain data
            }

            while (scnr.hasNextLine()) { // Iterates through entire CSV file
                currLine = scnr.nextLine();
                if (csvFilePath.contains("flightData.csv")) {
                    /**
                     * Line below separates by commas excluding those contained in quotations (Ex.
                     * MHK,AMW,"Manhattan, KS","Ames, IA",254 separates to MHK, AMW,(Manhattan,
                     * KS),(Ames, IA), and254])
                     */
                    flightData = currLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    if (flightData.length != 7) { // Confirms split into correct # of elements for
                                                  // Path
                        throw new IOException(); // Did not properly read the .CSV file if thrown
                    }

                    // Lines below removes state acronym from element (Ex. turns "Manhattan, KS" to
                    // "Manhattan")
                    flightData[2] = flightData[2].split(",")[0].substring(1);
                    flightData[3] = flightData[3].split(",")[0].substring(1);
                } else {
                    flightData = currLine.split(",");
                }

                if (flightData.length != 7) {// Checks if entered .CSV file is correctly formatted
                                             // for the code
                    throw new IOException(); // Did not properly read the .CSV file
                }

                /**
                 * This statement removes the flights that go to cities too small in population (our
                 * cutoff is (9,000,000)
                 */
                if (Integer.parseInt(flightData[5]) < 9000000 || Integer.parseInt(flightData[6]) < 9000000) {
                    continue; // Skips over adding flight data below population cutoff
                }

                fileList.add(new FlightData(flightData[0], flightData[1], flightData[2],
                    flightData[3], Integer.parseInt(flightData[4])));
            }
            scnr.close();
        } catch (IOException e) {
            System.out.println("ERROR: unable to properly read file");
        }
        return fileList;
    }
}


// placeholder(s) (implemented with proposal, and possibly added to later)
class FlightLoaderPlaceholder implements FlightLoaderInterface {
    public List<FlightDataInterface> loadFile(String csvFilePath) throws FileNotFoundException {
        List<FlightDataInterface> list = new LinkedList<>();
        list.add(new FlightDataPlaceholderA());
        list.add(new FlightDataPlaceholderB());
        return list;
    }
}

