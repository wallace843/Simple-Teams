package bolsonaro.team;

import simple_soccer_lib.PlayerCommander;
import simple_soccer_lib.perception.FieldPerception;
import simple_soccer_lib.perception.MatchPerception;
import simple_soccer_lib.perception.PlayerPerception;
import simple_soccer_lib.utils.Vector2D;


public class CommandPlayer extends Thread {
	private int LOOP_INTERVAL = 100;
	private PlayerCommander commander;
	private PlayerPerception selfPerc;
	private FieldPerception  fieldPerc;
	private MatchPerception matchPerc;
	
	
	public CommandPlayer(PlayerCommander player) {
		commander = player;
	}

	@Override
	public void run() {
		System.out.println(">> Executando...");
		long nextIteration = System.currentTimeMillis() + LOOP_INTERVAL;
		updatePerceptions();
		while(commander.isActive()) {
			switch (selfPerc.getUniformNumber()) {
			case 1:
				//acaoGoleiro(nextIteration);
				break ;
			case 2:
				//acaoArmador(nextIteration, -1); // cima
				break ;
			case 3:
				//acaoArmador(nextIteration, 1); // baixo
				break ;
			case 4:
				//acaoAtacante(nextIteration);
				break ;
			default : 
				break ;
		}
		}
	}	
	private void updatePerceptions() {
		PlayerPerception newSelf =
		commander.perceiveSelfBlocking();
		FieldPerception newField =
		commander.perceiveFieldBlocking();
		MatchPerception newMatch =
		commander.perceiveMatchBlocking();
		if (newSelf != null ) this .selfPerc = newSelf;
		if (newField != null ) this .fieldPerc = newField;
		if (newMatch != null ) this .matchPerc = newMatch;
		}
}
