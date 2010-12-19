package com.huateng.frame.maven;

import org.apache.ibatis.ibator.internal.NullProgressCallback;
import org.apache.maven.plugin.logging.Log;

public class IbatorMavenProgressCallback extends NullProgressCallback {
	private Log log;

	public IbatorMavenProgressCallback(Log log)
	{
		this.log = log;
	}
	
	@Override
	public void startTask(String taskName) {
		log.info(taskName);
	}

}
