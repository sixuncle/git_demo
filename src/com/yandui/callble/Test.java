package com.yandui.callble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.yandui.test.Task;

public class Test {

	public static ExecutorService es = Executors.newCachedThreadPool();
	public static HashMap<String, String> results = new HashMap<String, String>();
	
	public static final int TIME_OUT = 200;
	
	public static int DSP_COUNT = 10;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		try {
			List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
			for (int i = 0; i < DSP_COUNT; i++) {
				Task task = new Task();
				tasks.add(task); 
			}
			List<Future<Object>> result = null;
			try {
				result = es.invokeAll(tasks, TIME_OUT, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				for (int i = 0; i < result.size(); i++) {
					Future<Object> obj = result.get(i);
					Object str = obj.get();
					results.put(i + "", str.toString());
				}
			}
			System.err.println("-------------");
		} catch (CancellationException e) {
			System.err.println("有线程任务被中断");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("共耗时" + (endTime - startTime) + "ms");
			es.shutdown();
		}

		System.out.println("results = " + results);
	}

}
