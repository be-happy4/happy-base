package org.happy.common.enums.entity;

import org.happy.common.core.domain.code.I18nCode;

/**
 * Access status enumeration.
 *
 * @author happy
 */
public enum AccessStatus implements I18nCode<String> {
    SUCCESS,
    FAILED,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
