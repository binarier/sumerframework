package com.huateng.frame.security.server.home;

import com.huateng.frame.gwt.server.HibernateServerHome;
import com.huateng.frame.orm.TypedProperty;
import com.huateng.frame.security.shared.model.TblSecRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TblSecRoleServerHome extends HibernateServerHome<TblSecRole, String> {
    public static final TypedProperty<String> RoleId = new TypedProperty<String>("roleId");

    public static final TypedProperty<String> RoleName = new TypedProperty<String>("roleName");

    public static final TypedProperty<String> Authorities = new TypedProperty<String>("authorities");

    @Autowired
    public TblSecRoleServerHome(SessionFactory sessionFactory) {
        super(TblSecRole.class);
        setSessionFactory(sessionFactory);
    }
}