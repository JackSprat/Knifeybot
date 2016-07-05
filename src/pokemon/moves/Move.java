package pokemon.moves;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import pokemon.Effectiveness;
import pokemon.Pokemon;
import pokemon.moves.facets.FacetType;

public class Move {
	
	private BaseValues value;
	private int pp;
	
	public Move(BaseValues value) {
		this.value = value;
		this.pp = value.getBasePP();
	}
	
	public String getName() {
		return value.name();
	}
	
	public boolean execute(Pokemon user, Pokemon target, BlockingQueue<OutgoingMessage> listOut) {
		
		if (this.pp < 1) {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Move doesn't have enough PP!", "pokket"));
			return false;
		}
		float modifier = 1f;
		if (value.hasFacet(FacetType.CONTACT)) {
			//TODO: Implement KingsShield SpikyShield, var. abilities, rocky helmet and sticky barb
		}
		
		int damage = 0;
		
		if (!value.isCategory(Category.STATUS)) damage = runAttackFormula(user, target, modifier, listOut);
		
		listOut.add(new OutgoingMessage(OutType.CHAT, "Move does " + damage + " HP damage", "pokket"));
		target.damageHP(damage);
		
		this.pp--;
		return true;
		
	}
	
	private int runAttackFormula(Pokemon user, Pokemon target, float otherModifier, BlockingQueue<OutgoingMessage> listOut) {
		
		int level = user.getLevel();
		float attack = value.isCategory(Category.SPECIAL) ? user.getSPAttack() : user.getAttack();
		float defense = value.isCategory(Category.SPECIAL) ? target.getSPDefense() : target.getDefense();
		int base = value.getBasePower();
		float STAB = (value.getType() == user.getType1() || value.getType() == user.getType2()) ? 1.5f : 1f;
		float type = Effectiveness.getEffectivenessMultiplier(value.getType(), target.getType1()) * Effectiveness.getEffectivenessMultiplier(value.getType(), target.getType2());
		float critProb;
		switch (user.statuses.getCritStage()) {
			case 0:	critProb = 1/16f; break;
			case 1: critProb = 2/16f; break;
			case 2: critProb = 8/16f; break;
			default: critProb = 1f; break;
		}
		
		float crit = Math.random() < critProb ? 1.5f : 1f;
		
		float modifier = (float) (STAB * type * crit * otherModifier * (1 - Math.random() * 0.15));
		
		return (int)((((2 * level + 10)/250f) * (attack/defense) * (base + 2)) * modifier);
		
	}
	
}