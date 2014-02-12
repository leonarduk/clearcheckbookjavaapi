package com.leonarduk.clearcheckbook.processor.parallel;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

public class MonitorThread implements Runnable {
	private ThreadPoolExecutor executor;

	private static final Logger _logger = Logger.getLogger(MonitorThread.class);
	private int seconds;

	private boolean run = true;

	public MonitorThread(ThreadPoolExecutor executor, int delay) {
		this.executor = executor;
		this.seconds = delay;
	}

	public void shutdown() {
		this.run = false;
	}

	@Override
	public void run() {
		while (run) {
			_logger.debug(String
					.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
							this.executor.getPoolSize(),
							this.executor.getCorePoolSize(),
							this.executor.getActiveCount(),
							this.executor.getCompletedTaskCount(),
							this.executor.getTaskCount(),
							this.executor.isShutdown(),
							this.executor.isTerminated()));
			try {
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}