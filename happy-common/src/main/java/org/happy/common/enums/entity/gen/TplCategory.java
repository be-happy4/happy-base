package org.happy.common.enums.entity.gen;

import org.happy.common.core.domain.code.I18nCode;

public enum TplCategory implements I18nCode<String> {
    CRUD,
    TREE,
    SUB,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
