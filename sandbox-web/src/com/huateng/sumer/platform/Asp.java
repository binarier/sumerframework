package com.huateng.sumer.platform;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.webflow.execution.RequestContext;

import com.huateng.sumer.runtime.web.support.BusinessException;

@Aspect
public class Asp {
	
	@Pointcut("execution(public boolean org.springframework.webflow.engine.Transition.canExecute(..)) &&args(context)")
	public void anyOldTransfer(RequestContext context){}

	@Around("anyOldTransfer(context)")
	public Object doBasicProfiling(ProceedingJoinPoint pjp, RequestContext context) throws Throwable 
	{
		try
		{
			return pjp.proceed(new Object[]{context});
		}
		catch(Exception e)
		{
			context.getMessageContext().addMessage(new MessageBuilder().defaultText(e.getMessage()).error().build());
			return false;
		}
	}
}
