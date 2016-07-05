package processing.command;

public class CommandObject {

	public String command;
	public String alias;
	public boolean global;
	public String user = "NONE";
	
	@Override
	public String toString() {
		return (this.global ? "" : user + ": ") + command;
	}
	
	public CommandObject(String user, boolean global, String alias, String command) {
		this.global = global;
		if (!global) this.user = user;
		this.alias = alias;
		this.command = command;
	}
	
}