package com.yandui.test;

import java.util.HashMap;
import java.util.concurrent.Callable;

import com.yandui.HttpUtil;

public class Task implements Callable{

	@Override
	public Object call() throws Exception {
		HashMap<String,String> map = new HashMap<String,String>();
		int num = 0;
		System.out.println("子线程任务正在执行……");
		num++;
		System.out.println("num = "+num);
		map.put("key", String.valueOf(num));
		//Thread.sleep(900);
		HttpUtil.sendGet("http://baidu.com", "a=1");
		return map;
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for(int i=0;i<10;i++){
			System.out.println(i);
			HttpUtil.sendGet("http://baidu.com", "a=1");
		}
		long endTime = System.currentTimeMillis();
		System.out.println(" time :"+(endTime-startTime));
	}

}
