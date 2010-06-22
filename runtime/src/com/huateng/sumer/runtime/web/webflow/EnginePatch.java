package com.huateng.sumer.runtime.web.webflow;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.webflow.execution.RequestContext;

/**
 * 对Webflow的流程引擎打补丁，详见各advice上的说明
 * @author chenjun.li
 *
 */
@Aspect
public class EnginePatch {
	
	@Pointcut("execution(public boolean org.springframework.webflow.engine.Transition.canExecute(..)) &&args(context)")
	public void canExecute(RequestContext context){}

	/**
	 * <p>canExecute会在每点发生Event时被调用，用来执行Transition的Criteria，即执行Transition时的各项动作。</p>
	 * <p>原版本问题在于，执行时如发生Exception，则就算有全局的Transition On Exception到原State，也会退出当前的State，导致View级别的变量丢失，这样使得使用Exception作为报错机制并且使用ViewState级var的情况下很麻烦。</p>
	 * <p>这个aspect可以截获canExecute调用，把发生的Exception转换成messageContext中的错误报出来，同时阻止Transition，可以更友好地报错。</p>
	 * <p>使用时需要在目标项目启用AspectJ，并且放一个aop.xml到src/META-INF目录中，用于启用本类，参见sumer platform项目。</p>
	 * @param pjp
	 * @param context
	 * @return
	 * @throws Throwable
	 */
	@Around("canExecute(context)")
	public Object aroundCanExecute(ProceedingJoinPoint pjp, RequestContext context) throws Throwable 
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
