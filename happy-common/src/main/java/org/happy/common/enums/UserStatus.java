package org.happy.common.enums;

import lombok.Getter;
import org.happy.common.core.domain.code.I18nCode;

/**
 * 用户状态
 *
 * @author happy
 */
@Getter
public enum UserStatus implements I18nCode<String> {
    OK("正常"),
    DISABLE("停用"),
    DELETED("删除");

    private final String info;

    UserStatus(String info) {
        this.info = info;
    }

    @Override
    public String getCode() {
        return name();
    }
}
