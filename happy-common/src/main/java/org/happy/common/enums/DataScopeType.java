package org.happy.common.enums;

import org.happy.common.core.domain.code.I18nCode;

public enum DataScopeType implements I18nCode<String> {
    /**
     * All data permissions
     */
    ALL,
    /**
     * Custom data permissions
     */
    CUSTOM,
    /**
     * Department data permissions
     */
    DEPT,
    /**
     * Department and child data permissions
     */
    DEPT_AND_CHILD,
    /**
     * Only self data permissions
     */
    SELF;

    @Override
    public String getCode() {
        return name();
    }
}
