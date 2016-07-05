package pokemon;
import java.util.HashMap;
import java.util.Set;

public class RandomIntegerDistribution {

	private HashMap<Integer, Integer> probabilities = new HashMap<Integer, Integer>();
	private int total = 0;
	
	public void addValue(int index, int count) {
		
		probabilities.put(index, count);
		total += count;
		
	}
	public int getRandomValue() {
		
		int rand = (int) (Math.random() * total);
		Set<Integer> keys = probabilities.keySet();
		
		int probabilitySum = 0;
		for (Integer key : keys) {
			int value = probabilities.get(key);
			probabilitySum += value;
			if (probabilitySum > rand) return key;
		}
		return -1;
	}
	public boolean isEmpty() {
		return probabilities.isEmpty();
	}
	
}