/**
 * CheckSites
 *
 * @author ${author}
 * @since 10-Jul-2016
 */
package com.leonarduk.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Ping N web sites in parallel. The ping simply does a GET, and looks at the first header line.
 * This example could be applied to many sorts of similar tasks.
 * <P>
 * No time-out is used here. As usual, be wary of warm-up of the just-in-time compiler. You might
 * want to use -Xint.
 */
public final class CheckSites {

	/** The Constant URLs. */
	private static final List<String> URLs = Arrays.asList("http://www.youtube.com/",
	        "http://www.google.ca/", "http://www.date4j.net", "http://www.web4j.com");

	/**
	 * Log.
	 *
	 * @param aMsg
	 *            the a msg
	 */
	private static void log(final Object aMsg) {
		System.out.println(String.valueOf(aMsg));
	}

	/**
	 * Run this tool.
	 *
	 * @param aArgs
	 *            the arguments
	 */
	public static final void main(final String... aArgs) {
		final CheckSites checker = new CheckSites();
		try {
			CheckSites.log("Parallel, report each as it completes:");
			checker.pingAndReportEachWhenKnown();

			CheckSites.log("Parallel, report all at end:");
			checker.pingAndReportAllAtEnd();

			CheckSites.log("Sequential, report each as it completes:");
			checker.pingAndReportSequentially();
		}
		catch (final InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		catch (final ExecutionException ex) {
			CheckSites.log("Problem executing worker: " + ex.getCause());
		}
		catch (final MalformedURLException ex) {
			CheckSites.log("Bad URL: " + ex.getCause());
		}
		CheckSites.log("Done.");
	}

	/**
	 * Check N sites, in parallel, using up to 4 threads. Report the results only when all have
	 * completed.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 */
	void pingAndReportAllAtEnd() throws InterruptedException, ExecutionException {
		final Collection<Callable<PingResult>> tasks = new ArrayList<>();
		for (final String url : CheckSites.URLs) {
			tasks.add(new Task(url));
		}
		final int numThreads = CheckSites.URLs.size() > 4 ? 4 : CheckSites.URLs.size(); // max 4
		                                                                                // threads
		final ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		final List<Future<PingResult>> results = executor.invokeAll(tasks);
		for (final Future<PingResult> result : results) {
			final PingResult pingResult = result.get();
			CheckSites.log(pingResult);
		}
		executor.shutdown(); // always reclaim resources
	}

	// PRIVATE

	/**
	 * Check N sites, in parallel, using up to 4 threads. Report the result of each 'ping' as it
	 * comes in. (This is likely the style most would prefer.)
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecutionException
	 *             the execution exception
	 */
	void pingAndReportEachWhenKnown() throws InterruptedException, ExecutionException {
		final int numThreads = CheckSites.URLs.size() > 4 ? 4 : CheckSites.URLs.size(); // max 4
		                                                                                // threads
		final ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		final CompletionService<PingResult> compService = new ExecutorCompletionService<>(executor);
		for (final String url : CheckSites.URLs) {
			final Task task = new Task(url);
			compService.submit(task);
		}
		for (final String url : CheckSites.URLs) {
			final Future<PingResult> future = compService.take();
			CheckSites.log(future.get());
		}
		executor.shutdown(); // always reclaim resources
	}

	/**
	 * Check N sites, but sequentially, not in parallel. Does not use multiple threads at all.
	 *
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	void pingAndReportSequentially() throws MalformedURLException {
		for (final String url : CheckSites.URLs) {
			final PingResult pingResult = this.pingAndReportStatus(url);
			CheckSites.log(pingResult);
		}
	}

	/**
	 * Ping and report status.
	 *
	 * @param aURL
	 *            the a url
	 * @return the ping result
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	private PingResult pingAndReportStatus(final String aURL) throws MalformedURLException {
		final PingResult result = new PingResult();
		result.URL = aURL;
		final long start = System.currentTimeMillis();
		final URL url = new URL(aURL);
		try {
			final URLConnection connection = url.openConnection();
			final int FIRST_LINE = 0;
			final String firstLine = connection.getHeaderField(FIRST_LINE);
			result.SUCCESS = true;
			final long end = System.currentTimeMillis();
			result.TIMING = end - start;
		}
		catch (final IOException ex) {
			// ignore - fails
		}
		return result;
	}

	/** Simple struct to hold all the date related to a ping. */
	private static final class PingResult {
		
		/** The url. */
		String	URL;
		
		/** The success. */
		Boolean	SUCCESS;
		
		/** The timing. */
		Long	TIMING;

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Result:" + this.SUCCESS + " " + this.TIMING + " msecs " + this.URL;
		}
	}

	/** Try to ping a URL. Return true only if successful. */
	private final class Task implements Callable<PingResult> {
		
		/** The url. */
		private final String fURL;

		/**
		 * Instantiates a new task.
		 *
		 * @param aURL
		 *            the a url
		 */
		Task(final String aURL) {
			this.fURL = aURL;
		}

		/**
		 * Access a URL, and see if you get a healthy response.
		 *
		 * @return the ping result
		 * @throws Exception
		 *             the exception
		 */
		@Override
		public PingResult call() throws Exception {
			return CheckSites.this.pingAndReportStatus(this.fURL);
		}
	}
}
