package com.huateng.frame.security.client.ui.users;

import java.util.Map;

import com.google.gwt.rpc.client.RpcService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FetchResponse;

@RemoteServiceRelativePath("spring/frameUsersServer")
public interface UsersInter extends RpcService {

	FetchResponse listUsers(FetchRequest request);
	
	Map getUser(String id);

	void createUser(Map values);
	
	void updateUser(String id, Map values);
	
	void lockUser(String id);
	
	void unlockUser(String id);
	
}
