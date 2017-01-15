package users;

public enum PermissionClass {

	Banned,
	User,
	Sub,
	Mod,
	Admin,
	SuperAdmin;
	
	public static PermissionClass getPermissionClass(int s) {
		switch (s) {
		case 0: return Banned;
		case 1: return User;
		case 2: return Sub;
		case 3: return Mod;
		case 4: return Admin;
		case 5: return SuperAdmin;
		default: return User;
		}
	}
	
	public static PermissionClass getPermissionClass(String s) {
		switch (s.toLowerCase()) {
		case "banned": return Banned;
		case "user": return User;
		case "sub": return Sub;
		case "mod": return Mod;
		case "admin": return Admin;
		case "superadmin": return SuperAdmin;
		default: return User;
		}
	}
	
	public int getLevelID() {
		return this.ordinal();
	}
}