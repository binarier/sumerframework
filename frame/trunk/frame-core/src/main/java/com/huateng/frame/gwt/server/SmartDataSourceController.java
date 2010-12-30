package com.huateng.frame.gwt.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.huateng.frame.gwt.client.ui.UIRestDataSource;

@Controller
@RequestMapping("/" + UIRestDataSource.defaultControllerMapping)
public class SmartDataSourceController
{

//	@Autowired
//	private Map<String, FetchOperation<?, SmartCriteria>> fetchDataSources;
	
	@Autowired
	private ApplicationContext applicationContext;

	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping("/fetch")
	protected void fetch(@RequestParam("_dataSource") String dataSource,
			@RequestParam("_startRow") int startRow,
			@RequestParam("_endRow") int endRow,
			@RequestParam(value = "_sortBy", required = false) String sortBy, 
			@RequestParam(value = "criteria", required = false) String criteriaJSONs[],
			@RequestParam(value = "operator", required = false) OperatorId operator,
			@RequestParam(value = "fieldName", required = false) String fieldName,
			@RequestParam(value = "value", required = false) String value,
			WebRequest request,
			HttpServletResponse httpServletResponse) throws Exception
	{
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

		FetchOperation<?, SmartCriteria> fo = applicationContext.getBean(dataSource, FetchOperation.class);
		if (fo != null)
		{
			SmartCriteria sc = null;
			// 解析criteria
			if (operator == OperatorId.and || operator == OperatorId.or)
			{
				if (criteriaJSONs != null)
				{
					sc = new SmartCriteria();
					sc.setOperator(operator);
					List<SmartCriteria> scs = new ArrayList<SmartCriteria>(criteriaJSONs.length);
					for (int i = 0; i < criteriaJSONs.length; i++)
					{
						scs.add(mapper.readValue(criteriaJSONs[i], SmartCriteria.class));
					}
					sc.setCriteria(scs);
				}
			}
			else if (operator != null)
			{
				sc = new SmartCriteria();
				sc.setOperator(operator);
				sc.setFieldName(fieldName);
				sc.setValue(value);
			}

			Response resp = new Response();

			resp.setResponse(fo.fetch(sc, startRow, endRow, sortBy));
			byte [] data = mapper.writeValueAsBytes(resp);
			httpServletResponse.setCharacterEncoding("utf-8");
			httpServletResponse.setContentType("application/json");
			httpServletResponse.setContentLength(data.length);
			httpServletResponse.getOutputStream().write(data);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
		else
		{
			//没有处理
			httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
