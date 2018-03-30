package com.fable.industry.api.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步调用
 * 
 * @author nimj
 */
public class FutureTaskUtil<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(FutureTaskUtil.class);

	public static <T> T call(Callable<T> task) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		FutureTask<T> futureTask = new FutureTask<T>(task);
		executor.submit(futureTask);
		executor.shutdown();
		try {
			return futureTask.get(5, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			LOGGER.error("异步调用失败！", e);
			return null;
		}
	}

	public static <T> void callNoReturn(Callable<T> task) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new FutureTask<T>(task));
		executor.shutdown();
	}
}
