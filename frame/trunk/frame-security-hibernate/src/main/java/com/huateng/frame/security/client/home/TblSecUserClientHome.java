package com.huateng.frame.security.client.home;

import com.huateng.frame.gwt.client.ui.TextColumnHelper;
import com.huateng.frame.gwt.shared.DomainSupport;
import com.huateng.frame.security.shared.domain.DmnScmUserStatus;

public class TblSecUserClientHome {

    public static final TextColumnHelper<String> UserId() {
        return new TextColumnHelper<String>("userId", "登录ID", 20);
    }

    public static final TextColumnHelper<String> UserName() {
        return new TextColumnHelper<String>("userName", "用户名", 80);
    }

    public static final TextColumnHelper<String> Email() {
        return new TextColumnHelper<String>("email", "EMAIL", 255);
    }

    public static final TextColumnHelper<String> Status() {
        return new TextColumnHelper<String>("status", "用户状态", 10){
            @Override
            public DomainSupport<String> getDomain(){
                return new DmnScmUserStatus();
            }
        };
    }
}