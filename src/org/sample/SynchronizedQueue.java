package org.sample;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

import org.sample.phoneSystem.Call;

/**
 * This is a synchronous queue that can be accessed by multiple
 * threads at once. This is the queue which will be tested against
 * the ConcurrentLinkedQueue made by Java.
 * 
 * @author Gage Davidson
 */
public class SynchronizedQueue {
	
	private final Call[] array;
	private volatile int in, out;
	private final ReentrantLock inLock, outLock;
	
	/**
	 * @param sizeCap maximum number of calls allowed in queue
	 */
	public SynchronizedQueue(int sizeCap) {
		array = new Call[sizeCap];
		inLock = new ReentrantLock();
		outLock = new ReentrantLock();
	}
	
	/**
	 * Adds a call to the queue.
	 * @param call call to add
	 */
	public boolean push(Call call) {
		inLock.lock();
		try {
			if (array[in] != null)
				return false;
			
			array[in++] = call;
			
			if (in == array.length)
				in = 0;
		} finally {
			inLock.unlock();
		}
		
		return true;
	}
	
	/**
	 * Fetches the next call in queue and removes it from the queue.
	 * @return the next call in queue
	 */
	public Call poll() {
		outLock.lock();
		try {
			Call call = array[out];
			
			if (array[out] != null) {
				array[out++] = null;
				
				if (out == array.length)
					out = 0;
			}
			
			return call;
		} finally {
			outLock.unlock();
		}
	}
	
	/**
	 * Fetches the next call in queue without removing it from the queue
	 * @return
	 */
	public Call peek() {
		return array[out];
	}
	
	@Override
	public String toString() {
		return Arrays.toString(array);
	}
}
