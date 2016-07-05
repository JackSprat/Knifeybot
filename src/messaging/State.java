package messaging;

public class State {

	private static Boolean silentMode = false;
	
	public static boolean isSilentMode() {
		synchronized(silentMode) {
			return silentMode;
		}
	}
	
	public static void setSilentMode(boolean isSilent) {
		synchronized(silentMode) {
			silentMode = isSilent;
		}
	}
	
}