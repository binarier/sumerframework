package com.huateng.sumer.runtime.service.api;

import java.util.List;

import com.huateng.sumer.runtime.web.meta.Pagination;

public interface BrowseService<T> {
	List<T> browse(Object context, Pagination pagination);
}
