package com.huateng.frame.security.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FetchResponse;
import com.huateng.frame.gwt.server.GWTService;
import com.huateng.frame.gwt.server.HibernateDataSourceHelper;
import com.huateng.frame.security.client.ui.users.UsersInter;
import com.huateng.frame.security.server.home.TblSecUserServerHome;

@Controller
public class UsersServer extends GWTService implements UsersInter{
	
	@Autowired
	private TblSecUserServerHome shUser;

	@Override
	@Transactional
	public FetchResponse listUsers(FetchRequest request) {
		return HibernateDataSourceHelper.processRequest(request, shUser.createCriteria());
	}
	
}
