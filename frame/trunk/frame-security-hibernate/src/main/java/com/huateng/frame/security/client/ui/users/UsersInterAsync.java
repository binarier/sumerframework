package com.huateng.frame.security.client.ui.users;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FetchResponse;

public interface UsersInterAsync {

	void listUsers(FetchRequest request, AsyncCallback<FetchResponse> callback);

}
