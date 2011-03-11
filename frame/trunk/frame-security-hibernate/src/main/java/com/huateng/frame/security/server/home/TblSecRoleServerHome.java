package com.huateng.frame.security.server.home;

import com.huateng.frame.gwt.server.HibernateServerHome;
import com.huateng.frame.orm.TypedProperty;
import com.huateng.frame.security.shared.model.TblSecRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TblSecRoleServerHome extends HibernateServerHome<TblSecRole, String> {
    public static final TypedProperty<String, Void> RoleId = new TypedProperty<String, Void>("roleId");

    public static final TypedProperty<String, Void> RoleName = new TypedProperty<String, Void>("roleName");

    public static final TypedProperty<String, Void> Authorities = new TypedProperty<String, Void>("authorities");

    @Autowired
    public TblSecRoleServerHome(SessionFactory sessionFactory) {
        super(TblSecRole.class);
        setSessionFactory(sessionFactory);
    }
}