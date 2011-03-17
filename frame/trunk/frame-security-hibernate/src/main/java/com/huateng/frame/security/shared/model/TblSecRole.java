package com.huateng.frame.security.shared.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TBL_SEC_ROLE")
public class TblSecRole implements Serializable {
    @Id
    @Column(name="ROLE_ID")
    private String roleId;

    @Column(name="ROLE_NAME")
    private String roleName;

    @Lob
    @Column(name="AUTHORITIES")
    private String authorities;

    public static final String P_RoleId = "roleId";

    public static final String P_RoleName = "roleName";

    public static final String P_Authorities = "authorities";

    @OneToMany(mappedBy = "tblSecRole", cascade = CascadeType.ALL)
    private Set<MapSecUserRole> mapSecUserRoles;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("roleId", roleId);
        map.put("roleName", roleName);
        map.put("authorities", authorities);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("roleId")) this.setRoleId((String)map.get("roleId"));
        if (map.containsKey("roleName")) this.setRoleName((String)map.get("roleName"));
        if (map.containsKey("authorities")) this.setAuthorities((String)map.get("authorities"));
    }

    public Set<MapSecUserRole> getMapSecUserRoles() {
        return mapSecUserRoles;
    }

    public void setMapSecUserRoles(Set<MapSecUserRole> mapSecUserRoles) {
        this.mapSecUserRoles = mapSecUserRoles;
    }
}