package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;
import java.util.List;

public class FormDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<AbstractLayout> layouts;

	public List<AbstractLayout> getLayouts() {
		return layouts;
	}

	public void setLayouts(List<AbstractLayout> layouts) {
		this.layouts = layouts;
	}

}
