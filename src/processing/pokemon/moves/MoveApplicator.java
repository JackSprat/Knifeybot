package processing.pokemon.moves;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.pokemon.creation.PokemonObject;

public class MoveApplicator {

	public static void applyMove(PokemonObject user, PokemonObject target, Move move, BlockingQueue<OutgoingMessage> listOut) {
		
		if (move.hasFacet(MoveFacet.MAKES_CONTACT)) {
			//NULLIFY MOVE IF MAKES CONTACT
		}
		
		if (move.hasFacet(MoveFacet.AFFECTED_BY_KINGS_ROCK)) {
			//FLINCH APPLICATOR
		}
		
		if (move.hasFacet(MoveFacet.AFFECTED_BY_PROTECT)) {
			//PROTECT
		}
		
		
		if (move.hasFacet(MoveFacet.DEALS_DAMAGE)) {
			int damage = (int) calculateDamage(user, target, move, listOut);
			target.modifyHP(-damage);
		}
		
		if (move.hasFacet(MoveFacet.APPLIES_STATUS_EFFECT)) {
			if (move == Move.GROWL) {
				//apply attack stage mod
			}
			
		}

	}
	
	private static float calculateDamage(PokemonObject user, PokemonObject target, Move move, BlockingQueue<OutgoingMessage> listOut) {
		
		float damage = 0;
		
		//DAMAGE FORMULA PART 1
		damage = ((2 * user.getLevel()) + 10f) / 250;

		boolean isSpecial;
		
		switch (move.getMoveType()) {
			case FIRE: case WATER: case ELECTRIC: case GRASS: case ICE: case PSYCHIC: case DRAGON: case DARK:
				isSpecial = true;
				break;
			default:
				isSpecial = false;
				break;
		}
		damage *= isSpecial ? ((float)user.getSPAtk() / target.getSPDef()) : ((float)user.getAtk() / target.getDef()) * move.power;

		damage += 2;

		//DAMAGE MODIFIER
		
		//STAB
		damage *= user.hasType(move.getMoveType()) ? 1.5f : 1f;

		//EFFECTIVENESS
		float effectiveness = getEffectivenessValue(move.getMoveType(), target.getTypes());
		damage *= effectiveness;

		//CRITICAL
		
		//OTHER
		//calculate stage modifiers
		
		//RANDOM
		double randomMult = (Math.random() * 0.15) + 0.85;
		damage *= randomMult;
		
		return damage;
		
		
	}
	
