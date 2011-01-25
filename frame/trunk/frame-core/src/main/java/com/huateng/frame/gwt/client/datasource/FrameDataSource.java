package com.huateng.frame.gwt.client.datasource;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.DSProtocol;

public abstract class FrameDataSource extends DataSource
{
	public FrameDataSource()
	{
		setDataProtocol(DSProtocol.CLIENTCUSTOM);
		setClientOnly(true);
	}
	
	@Override
	protected Object transformRequest(DSRequest dsRequest)
	{
		FetchRequest request = new FetchRequest();
//		fr.setCriteriaJSON(JSON.encode(dsRequest.getCriteria().getJsObj()));
		request.setStartRow(dsRequest.getStartRow());
		request.setEndRow(dsRequest.getEndRow());
		//fr.setSortBy(dsRequest.getSortBy());
		fetchData(request, new DataSourceCallback(this, dsRequest.getRequestId()));
		return null;
	}
	
	public abstract void fetchData(FetchRequest fetchRequest, DataSourceCallback callback);
}
