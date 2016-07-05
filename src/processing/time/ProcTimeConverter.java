package processing.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.db4o.query.Predicate;

import processing.ProcBase;

public class ProcTimeConverter extends ProcBase {
	
	public ProcTimeConverter() {
		super(null, null);
		commands.add(new CommandTimeRequest());
		
	}
	
	public TimeZone getTimezone(String timezoneStr) {
		
		ZonedDateTime date1;
		ZoneId.getAvailableZoneIds()
		if (timezone == null) return null;
		return timezone.get(0);
	}
	
	public boolean setTimezone(String name, int hours, int minutes) {
		TimeZone newTZ = new TimeZone(name);
		if (minutes < 0 || minutes > 59) return false;
		if (hours < -14 || hours > 14) return false;
		
		newTZ.setTime(hours, minutes);
		
		database.store(newTZ);
		database.commit();
		
		return true;
	}
	
	public boolean removeTimezone(String name) {
		TimeZone tz = getTimezone(name);
		if (tz != null) {
			database.delete(tz);
			database.commit();
			return true;
		}
		return false;
	}
	
}

class TimeZone {

	private int hours;
	private int minutes;
	private String name;
	
	public TimeZone(String name) {
		hours = 0;
		minutes = 0;
		this.name = name;
	}
	
	public void setTime(int hours, int minutes) {
		if (hours >= -12 && hours <= 14 && minutes >= 0 && minutes < 60){
			this.hours = hours;
			this.minutes = minutes;
		} else {
			this.hours = 0;
			this.minutes = 0;
		}
	}
	
	public int getHours() {return hours;}
	public int getMinutes() {return minutes;}
	public String getName() {return name;}
	
}