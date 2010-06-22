package com.huateng.sumer.runtime.web.webflow;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.webflow.execution.RequestContext;

@Aspect
public class EnginePatch {
	
	@Pointcut("execution(public boolean org.springframework.webflow.engine.Transition.canExecute(..)) &&args(context)")
	public void canExecute(RequestContext context){}

	@Around("canExecute(context)")
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
