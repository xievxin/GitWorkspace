package com.xx.concurrent;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {

	private int corePoolSize;

	/**
	 * 任务执行队列
	 */
	private BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);

	HashSet<Worker> workers = new HashSet<>();

	/**
	 * 动态生成
	 * @return
	 */
	public static ThreadPool newCachedThreadPool() {
		return new ThreadPool(Integer.MAX_VALUE);
	}

	/**
	 * 指定大小线程池
	 * @param corePoolSize
	 * @return
	 */
	public static ThreadPool newFixedThreadPool(int corePoolSize) {
		return new ThreadPool(corePoolSize);
	}

	/**
	 * 只有一个线程
	 * @return
	 */
	public static ThreadPool singleThreadPool() {
		return new ThreadPool(1);
	}

	private ThreadPool(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void execute(Runnable runnable) throws InterruptedException {
		if(workers.size()>=corePoolSize) {
			workingQueue.offer(runnable);
		}else {
			addWorker(runnable);
		}
	}

	private void addWorker(Runnable runnable) {
		Worker w = new Worker(runnable);
		workers.add(w);
		w.thread.start();
	}

	private void runWorker(Worker w) {
		Runnable task = w.firstTask;
		while(task!=null || (task=getTask())!=null) {
			try {
				task.run();
			} finally {
				task = null;
			}
		}

		workers.remove(w);
	}

	private Runnable getTask() {
		try {
			return workingQueue.poll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	class Worker implements Runnable {

		Runnable firstTask;
		Thread thread;

		public Worker(Runnable firstTask) {
			this.firstTask = firstTask;
			this.thread = new Thread(this);
		}

		@Override
		public void run() {
			runWorker(this);
		}
	}

}