package team.B2018;

import java.net.UnknownHostException;


public class Main {

	public static void main(String[] args) throws UnknownHostException {
		CommandTeam team1 = new CommandTeam("bolsonaros");
		CommandTeam team2 = new CommandTeam("haddads");
		
		team1.launchTeamAndServer();
		team2.launchTeam();
	}
}