	private static float getEffectivenessValue(MoveType moveType, MoveType[] targetType) {
		float effectiveness = 1f;
		
		for (MoveType targetInstance : targetType) {
			switch (moveType) {
				case NORMAL:
					if (targetInstance == MoveType.ROCK || targetInstance == MoveType.STEEL) effectiveness *= 0.5;
					if (targetInstance == MoveType.GHOST) return 0f;
					break;
				case FIGHTING:
					if (targetInstance == MoveType.FLYING || targetInstance == MoveType.POISON || targetInstance == MoveType.BUG || targetInstance == MoveType.PSYCHIC) effectiveness *= 0.5f;
					if (targetInstance == MoveType.NORMAL || targetInstance == MoveType.ROCK || targetInstance == MoveType.STEEL || targetInstance == MoveType.ICE || targetInstance == MoveType.DARK) effectiveness *= 2f;
					if (targetInstance == MoveType.GHOST) return 0f;
					break;
				case FLYING:
					if (targetInstance == MoveType.ROCK || targetInstance == MoveType.STEEL || targetInstance == MoveType.ELECTRIC) effectiveness *= 0.5f;
					if (targetInstance == MoveType.FIGHTING || targetInstance == MoveType.BUG || targetInstance == MoveType.GRASS) effectiveness *= 2f;
					break;
				case POISON:
					if (targetInstance == MoveType.POISON || targetInstance == MoveType.GROUND || targetInstance == MoveType.ROCK || targetInstance == MoveType.GHOST) effectiveness *= 0.5f;
					if (targetInstance == MoveType.GRASS) effectiveness *= 2f;
					if (targetInstance == MoveType.STEEL) return 0f;
					break;
				case GROUND:
					if (targetInstance == MoveType.BUG || targetInstance == MoveType.GRASS) effectiveness *= 0.5f;
					if (targetInstance == MoveType.POISON || targetInstance == MoveType.ROCK || targetInstance == MoveType.STEEL || targetInstance == MoveType.FIRE || targetInstance == MoveType.ELECTRIC) effectiveness *= 2f;
					if (targetInstance == MoveType.FLYING) return 0f;
					break;
				case ROCK:
					if (targetInstance == MoveType.FIGHTING || targetInstance == MoveType.GROUND || targetInstance == MoveType.STEEL) effectiveness *= 0.5f;
					if (targetInstance == MoveType.FLYING || targetInstance == MoveType.BUG || targetInstance == MoveType.FIRE || targetInstance == MoveType.ICE) effectiveness *= 2f;
					break;
				case BUG:
					if (targetInstance == MoveType.FIGHTING || targetInstance == MoveType.FLYING || targetInstance == MoveType.POISON || targetInstance == MoveType.GHOST || targetInstance == MoveType.STEEL || targetInstance == MoveType.FIRE) effectiveness *= 0.5f;
					if (targetInstance == MoveType.GRASS || targetInstance == MoveType.PSYCHIC || targetInstance == MoveType.DARK) effectiveness *= 2f;
					break;
				case GHOST:
					if (targetInstance == MoveType.STEEL || targetInstance == MoveType.DARK) effectiveness *= 0.5f;
					if (targetInstance == MoveType.GHOST || targetInstance == MoveType.PSYCHIC) effectiveness *= 2f;
					if (targetInstance == MoveType.NORMAL) return 0f;
					break;
				case STEEL:
					if (targetInstance == MoveType.STEEL || targetInstance == MoveType.FIRE || targetInstance == MoveType.WATER || targetInstance == MoveType.ELECTRIC) effectiveness *= 0.5f;
					if (targetInstance == MoveType.ROCK || targetInstance == MoveType.ICE) effectiveness *= 2f;
					break;
				case FIRE:
					if (targetInstance == MoveType.ROCK || targetInstance == MoveType.FIRE || targetInstance == MoveType.WATER || targetInstance == MoveType.DRAGON) effectiveness *= 0.5f;
					if (targetInstance == MoveType.BUG || targetInstance == MoveType.STEEL || targetInstance == MoveType.GRASS || targetInstance == MoveType.ICE) effectiveness *= 2f;
					break;
				case WATER:
					if (targetInstance == MoveType.WATER || targetInstance == MoveType.GRASS || targetInstance == MoveType.DRAGON) effectiveness *= 0.5f;
					if (targetInstance == MoveType.GROUND || targetInstance == MoveType.ROCK || targetInstance == MoveType.FIRE) effectiveness *= 2f;
					break;
				case GRASS:
					if (targetInstance == MoveType.FLYING || targetInstance == MoveType.POISON || targetInstance == MoveType.BUG || targetInstance == MoveType.STEEL || targetInstance == MoveType.FIRE || targetInstance == MoveType.GRASS || targetInstance == MoveType.DRAGON) effectiveness *= 0.5f;
					if (targetInstance == MoveType.GROUND || targetInstance == MoveType.ROCK || targetInstance == MoveType.WATER) effectiveness *= 2f;
					break;
				case ELECTRIC:
					if (targetInstance == MoveType.GRASS || targetInstance == MoveType.ELECTRIC || targetInstance == MoveType.DRAGON) effectiveness *= 0.5f;
					if (targetInstance == MoveType.FLYING || targetInstance == MoveType.WATER) effectiveness *= 2f;
					if (targetInstance == MoveType.GROUND) return 0f;
					break;
				case PSYCHIC:
					if (targetInstance == MoveType.STEEL || targetInstance == MoveType.PSYCHIC) effectiveness *= 0.5f;
					if (targetInstance == MoveType.FIGHTING || targetInstance == MoveType.POISON) effectiveness *= 2f;
					if (targetInstance == MoveType.DARK) return 0f;
					break;
				case ICE:
					if (targetInstance == MoveType.STEEL || targetInstance == MoveType.FIRE || targetInstance == MoveType.WATER || targetInstance == MoveType.ICE) effectiveness *= 0.5f;
					if (targetInstance == MoveType.FLYING || targetInstance == MoveType.GROUND || targetInstance == MoveType.GRASS || targetInstance == MoveType.DRAGON) effectiveness *= 2f;
					break;
				case DRAGON:
					if (targetInstance == MoveType.STEEL) effectiveness *= 0.5f;
					if (targetInstance == MoveType.DRAGON) effectiveness *= 2f;
					break;
				case DARK:
					if (targetInstance == MoveType.FIGHTING || targetInstance == MoveType.STEEL || targetInstance == MoveType.DARK) effectiveness *= 0.5f;
					if (targetInstance == MoveType.GHOST || targetInstance == MoveType.PSYCHIC) effectiveness *= 2f;
					break;
			}
		}
		
		return effectiveness;
		
	}
	
}