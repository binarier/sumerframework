package com.huateng.sumer.runtime.web.webflow;

import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import org.springframework.util.Assert;

public class MessageContextUtils {
	public static void addMessage(MessageResolver mr)
	{
		MessageContext mc = MessageContextListener.getCurrentMessageContext();
		Assert.notNull(mc);
		
		mc.addMessage(mr);
	}
	
	public static boolean hasError()
	{
		MessageContext mc = MessageContextListener.getCurrentMessageContext();
		Assert.notNull(mc);
		
		return mc.hasErrorMessages();
	}
}
