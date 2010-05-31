package com.huateng.sumer.runtime.web.support;

/**
 * 用来包装全局业务异常。
 * TODO 暂时没有考虑国际化
 * @author chenjun.li
 * 
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String defaultMessage) {
		super(defaultMessage);
	}

	public BusinessException(String defaultMessage, String code) {
		super(defaultMessage);
	}

	public BusinessException(String defaultMessage, Throwable t) {
		super(defaultMessage);
	}

	public BusinessException(String defaultMessage, String code, Throwable t) {
		super(defaultMessage);
	}
}
