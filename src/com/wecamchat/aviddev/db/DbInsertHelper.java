package com.wecamchat.aviddev.db;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DbInsertHelper {

	private static DbInsertHelper sInstance;
	private BlockingQueue<Runnable> workQueue;
	private ThreadPoolExecutor executor;
	private static final Integer NO_OF_CORES = Runtime.getRuntime()
			.availableProcessors();

	private DbInsertHelper() {
		workQueue = new LinkedBlockingQueue<Runnable>();
		executor = new ThreadPoolExecutor(NO_OF_CORES, 10, 2, TimeUnit.SECONDS,
				workQueue);
	}

	public static DbInsertHelper getInstance() {
		if (sInstance == null) {
			sInstance = new DbInsertHelper();
		}
		return sInstance;
	}
	
	public void submitNewTask(Runnable runnable){
		executor.submit(runnable);
	}

}
