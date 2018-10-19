package bolsonaro.team;

import simple_soccer_lib.AbstractTeam;
import simple_soccer_lib.PlayerCommander;


public class CommandTeam extends AbstractTeam {

	public CommandTeam(String name, int players, boolean withGoalie) {
		super(name, players, withGoalie);
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
