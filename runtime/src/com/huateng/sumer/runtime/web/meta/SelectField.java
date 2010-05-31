package com.huateng.sumer.runtime.web.meta;

import com.huateng.sumer.runtime.service.api.Dictionary;

public class SelectField extends AbstractInputField {

	private static final long serialVersionUID = -8580640075559715297L;
	
	private Dictionary options;

	public Dictionary getOptions() {
		return options;
	}

	public void setOptions(Dictionary options) {
		this.options = options;
	}

	@Override
	public Object getReference(Object context) {
		return options.asMap(context);
	}
}
