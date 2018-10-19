package bolsonaro.team;

import java.net.UnknownHostException;


public class Main {

	public static void main(String[] args) throws UnknownHostException {
		CommandTeam team1 = new CommandTeam("A", 3, true);
		//CommandTeam team2 = new CommandTeam("g", 3, false);
		
		team1.launchTeamAndServer();
		//team2.launchTeam();
	}
	
}

