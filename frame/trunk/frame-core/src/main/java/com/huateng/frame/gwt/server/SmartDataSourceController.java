package com.huateng.frame.gwt.server;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.huateng.frame.gwt.client.ui.UIRestDataSource;


@Controller
@RequestMapping("/" + UIRestDataSource.defaultControllerMapping)
public class SmartDataSourceController{
	
	@Autowired
	private Map<String, FetchOperation<?>> fetchDataSources;

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("/fetch")
	protected void fetch(
			@RequestParam("_dataSource") String dataSource,
			@RequestParam("_startRow") int startRow,
			@RequestParam("_endRow") int endRow,
			HttpServletResponse httpServletResponse) throws Exception
	{
		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

		if (fetchDataSources.containsKey(dataSource))
		{
			Response resp = new Response();
			FetchOperation<?> sds = fetchDataSources.get(dataSource);
			
			resp.setResponse(sds.fetch(null, startRow, endRow));
			httpServletResponse.getOutputStream().write(mapper.writeValueAsBytes(resp));
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	static class Response{
		FetchResult<?> response;

		public FetchResult<?> getResponse() {
			return response;
		}

		public void setResponse(FetchResult<?> response) {
			this.response = response;
		}
	}
}
