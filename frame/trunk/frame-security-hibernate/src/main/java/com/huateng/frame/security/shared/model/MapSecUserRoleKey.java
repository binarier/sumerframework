package com.huateng.frame.security.shared.model;

import java.io.Serializable;

public class MapSecUserRoleKey implements Serializable {
    private String userId;

    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String toString() {
        return String.valueOf(userId) + "|" + String.valueOf(roleId);
    }
}