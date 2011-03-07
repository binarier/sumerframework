package com.huateng.frame.security.shared.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="MAP_SEC_USER_ROLE")
@IdClass(MapSecUserRoleKey.class)
public class MapSecUserRole implements Serializable {
    @Id
    @Column(name="USER_ID")
    private String userId;

    @Id
    @Column(name="ROLE_ID")
    private String roleId;

    @ManyToOne
    @JoinColumn(name="USER_ID", referencedColumnName = "USER_ID", updatable=false, insertable=false)
    private TblSecUser tblSecUser;

    @ManyToOne
    @JoinColumn(name="ROLE_ID", referencedColumnName = "ROLE_ID", updatable=false, insertable=false)
    private TblSecRole tblSecRole;

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

    public static MapSecUserRole createFromMap(Map<String, Serializable> map) {
        MapSecUserRole instance = new MapSecUserRole();
        instance.setUserId((String)map.get("userId"));
        instance.setRoleId((String)map.get("roleId"));
        return instance;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("userId", userId);
        map.put("roleId", roleId);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        this.setUserId((String)map.get("userId"));
        this.setRoleId((String)map.get("roleId"));
    }

    public TblSecUser getTblSecUser() {
        return tblSecUser;
    }

    public void setTblSecUser(TblSecUser tblSecUser) {
        this.tblSecUser = tblSecUser;
    }

    public TblSecRole getTblSecRole() {
        return tblSecRole;
    }

    public void setTblSecRole(TblSecRole tblSecRole) {
        this.tblSecRole = tblSecRole;
    }
}