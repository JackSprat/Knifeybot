package utils;

import java.io.File;
import java.io.IOException;

public class DirectoryUtils {

	public static void createDirectories(String path) {
	    File f = new File(path);
	    f.mkdirs();
	}
	
	public static void ensureFileExists(String path) {
		
		File f = new File(path);
		if (f.exists()) return;
		
		String[] dirs = path.split("\\");
		if (dirs.length > 1) {
			String newPath = "";
			for (int i = 0; i < dirs.length - 1; i++) {
				newPath += dirs[i] + "\\";
			}
			path = newPath;
		}
		
		File permData = new File(path);
		
		if (!permData.exists()) {
			try { permData.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
		}
	}
}
