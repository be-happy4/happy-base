package org.happy.common.enums;

import lombok.Getter;
import org.happy.common.core.domain.code.I18nCode;

public enum RCode implements I18nCode<String> {
    _0,

    /**
     * User related error
     */
    _10,
    /**
     * Unauthorized
     */
    _1010,

    /**
     * Unknown error
     */
    _99,
    ;
    public static final String I18N_PREFIX = "response.code.";

    @Getter
    private final String code;

    RCode() {
        var code = name();
        if (code.startsWith("_")) {
            code = code.substring(1);
        }
        this.code = code;
    }

    @Override
    public String getI18nKey() {
        return I18N_PREFIX + code;
    }

    public boolean isSuccess() {
        return this == _0;
    }
}
