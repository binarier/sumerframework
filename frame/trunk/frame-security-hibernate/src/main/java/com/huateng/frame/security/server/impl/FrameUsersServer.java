package com.huateng.frame.security.server.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FetchResponse;
import com.huateng.frame.gwt.server.GWTService;
import com.huateng.frame.gwt.server.HibernateDataSourceHelper;
import com.huateng.frame.security.client.ui.users.UsersInter;
import com.huateng.frame.security.server.home.TblSecUserServerHome;
import com.huateng.frame.security.shared.domain.DmnScmUserStatus;
import com.huateng.frame.security.shared.model.TblSecUser;

@Controller
public class FrameUsersServer extends GWTService implements UsersInter{
	
	@Autowired
	private TblSecUserServerHome shUser;

	@Override
	@Transactional
	public FetchResponse listUsers(FetchRequest request) {
		return HibernateDataSourceHelper.processRequest(request, shUser.createCriteria());
	}

	@Override
	@Transactional
	public void createUser(Map values) {
		TblSecUser user = new TblSecUser();
		user.updateFromMap(values);
		shUser.save(user);
	}

	@Override
	public void updateUser(String id, Map values) {
		TblSecUser user = shUser.load(id);
		user.updateFromMap(values);
	}

	@Override
	@Transactional
	public void lockUser(String id) {
		TblSecUser user = shUser.load(id);
		user.setStatus(TblSecUserServerHome.Status.domain().LOCKED);
	}

	@Override
	@Transactional
	public void unlockUser(String id) {
		TblSecUser user = shUser.load(id);
		user.setStatus(TblSecUserServerHome.Status.domain().NORMAL);
	}
	
	
}
