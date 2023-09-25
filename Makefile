compile: run
	@java FlightAgendaPlannerApp

test: run
	@java -jar junit5.jar -cp . -c FlightAgendaPlannerTests

run: FlightLoader.class SearchFrontEnd.class 
	@javac FlightAgendaPlannerApp.java
	@javac -cp .:junit5.jar FlightAgendaPlannerTests.java

FlightLoader.class: FlightData.class
	@javac FlightLoader.java

FlightData.class:
	@javac FlightData.java

SearchFrontEnd.class: SearchBackEnd.class
	@@javac FrontEnd.java

SearchBackEnd.class:
	@javac SearchBackEnd.java
