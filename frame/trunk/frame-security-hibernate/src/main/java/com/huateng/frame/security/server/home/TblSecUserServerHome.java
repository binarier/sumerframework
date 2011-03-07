package com.huateng.frame.security.server.home;

import com.huateng.frame.gwt.server.HibernateServerHome;
import com.huateng.frame.orm.TypedProperty;
import com.huateng.frame.security.shared.model.TblSecUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TblSecUserServerHome extends HibernateServerHome<TblSecUser, String> {
    public static final TypedProperty<String> UserId = new TypedProperty<String>("userId");

    public static final TypedProperty<String> UserName = new TypedProperty<String>("userName");

    public static final TypedProperty<String> Email = new TypedProperty<String>("email");

    public static final TypedProperty<String> Status = new TypedProperty<String>("status");

    @Autowired
    public TblSecUserServerHome(SessionFactory sessionFactory) {
        super(TblSecUser.class);
        setSessionFactory(sessionFactory);
    }
}