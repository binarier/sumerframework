package com.huateng.frame.security.server.home;

import com.huateng.frame.gwt.server.HibernateServerHome;
import com.huateng.frame.orm.TypedProperty;
import com.huateng.frame.security.shared.model.MapSecUserRole;
import com.huateng.frame.security.shared.model.MapSecUserRoleKey;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MapSecUserRoleServerHome extends HibernateServerHome<MapSecUserRole, MapSecUserRoleKey> {
    public static final TypedProperty<String> UserId = new TypedProperty<String>("userId");

    public static final TypedProperty<String> RoleId = new TypedProperty<String>("roleId");

    @Autowired
    public MapSecUserRoleServerHome(SessionFactory sessionFactory) {
        super(MapSecUserRole.class);
        setSessionFactory(sessionFactory);
    }
}