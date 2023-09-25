// --== CS400 File Header Information ==--
// Name: Joseph Neimon
// Email: neimon@wisc.edu
// Team: AU
// TA: Cameron
// Lecturer: Gary Dahl
// Notes to Grader: None

// interface (implemented with proposal)

interface FlightDataInterface {
    public String getOriginAirport();

    public String getOriginCity();

    public String getDestinationAirport();

    public String getDestinationCity();

    public int getDistance();
}

// public class (implemented primarily in final app week)


public class FlightData implements FlightDataInterface {
    String originAirport;
    String originCity;
    String destinationAirport;
    String destinationCity;
    int distance;

    public FlightData(String originAirport, String destinationAirport, String originCity,
        String destinationCity, int distance) {
        this.originAirport = originAirport;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.destinationAirport = destinationAirport;
        this.distance = distance;
    }

    @Override
    public String getOriginAirport() {
        return originAirport;
    }

    @Override
    public String getOriginCity() {
        return originCity;
    }

    @Override
    public String getDestinationAirport() {
        return destinationAirport;
    }

    @Override
    public String getDestinationCity() {
        return destinationCity;
    }

    @Override
    public int getDistance() {
        return distance;
    }


}


// placeholder(s) (implemented with proposal, and possibly added to later)
class FlightDataPlaceholderA implements FlightDataInterface {
    public String getOriginAirport() {
        return "Airport A";
    }

    public String getOriginCity() {
        return "Town A";
    }

    public String getDestinationAirport() {
        return "Airport B";
    }

    public String getDestinationCity() {
        return "Town B";
    }

    public int getDistance() {
        return 1000;
    }
}


class FlightDataPlaceholderB implements FlightDataInterface {
    public String getOriginAirport() {
        return "Airport B";
    }

    public String getOriginCity() {
        return "Town B";
    }

    public String getDestinationAirport() {
        return "Airport C";
    }

    public String getDestinationCity() {
        return "Town C";
    }

    public int getDistance() {
        return 2000;
    }
}


class FlightDataPlaceholderC implements FlightDataInterface {
    public String getOriginAirport() {
        return "Airport C";
    }

    public String getOriginCity() {
        return "Town C";
    }

    public String getDestinationAirport() {
        return "Airport A";
    }

    public String getDestinationCity() {
        return "Town A";
    }

    public int getDistance() {
        return 3000;
    }
}

