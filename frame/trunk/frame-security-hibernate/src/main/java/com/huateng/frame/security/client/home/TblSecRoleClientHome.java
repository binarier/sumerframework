package com.huateng.frame.security.client.home;

import com.huateng.frame.gwt.client.ui.TextColumnHelper;

public class TblSecRoleClientHome {

    public static final TextColumnHelper<String> RoleId() {
        return new TextColumnHelper<String>("roleId", "角色ID", 20);
    }

    public static final TextColumnHelper<String> RoleName() {
        return new TextColumnHelper<String>("roleName", "角色名", 80);
    }

    public static final TextColumnHelper<String> Authorities() {
        return new TextColumnHelper<String>("authorities", "权限集", 0);
    }
}