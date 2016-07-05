package users;

public enum PermissionClass {

	Banned,
	User,
	Sub,
	Mod,
	Admin,
	SuperAdmin;
	
	public static PermissionClass getPermissionClass(String s) {
		switch (s.toLowerCase()) {
		case "banned": return Banned;
		case "user": return User;
		case "sub": return Sub;
		case "mod": return Mod;
		case "admin": return Admin;
		case "superadmin": return SuperAdmin;
		default: return null;
		}
	}
	
}