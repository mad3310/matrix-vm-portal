package com.letv.portal.model.task.service;

import com.letv.portal.model.task.TaskChain;
import com.letv.portal.model.task.TaskChainIndex;
import com.letv.portal.model.task.TaskResult;


public interface ITaskEngine {

	public TaskChainIndex init(Long taskId,Object params);
	public TaskChainIndex init(String taskName,Object params);
	
	public void run(Long taskId);
	public void run(String taskName);
	
	public void run(Long taskId,Object params);
	public void run(String taskName,Object params);

	public void run(TaskChainIndex tci);
	
	public void run(TaskChain tc);
	
	public void run(TaskChain tc,TaskChainIndex tci);
	
	public TaskChain beforeExecute(TaskChain tc);
	
	public TaskChain afterExecute(TaskChain tc,TaskResult tr);
	
	public void execute(TaskChain tc,TaskChainIndex tci);

}
