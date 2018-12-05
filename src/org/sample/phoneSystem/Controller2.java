package org.sample.phoneSystem;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
//import java.util.concurrent.TimeUnit;

import org.sample.Main;

//import org.openjdk.jmh.annotations.*;

/**
 * This Controller uses the ConcurrentLinkedQueue<> provided by Java.
 * The controller is responsible for controlling the queue of calls
 * which the operators take.
 * 
 * @author Gage Davidson
 */
//@State(Scope.Thread)
public class Controller2 implements Runnable {
	
	final ConcurrentLinkedQueue<Call> callQueue;
	private final Operator[] operators;
	private final Thread[] threads;
	
	public Controller2() {
		this (Main.N_OPERATORS);
	}
	
	/**
	 * @param nOperators number of operators to host
	 */
	public Controller2(int nOperators) {
		callQueue = new ConcurrentLinkedQueue<>();
		operators = new Operator[nOperators];
		threads = new Thread[nOperators];
		
		for (int i = 0; i < nOperators; ++i) {
			operators[i] = new Operator(i) {
				@Override Call getCall()    { return pollQueue(); }
				@Override Call checkQueue() { return peekQueue(); }
			};
			
			threads[i] = new Thread(operators[i]);
		}
	}
	
	@Override
	public void run() {
		start();
		
		for (int i = 0; i < Main.CALL_COUNT; ++i) {
			boolean added = pushQueue(new Call());
			
			if (! added)
				System.out.println("* Queue full *");
			
			try {
				Thread.sleep(Main.CALL_SPREAD + ThreadLocalRandom.current().nextInt(Main.CALL_SPREAD));
			} catch (InterruptedException ex) {
			}
		}
		
		stop();
	}
	
//	@Benchmark
//	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean pushQueue(Call call) {
		return callQueue.add(call);
	}
	
//	@Benchmark
//	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public Call pollQueue() {
		return callQueue.poll();
	}
	
//	@Benchmark
//	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public Call peekQueue() {
		return callQueue.peek();
	}
	
	/**
	 * Starts the phone queue simulation.
	 */
	private void start() {
		System.out.println("Starting phone system");
		
		for (int i = 0; i < threads.length; ++i)
			threads[i].start();
	}
	
	/**
	 * Stops the phone queue simulation and closes threads.
	 */
	private void stop() {
		System.out.println("Stopping phone system");
		
		for (int i = 0; i < operators.length; ++i)
			threads[i].interrupt();
		
		System.out.println("Operators interrupted");
		
		for (int i = 0; i < operators.length; ++i)
			try {
				threads[i].join();
			} catch (InterruptedException ex) {
			}
		
		System.out.println("Phone system stopped");
	}
}
