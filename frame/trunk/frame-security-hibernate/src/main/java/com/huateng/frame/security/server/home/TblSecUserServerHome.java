package com.huateng.frame.security.server.home;

import com.huateng.frame.gwt.server.HibernateServerHome;
import com.huateng.frame.orm.TypedProperty;
import com.huateng.frame.security.shared.domain.DmnScmUserStatus;
import com.huateng.frame.security.shared.model.TblSecUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TblSecUserServerHome extends HibernateServerHome<TblSecUser, String> {
    public static final TypedProperty<String, Void> UserId = new TypedProperty<String, Void>("userId");

    public static final TypedProperty<String, Void> UserName = new TypedProperty<String, Void>("userName");

    public static final TypedProperty<String, Void> Email = new TypedProperty<String, Void>("email");

    public static final TypedProperty<String, DmnScmUserStatus> Status = new TypedProperty<String, DmnScmUserStatus>("status");

    @Autowired
    public TblSecUserServerHome(SessionFactory sessionFactory) {
        super(TblSecUser.class);
        setSessionFactory(sessionFactory);
    }
}