package com.huateng.frame.security.client.ui.users;

import com.google.gwt.rpc.client.RpcService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.huateng.frame.gwt.client.datasource.FetchRequest;
import com.huateng.frame.gwt.client.datasource.FetchResponse;

@RemoteServiceRelativePath("spring/security/users")
public interface UsersInter extends RpcService {

	FetchResponse listUsers(FetchRequest request);

}
