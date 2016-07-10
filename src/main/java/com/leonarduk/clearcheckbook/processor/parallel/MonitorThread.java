/**
 * MonitorThread
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.clearcheckbook.processor.parallel;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * The Class MonitorThread.
 */
public class MonitorThread implements Runnable {

	/** The Constant _logger. */
	private static final Logger _logger = Logger.getLogger(MonitorThread.class);

	/** The executor. */
	private final ThreadPoolExecutor executor;

	/** The seconds. */
	private final int seconds;

	/** The run. */
	private boolean run = true;

	/**
	 * Instantiates a new monitor thread.
	 *
	 * @param executor
	 *            the executor
	 * @param delay
	 *            the delay
	 */
	public MonitorThread(final ThreadPoolExecutor executor, final int delay) {
		this.executor = executor;
		this.seconds = delay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (this.run) {
			MonitorThread._logger.debug(String.format(
			        "[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
			        this.executor.getPoolSize(), this.executor.getCorePoolSize(),
			        this.executor.getActiveCount(), this.executor.getCompletedTaskCount(),
			        this.executor.getTaskCount(), this.executor.isShutdown(),
			        this.executor.isTerminated()));
			try {
				Thread.sleep(this.seconds * 1000);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		this.run = false;
	}
}
