package org.sample;

import org.sample.phoneSystem.Controller1;
import org.sample.phoneSystem.Controller2;

/**
 * This program is a parallel simulated phone queue where each
 * operator in the queue is a thread taking calls from the queue.
 * The purpose of this simulation is to test an synchronous queue
 * made by myself against the ConcurrentLinkedQueue made by Java.
 * 
 * @author Gage Davidson
 */
public class PhoneQueueSimulation {
	
	public static final int CALL_COUNT = 500; // Program takes 12500ms-25000ms
	public static final int CALL_SPREAD = 25; // Call comes in every 25-50ms
	public static final int CALL_TIME_SPREAD = 1000; // Call takes 1000-2000ms to take
	
	public static final int N_OPERATORS = 25;
	public static final int QUEUE_SIZE = 100;
	
	/**
	 * @param args command line arguments (must be a 1 or 2)
	 */
	public static void main(String[] args) {
		getController(args).run();
	}
	
	/**
	 * Determines which controller to use based on the command line arguments.
	 * @param args command line arguments
	 * @return Controller
	 */
	private static Runnable getController(String[] args) {
		if (args.length != 1)
			wrongUsage();
		
		int controllerNumber = 0;
		
		try {
			controllerNumber = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
			wrongUsage();
		}
		
		switch (controllerNumber) {
		case 1:
			return new Controller1(N_OPERATORS, QUEUE_SIZE);
		case 2:
			return new Controller2(N_OPERATORS);
		default:
			wrongUsage();
			return null;
		}
	}
	
	/**
	 * Called when the parameters at the command line are invalid.
	 */
	private static void wrongUsage() {
		System.out.println("Incorrect usage. Parameter must be 1 or 2.");
		System.exit(0);
	}
}
