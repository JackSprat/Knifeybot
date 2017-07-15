package logger;

import java.util.Calendar;
import java.util.Date;

import utils.TextUtils;

public class Logger {

	private static LoggingLevel logLevel = LoggingLevel.TRACE; 
	
	public static void TRACE	(String messageIn) { logError(messageIn, LoggingLevel.TRACE); }
	public static void DEBUG	(String messageIn) { logError(messageIn, LoggingLevel.DEBUG); }
	public static void INFO		(String messageIn) { logError(messageIn, LoggingLevel.INFO); }
	public static void WARNING	(String messageIn) { logError(messageIn, LoggingLevel.WARNING); }
	public static void ERROR	(String messageIn) { logError(messageIn, LoggingLevel.ERROR); }
	public static void FATAL	(String messageIn) { logError(messageIn, LoggingLevel.FATAL); }
	public static void STACK	(String messageIn, Exception ex) { logError(messageIn, LoggingLevel.ERROR); ex.printStackTrace(); }
	
	private static void logError(String messageIn, LoggingLevel severity) {
		
		utils.DirectoryUtils.createDirectories("logs");
		
		if (severity.ordinal() > logLevel.ordinal()) return;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		String dateString = "" + 	String.format("%02d:%02d:%02d", 
										cal.get(Calendar.HOUR_OF_DAY),
										cal.get(Calendar.MINUTE),
										cal.get(Calendar.SECOND))
									+ " " +
									String.format("%02d/%02d/%04d", 
										cal.get(Calendar.DAY_OF_MONTH),
										cal.get(Calendar.MONTH) + 1,
										cal.get(Calendar.YEAR));
		String[] callingClass = getCallingClass().split("\\.");
		String callingString = callingClass[callingClass.length - 1];
		String outString = "[" + dateString + "] - " + TextUtils.setLength(TextUtils.setLength(severity.toString(), 8) + callingString, 20) + " - " + messageIn + "\n";
		System.out.println(TextUtils.cleanNewlines(outString));
	
	}
	
	private static String getCallingClass() {
		return Thread.currentThread().getStackTrace()[4].getClassName();
	}
	
}