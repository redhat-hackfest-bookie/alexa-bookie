
var sportListLocation = "/res/sports.json";
var serviceEndpoint = "http://hackfest-alexa-bookie-alexa.apps.cluster-fed2.fed2.example.opentlc.com/api/v1/bookie/";


var sportSelect = $('#sportSelect');
var homeTeamSelect = $('#homeTeamSelect');
var awayTeamSelect = $('#awayTeamSelect');

var getPredictionButton = $('#getPredictionButton');

var predictionContainer = $('#predictionContainer');
var predictionText = $('#predictionText');

console.log("Page loaded!");

$(document).ready(function() {
	
	//read in sport list
	jQuery.get(sportListLocation, function(data) {
		console.log("Read in sports data: " + data);	
	
		var sportsList = data.sports;
		console.log("Got list of sports: " + sportsList);
		
		sportsList.forEach(function(sport, index) {
			console.log("adding sport: " + sport);
			
			sportSelect.append($('<option/>', { 
        		value: sport,
        		text : sport 
    		}));
		});
	});
	
	sportSelect.change(function() {
		populateTeamSelects();
	});
	
	getPredictionButton.click(function() {
		getBookiePrediction();
	});
	
    console.log("ready!");
});


function resetTeams(){
	console.log("Resetting team selects.");
	homeTeamSelect.empty().append('<option selected="selected"></option>');
	awayTeamSelect.empty().append('<option selected="selected"></option>');
	
}

function populateTeamSelects(){
	console.log("Populating team selects from sport.");
	resetTeams();
	resetPrediction();
	var selectedSport = sportSelect.find(":selected").text();//or val()?
	
	if(selectedSport == ""){
		console.log("No sport selected.");
		return;
	}
	
	console.log("Sport selected: \"" + selectedSport + "\"");
	
	makeCallToService(selectedSport, function(data, status){
		console.log("Got team data from service: " + data);
		
		data.forEach(function(team, index) {
			var teamName = team.name;
			console.log("adding team: " + teamName);
			
    		homeTeamSelect.append($('<option/>', { 
        		value: teamName,
        		text : teamName 
    		}));
    		awayTeamSelect.append($('<option/>', { 
        		value: teamName,
        		text : teamName 
    		}));
		});
	});
}

function getBookiePrediction(){
	console.log("Getting the bookie prediction.");
	resetPrediction();
	
	var selectedSport = sportSelect.find(":selected").text();
	var selectedHome = homeTeamSelect.find(":selected").text();
	var selectedAway = awayTeamSelect.find(":selected").text();
	
	if(selectedSport == ""){
		console.log("No sport selected.");
		updatePredictionResult("No sport selected.");
		return;
	}
	if(selectedHome == ""){
		console.log("No home team selected.");
		updatePredictionResult("No home team selected.");
		return;
	}
	if(selectedAway == ""){
		console.log("No away team selected.");
		updatePredictionResult("No away team selected.");
		return;
	}
	
	var request = selectedSport + "/" + selectedHome + "/" + selectedAway;
	
	makeCallToService(request, function(response, status){
		console.log("Got game result: " + response);

	    var textOut = "";
	    if(response.home_score == response.away_score){
	        textOut = "The game will tie.";
	    }else{
	        var winningTeam = "";
	        var winningScore = "";
	        var losingTeam = "";
	        var losingScore = "";
	
	        if(response.home_score > response.away_score){
	            winningTeam = response.home_team;
	            winningScore = response.home_score;
	            losingTeam = response.away_team;
	            losingScore = response.away_score;
	        } else {
	            winningTeam = response.away_team;
	            winningScore = response.away_score;
	            losingTeam = response.home_team;
	            losingScore = response.home_score;
	        }
	        
	        textOut = winningTeam + " will win against the " + losingTeam + " " + winningScore + " to " + losingScore + ".";
	    }
	    
	    updatePredictionResult(textOut);
	});
}

function resetPrediction(){
	predictionContainer.hide();
	predictionText.empty();
}

function updatePredictionResult(resultString){
	predictionContainer.show();
	predictionText.text(resultString);
}

function makeCallToService(request, process){
	var fullEndpoint = serviceEndpoint + request;
	console.log("Hitting endpoint: " + fullEndpoint);
	
	$.get(fullEndpoint, process);
}
