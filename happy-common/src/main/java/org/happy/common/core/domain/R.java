package org.happy.common.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.happy.common.constant.HttpStatus;
import org.happy.common.enums.RCode;
import org.jetbrains.annotations.NotNull;

import static org.happy.common.enums.RCode._0;
import static org.happy.common.enums.RCode._99;

/**
 * Response result
 *
 * @author happy
 */
public record R<T>(
        T data,
        @NotNull
        RCode code,
        String msg) {
    public static <T> R<T> ok() {
        return of(null, _0, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return of(data, _0, "操作成功");
    }

    public static <T> R<T> ok(T data, String msg) {
        return of(data, _0, msg);
    }

    public static <T> R<T> fail() {
        return of(null, _99, "操作失败");
    }

    public static <T> R<T> fail(String msg) {
        return of(null, _99, msg);
    }

    public static <T> R<T> fail(T data, String msg) {
        return of(data, _99, msg);
    }

    public static <T> R<T> fail(RCode code, String msg) {
        return of(null, code, msg);
    }

    public static <T> R<T> fail(T data, RCode code, String msg) {
        return of(data, code, msg);
    }

    private static <T> R<T> of(T data, RCode code, String msg) {
        return new R<>(data, code, msg);
    }

    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code.isSuccess();
    }
}
