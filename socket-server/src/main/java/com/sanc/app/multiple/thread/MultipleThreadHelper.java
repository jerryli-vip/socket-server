package com.sanc.app.multiple.thread;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultipleThreadHelper<E> {

	public static final int THREAD_NUM = 10;
	public static final long TIME_OUT = 24 * 3600;

	private List<E> dataList;
	private IExecutor<E> executor;

	public MultipleThreadHelper(List<E> dataList, IExecutor<E> executor) {
		this.dataList = dataList;
		this.executor = executor;
	}

	public void execute() throws Exception {
		final String METHOD_NAME = "execute";
		if (dataList == null || dataList.size() == 0) {
			System.out.println(METHOD_NAME + " Data list can not be empty!");
			return;
		}

		if (executor == null) {
			System.out.println(METHOD_NAME + "An implementation of interface IExecutor must be offered!");
			return;
		}

		final ArrayBlockingQueue<E> queue = new ArrayBlockingQueue<E>(dataList.size());
		for (int k = 0; k < dataList.size(); k++) {
			queue.offer(dataList.get(k));
		}

		long start = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
		for (int i = 0; i < THREAD_NUM; i++) {
			Runnable task = new Runnable() {
				public void run() {
					System.out.println(METHOD_NAME + Thread.currentThread().getName()
							+ " is starting....................");
					int count = 0;
					while (queue.size() > 0) {
						try {
							executor.execute(queue.poll());
						} catch (Exception e) {
							e.printStackTrace();
						}
						count++;
					}
					System.out.println(METHOD_NAME + Thread.currentThread().getName() + " count : " + count);
				}
			};
			executorService.submit(task);
		}
		executorService.shutdown();
		executorService.awaitTermination(TIME_OUT, TimeUnit.SECONDS);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println(METHOD_NAME + "The time costing in million is :" + time);
	}
}
