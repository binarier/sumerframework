package com.huateng.frame.security.client.home;

import com.huateng.frame.gwt.client.ui.TextColumnHelper;

public class MapSecUserRoleClientHome {

    public static final TextColumnHelper<String> UserId() {
        return new TextColumnHelper<String>("userId", "登录ID", 20);
    }

    public static final TextColumnHelper<String> RoleId() {
        return new TextColumnHelper<String>("roleId", "角色ID", 20);
    }
}