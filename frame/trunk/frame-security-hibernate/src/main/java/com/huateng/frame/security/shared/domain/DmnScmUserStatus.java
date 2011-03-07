package com.huateng.frame.security.shared.domain;

import com.huateng.frame.gwt.shared.DomainSupport;
import java.util.Map;

public class DmnScmUserStatus extends DomainSupport<String> {

    protected void fill(Map<String, String> map) {
        map.put("Normal", "正常");
        map.put("Locked", "锁定");
    }
}