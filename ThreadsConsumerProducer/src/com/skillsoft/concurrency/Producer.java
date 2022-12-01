package com.skillsoft.concurrency;

import java.util.concurrent.ArrayBlockingQueue;

/* Weird and inferior to the method used below
 	public void produce(String item) throws InterruptedException {
		
		synchronized(sharedQueue) {
			if(sharedQueue.queue.size() >= sharedQueue.capacity) {
				
				System.out.println("Queue is full. Producer is waiting...");
				sharedQueue.wait();//will need to be woken up by consumer consuming from the queue
				System.out.println("Producer has woken up");
			}
		}
		synchronized(sharedQueue) {
			sharedQueue.queue.add(item);
			System.out.println("Produced : "+item);
			sharedQueue.notify();//must notify because if added to an empty queue, then there may be consumers waiting to consume	
		}
	}
 */

//Overly complex due to the fact the Java has created an ArrayBlockingQueue seen below...but
// what is below will be necessary if your own data structure (ie not a Queue) needs to be used.
// There are other data structures that can be used like the ArrayBlocking Queue
//
//public class Producer implements Runnable{
//	
//	SharedQueue sharedQueue;
//	
//	public Producer(SharedQueue sharedQueue) {
//		this.sharedQueue = sharedQueue;
//	}
//	
//	String[] items = {"ItemOne", "ItemTwo", "ItemThree", "ItemFour", "ItemFive",
//					"ItemSix", "ItemSeven", "ItemEight", "ItemNine", "ItemTen"};
//	
//	public void produce(String item) throws InterruptedException {
//		
//		String threadName = Thread.currentThread().getName();
//		
//		sharedQueue.queueLock.lock();
//		if(sharedQueue.queue.size() >= sharedQueue.capacity) {
//			System.out.println("Queue is full. "+threadName+" is waiting...");
//			
//			sharedQueue.notFull.await();//relinquishes the lock it acquired, but unlike wait() when it is awoken
//										// the lock is returned to the thread
//		}
//		
//		sharedQueue.queue.add(item);
//		System.out.println(threadName+" produced : "+item);
//		
//		sharedQueue.notEmpty.signal();//Wake up any thread that is waiting for the queue to not be empty
//		sharedQueue.queueLock.unlock();
//	}
//
//	@Override
//	public void run() {
//		
//		for(int i = 0; i < items.length; i++) {
//			try {
//				Thread.sleep((long)(Math.random() * 1000)*5);//simulate a rate of production
//				produce(items[i]);
//			}catch(InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("Producer has run its course");
//	}
//	
//}

public class Producer implements Runnable{
	
	ArrayBlockingQueue<String> sharedQueue;//Dont need to perform any synchronization on our own whe nusing this
	
	public Producer(ArrayBlockingQueue<String> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}
	
	String[] items = {"ItemOne", "ItemTwo", "ItemThree", "ItemFour", "ItemFive",
					"ItemSix", "ItemSeven", "ItemEight", "ItemNine", "ItemTen"};
	
	public void produce(String item) throws InterruptedException {
		
		String threadName = Thread.currentThread().getName();		
		sharedQueue.put(item);//takes care of synchronization and blocking conditions implemented above
		System.out.println(threadName+" produced : "+item);
		
	}

	@Override
	public void run() {
		
		for(int i = 0; i < items.length; i++) {
			try {
				Thread.sleep((long)(Math.random() * 1000)*5);//simulate a rate of production
				produce(items[i]);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName()+" has run its course");
	}
	
}
