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
public class SmartDataSourceController
{

	@Autowired
	private Map<String, FetchOperation<?, SmartCriteria>> fetchDataSources;

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("/fetch")
	protected void fetch(@RequestParam("_dataSource") String dataSource,
			@RequestParam("_startRow") int startRow,
			@RequestParam("_endRow") int endRow,
			@RequestParam(value = "_sortBy", required = false) String sortBy, 
			@RequestParam(value = "criteria", required = false) String criteriaJSONs[],
			@RequestParam(value = "operator", required = false) OperatorId operator,
			HttpServletResponse httpServletResponse) throws Exception
	{
		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

		if (fetchDataSources.containsKey(dataSource))
		{
			SmartCriteria sc = null;
			// 解析criteria
			if (criteriaJSONs != null)
			{
				if (criteriaJSONs.length > 1)
				{
					sc = new SmartCriteria();
					sc.setOperator(operator);
					SmartCriteria scs[] = new SmartCriteria[criteriaJSONs.length];
					for (int i = 0; i < criteriaJSONs.length; i++)
					{
						scs[i] = mapper.readValue(criteriaJSONs[i], SmartCriteria.class);
					}
					sc.setCriteria(scs);
				} else
				{
					sc = mapper.readValue(criteriaJSONs[0], SmartCriteria.class);
				}
			}

			Response resp = new Response();
			FetchOperation<?, SmartCriteria> sds = fetchDataSources.get(dataSource);

			resp.setResponse(sds.fetch(sc, startRow, endRow, sortBy));
			httpServletResponse.getOutputStream().write(mapper.writeValueAsBytes(resp));
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
	}

	static class Response
	{
		FetchResult<?> response;

		public FetchResult<?> getResponse()
		{
			return response;
		}

		public void setResponse(FetchResult<?> response)
		{
			this.response = response;
		}
	}
}
