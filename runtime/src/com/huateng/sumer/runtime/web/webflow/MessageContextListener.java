package com.huateng.sumer.runtime.web.webflow;

import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.RequestContext;

public class MessageContextListener extends FlowExecutionListenerAdapter {
	private static final ThreadLocal<MessageContext> messageContext = new ThreadLocal<MessageContext>();
	
	public static MessageContext getCurrentMessageContext()
	{
		MessageContext mc = messageContext.get();
		return mc;
	}
	
	@Override
	public void resuming(RequestContext context) {
		messageContext.set(context.getMessageContext());
	}
}
