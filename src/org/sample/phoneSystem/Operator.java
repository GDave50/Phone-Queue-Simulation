package org.sample.phoneSystem;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Gage Davidson
 */
abstract class Operator implements Runnable {
	
	private final int id;
	
	/**
	 * @param id operator ID
	 */
	Operator(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		while (! Thread.currentThread().isInterrupted())
			takeCall();
	}
	
	private void takeCall() {
		try {
			Call call = getCall();
			if (call == null) {
				Thread.sleep(50 + ThreadLocalRandom.current().nextInt(250));
				return;
			}
			
			Thread.sleep(call.callTime / 2);
			
			Call nextCall = checkQueue();
			
			// If there is no other calls in queue, take your time
			// with current call (1.5x original duration)
			if (nextCall == null) {
				Thread.sleep(call.callTime);
				System.out.printf("Operator%d took time with call %s\n", id, call);
			} else {
				Thread.sleep(call.callTime / 2);
				System.out.printf("Operator%d took call %s\n", id, call);
			}
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			return;
		}
	}
	
	abstract Call getCall();
	abstract Call checkQueue();
}
