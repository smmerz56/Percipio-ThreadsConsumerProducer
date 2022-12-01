package com.skillsoft.concurrency;

import java.util.concurrent.ArrayBlockingQueue;

/*
 		synchronized(sharedQueue) {
			if(sharedQueue.queue.size() == 0) {
				
				System.out.println("Queue is empty. "+consumerName+ " is waiting...");
				sharedQueue.wait();
				System.out.println(consumerName+" has woken up");
			}
		}
		synchronized(sharedQueue) {
			String item = sharedQueue.queue.remove();
			System.out.println(consumerName+" has consumed "+item);
			sharedQueue.notify();//if a producer is sleeping on the queue, then it will be woken and add to it
		}
 */

//See notes in Producer
//
//public class Consumer implements Runnable{
//	SharedQueue sharedQueue;
//	String consumerName;
//	int consumerCapacity;
//	
//	public Consumer(SharedQueue sharedQueue, String consumerName, int consumerCapacity) {
//		this.sharedQueue = sharedQueue;
//		this.consumerName = consumerName;
//		this.consumerCapacity = consumerCapacity;
//	}
//	
//	public void consume() throws InterruptedException{
//		sharedQueue.queueLock.lock();
//		if(sharedQueue.queue.size() == 0) {
//			System.out.println("Queue is empty. "+consumerName+ " is waiting...");
//			
//			sharedQueue.notEmpty.await();//relinquishes the lock it acquired, but unlike wait() when it is awoken
//										// the lock is returned to the thread
//			System.out.println(consumerName+" has woken up");
//		}
//		
//		String item = sharedQueue.queue.remove();
//		System.out.println(consumerName+" has consumed "+item);
//		
//		sharedQueue.notFull.signal();//Wake up any producer to inform them the queue isnt full anymore 
//		sharedQueue.queueLock.unlock();
//	}
//
//	@Override
//	public void run() {
//		for(int i = 0; i <consumerCapacity; i++) {
//			try {
//				Thread.sleep((long)(Math.random() * 1000)*5);//simulate a rate of consumption
//				consume();
//			}catch(InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println(consumerName+" has run its course");
//		
//	}
//}

public class Consumer implements Runnable{
	ArrayBlockingQueue<String> sharedQueue;
	String consumerName;
	int consumerCapacity;
	
	public Consumer(ArrayBlockingQueue<String> sharedQueue, String consumerName, int consumerCapacity) {
		this.sharedQueue = sharedQueue;
		this.consumerName = consumerName;
		this.consumerCapacity = consumerCapacity;
	}
	
	public void consume() throws InterruptedException{
		
		String item = sharedQueue.take();
		System.out.println(consumerName+" has consumed "+item);
		
	}

	@Override
	public void run() {
		for(int i = 0; i <consumerCapacity; i++) {
			try {
				Thread.sleep((long)(Math.random() * 1000)*5);//simulate a rate of consumption
				consume();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(consumerName+" has run its course");
		
	}
}
