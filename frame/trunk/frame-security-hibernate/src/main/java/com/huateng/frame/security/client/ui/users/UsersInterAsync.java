package com.huateng.frame.security.client.ui.users;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FetchResponse;

public interface UsersInterAsync {

	void listUsers(FetchRequest request, AsyncCallback<FetchResponse> callback);

	void createUser(Map values, AsyncCallback<Void> callback);

	void updateUser(String id, Map values, AsyncCallback<Void> callback);

	void lockUser(String id, AsyncCallback<Void> callback);

	void unlockUser(String id, AsyncCallback<Void> callback);

}
