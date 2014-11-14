package com.letv.portal.job;

import org.springframework.stereotype.Component;

@Component("jobExecute")
public class JobExecuteImpl implements IJobExecute {

	@Override
	public void test() {
		System.out.println("jobExcuteImpl  start。。。");
		
	}

}
