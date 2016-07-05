package pokemon.status;

public class StatusManager {

	private int critStage = 0, accuracyStage = 0, evasionStage = 0;

	public int getCritStage() {	return critStage; }
	public void addCritStage(int critStage) { this.critStage = Math.max(this.critStage + critStage, 0); }
	public int getAccuracyStage() { return accuracyStage; }
	public void addAccuracyStage(int accuracyStage) { this.accuracyStage = Math.max(this.critStage + critStage, 0); }
	
}