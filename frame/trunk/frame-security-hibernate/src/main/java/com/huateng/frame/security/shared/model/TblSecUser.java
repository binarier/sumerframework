package com.huateng.frame.security.shared.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TBL_SEC_USER")
public class TblSecUser implements Serializable {
    @Id
    @Column(name="USER_ID")
    private String userId;

    @Column(name="USER_NAME")
    private String userName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="STATUS")
    private String status;

    @OneToMany(mappedBy = "tblSecUser", cascade = CascadeType.ALL)
    private Set<MapSecUserRole> mapSecUserRoles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("email", email);
        map.put("status", status);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        this.setUserId((String)map.get("userId"));
        this.setUserName((String)map.get("userName"));
        this.setEmail((String)map.get("email"));
        this.setStatus((String)map.get("status"));
    }

    public Set<MapSecUserRole> getMapSecUserRoles() {
        return mapSecUserRoles;
    }

    public void setMapSecUserRoles(Set<MapSecUserRole> mapSecUserRoles) {
        this.mapSecUserRoles = mapSecUserRoles;
    }
}