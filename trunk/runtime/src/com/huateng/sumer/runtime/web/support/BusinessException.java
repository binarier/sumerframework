package com.huateng.sumer.runtime.web.support;

/**
 * ������װȫ��ҵ���쳣��
 * TODO ��ʱû�п��ǹ��ʻ�
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
