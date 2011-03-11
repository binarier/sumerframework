package com.huateng.frame.security.shared.domain;

import com.huateng.frame.gwt.shared.DomainSupport;
import java.util.Map;

public class DmnScmUserStatus extends DomainSupport<String> {
    /**
     * 正常
     */
    public static final String NORMAL = "Normal";

    /**
     * 锁定
     */
    public static final String LOCKED = "Locked";

    protected void fill(Map<String, String> map) {
        map.put(NORMAL, "正常");
        map.put(LOCKED, "锁定");
    }
}