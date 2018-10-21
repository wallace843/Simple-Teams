package team.B2018;

import java.util.ArrayList;

import simple_soccer_lib.PlayerCommander;
import simple_soccer_lib.perception.FieldPerception;
import simple_soccer_lib.perception.MatchPerception;
import simple_soccer_lib.perception.PlayerPerception;
import simple_soccer_lib.utils.EFieldSide;
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
					acaoGoleiro(nextIteration);
					break ;
				case 2:
					acaoArmador(nextIteration, 2);
					break ;
				case 3:
					acaoDefensor(nextIteration, 3);
					break ;
				case 4:
					acaoDefensor(nextIteration, 4);
					break ;
				case 5:
					acaoArmador(nextIteration, 5);
					break ;
				case 6:
					acaoArmador(nextIteration, 6);
					break ;
				case 7:
					acaoFixo(nextIteration);
					break ;
				default : 
					break ;
			}
		}
	}	
	
	private void acaoArmador(long nextIteration, int pos) {
		EFieldSide side = selfPerc.getSide();
		Vector2D ballPos;
		while(true) {
			updatePerceptions();
			ballPos = fieldPerc.getBall().getPosition();
			switch(matchPerc.getState()) {
			case BEFORE_KICK_OFF:
				if(pos == 2)
					commander.doMoveBlocking(-25d, -30d);
				else if(pos == 5)
					commander.doMoveBlocking(-25d, 30d);
				else if(pos == 6)
					commander.doMoveBlocking(-20d, 0d);
				break;
			default:
				break;
			}
		}
	}
	
	private void acaoDefensor(long nextIteration, int pos) {
		EFieldSide side = selfPerc.getSide();
		Vector2D ballPos;
		while(true) {
			updatePerceptions();
			ballPos = fieldPerc.getBall().getPosition();
			switch(matchPerc.getState()) {
			case BEFORE_KICK_OFF:
				if(pos == 3)
					commander.doMoveBlocking(-30d, -20d);
				else if(pos == 4)
					commander.doMoveBlocking(-30d, 20d);
				break;
			default:
				break;
			}
		}
	}
	
	private void acaoFixo(long nextIteration) {
		EFieldSide side = selfPerc.getSide();
		Vector2D ballPos;
		while(true) {
			updatePerceptions();
			ballPos = fieldPerc.getBall().getPosition();
			switch(matchPerc.getState()) {
			case BEFORE_KICK_OFF:
				commander.doMoveBlocking(-1d, -0d);
				break;
			case KICK_OFF_LEFT:
				passe(fieldPerc.getTeamPlayer(side, 6).getPosition());
			default:
				break;
			}
		}
	}
	
	private void acaoGoleiro(long nextIteration) {
		EFieldSide side = selfPerc.getSide();
		Vector2D ballPos;
		while(true) {
			updatePerceptions();
			ballPos = fieldPerc.getBall().getPosition();
			switch(matchPerc.getState()) {
			case BEFORE_KICK_OFF:
				commander.doMoveBlocking(-50d, 0d);
				break;
			default:
				break;
			}
		}
	}
	
	private void updatePerceptions() {
		PlayerPerception newSelf = commander.perceiveSelfBlocking();
		FieldPerception newField = commander.perceiveFieldBlocking();
		MatchPerception newMatch = commander.perceiveMatchBlocking();
		if(newSelf != null)
			this.selfPerc = newSelf;
		if(newField != null)
			this.fieldPerc = newField;
		if (newMatch != null ) 
			this.matchPerc = newMatch;
	}
	
	private void passe(Vector2D point){
		Vector2D newDirection = point.sub(selfPerc.getPosition());
		commander.doTurnToDirectionBlocking(newDirection);
		commander.doKickBlocking(130, 0);
	}
	
	private PlayerPerception getClosestPlayerPoint(Vector2D point, EFieldSide side, double margin){
		ArrayList<PlayerPerception> lp = fieldPerc.getTeamPlayers(side);
		PlayerPerception np = null ;
		if (lp != null && !lp.isEmpty()){
			double dist,temp;
			dist = lp.get(0).getPosition().distanceTo(point);
			np = lp.get(0);
			if (isPointsAreClose(np.getPosition(), point, margin))
				return np;
			for (PlayerPerception p : lp) {
				if (p.getPosition() == null ) 
					break ;
				if (isPointsAreClose(p.getPosition(), point, margin))
					return p;
				temp = p.getPosition().distanceTo(point);
				if (temp < dist){
					dist = temp;
					np = p;
				}
			}
		}
		return np;
	}
	
	private boolean isPointsAreClose(Vector2D reference, Vector2D point, double margin){
		return reference.distanceTo(point) <= margin;
	}
	
	private void dash(Vector2D point){
		if (selfPerc.getPosition().distanceTo(point) <= 1) 
			return ;
		if (!isAlignToPoint(point, 15)) 
			turnToPoint(point);
		commander.doDashBlocking(70);
	}
	
	private void turnToPoint(Vector2D point){
		Vector2D newDirection = point.sub(selfPerc.getPosition());
		commander.doTurnToDirectionBlocking(newDirection);
	}
	
	private boolean isAlignToPoint(Vector2D point, double margin){
		double angle = point.sub(selfPerc.getPosition()).angleFrom(selfPerc.getDirection());
		return angle < margin && angle > margin*(-1);
	}
}
