package begin;

import java.io.File;
import java.util.Set;

import channel.DataManager;
import messaging.WebReceiver;
import users.UserManager;

public class Test {
	public static void main (String[] args) {
		WebReceiver wr = new WebReceiver();
		Thread t = new Thread(wr);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
