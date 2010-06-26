package com.huateng.sumer.platform.service.sample.quickstart;

import java.util.List;

import com.huateng.sumer.platform.web.sample.quickstart.BrowseItem;
import com.huateng.sumer.runtime.service.api.BrowseService;
import com.huateng.sumer.runtime.web.meta.Pagination;

public class DemoBrowseService implements BrowseService<BrowseItem> {

	@Override
	public List<BrowseItem> browse(Object context, Pagination pagination) {
		
		return null;
	}

}
