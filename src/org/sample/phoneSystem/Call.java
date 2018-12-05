package org.sample.phoneSystem;

import java.util.concurrent.ThreadLocalRandom;

import org.sample.Main;

//import org.openjdk.jmh.annotations.*;

/**
 * This represents a call coming into the phone queue.
 * 
 * @author Gage Davidson
 */
//@State(Scope.Thread)
public class Call {
	
	public final String number;
	public final int callTime; // amount of time it takes to take the call
	
	public Call() {
		number = randomPhoneNumber();
		callTime = ThreadLocalRandom.current().nextInt(Main.CALL_TIME_SPREAD) + Main.CALL_TIME_SPREAD;
	}
	
	@Override
	public String toString() {
		return number;
	}
	
	/**
	 * Produces a random phone number.
	 * @return random phone number
	 */
	private static String randomPhoneNumber() {
		return String.format("(%d%d%d) %d%d%d-%d%d%d%d",
				ThreadLocalRandom.current().nextInt(10),
				ThreadLocalRandom.current().nextInt(10),
				ThreadLocalRandom.current().nextInt(10),
				
				ThreadLocalRandom.current().nextInt(10),
				ThreadLocalRandom.current().nextInt(10),
				ThreadLocalRandom.current().nextInt(10),
				
				ThreadLocalRandom.current().nextInt(10),
				ThreadLocalRandom.current().nextInt(10),
				ThreadLocalRandom.current().nextInt(10),
				ThreadLocalRandom.current().nextInt(10));
	}
}
