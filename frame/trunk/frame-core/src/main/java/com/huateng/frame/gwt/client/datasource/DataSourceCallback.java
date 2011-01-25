package com.huateng.frame.gwt.client.datasource;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DataSourceCallback implements AsyncCallback<FetchResponse>
{
	private DataSource datasource;
	private String requestId;
	
	public DataSourceCallback(DataSource datasource, String requestId)
	{
		this.datasource = datasource;
		this.requestId = requestId;
	}
	
	public void onFailure(Throwable caught)
	{
		SC.warn(caught.getLocalizedMessage());
	}
	
	public void onSuccess(FetchResponse result)
	{
		DSResponse response = new DSResponse();
		response.setTotalRows(result.getTotalRows());
		response.setStartRow(result.getStartRow());
		response.setEndRow(result.getEndRow());
		
		if (result.getFields() != null && result.getData() != null)
		{
			ListGridRecord records[] = new ListGridRecord[result.getData().length];
			for (int i=0; i<result.getData().length; i++)
			{
				Object row[] = result.getData()[i];
				records[i] = new ListGridRecord();
				if (row != null)
				{
					for (int j=0; j<row.length; j++)
					{
						records[i].setAttribute(result.getFields()[j], row[j]);
					}
				}
			}
			response.setData(records);
		}
		
		datasource.processResponse(requestId, response);
	}
}
