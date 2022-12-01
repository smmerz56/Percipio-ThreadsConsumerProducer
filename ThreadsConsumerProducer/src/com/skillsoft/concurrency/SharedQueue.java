package com.skillsoft.concurrency;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedQueue {
	Queue<String> queue;
	int capacity;//How much the producer can produce before waiting
	Lock queueLock = new ReentrantLock();//RenetrantLock allows us to create conditions
	Condition notFull = queueLock.newCondition();//Producers condition
	Condition notEmpty = queueLock.newCondition();//Consumers condition
	
	public SharedQueue(Queue<String> queue, int capacity) {
		this.queue = queue;
		this.capacity = capacity;
	}
	
	
}
