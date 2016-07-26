package processing.pokemon.moves;

public enum MoveName {

	TACKLE(33),
	GROWL(45);
	
	private final int value;
	
	private MoveName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
	
}