package team.B2018;

import simple_soccer_lib.AbstractTeam;
import simple_soccer_lib.PlayerCommander;


public class CommandTeam extends AbstractTeam {

	public CommandTeam(String name) {
		super(name, 7, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void launchPlayer(int ag, PlayerCommander commander) {
		// TODO Auto-generated method stub
		System. out .println("Player lançado!");
		CommandPlayer p = new CommandPlayer(commander);
		p.start();
		
	}

	

}
