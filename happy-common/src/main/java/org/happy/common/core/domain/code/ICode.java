package org.happy.common.core.domain.code;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * code interface
 *
 * @param <K> Key type
 * @see <a href="https://stackoverflow.com/questions/23564506/is-it-possible-to-write-a-generic-enum-converter-for-jpa">
 * stackoverflow - Generic enum converter for JPA</a>
 */
public interface ICode<K> {
    /**
     * Key
     *
     * @return key
     */
    @JsonValue
    K getCode();

    static <K, E extends Enum<E> & ICode<K>> Map<K, E> getEnumCodeMapping(Class<E> clz) {
        return Collections.unmodifiableMap(Stream.of(clz.getEnumConstants())
                .collect(Collectors.toMap(
                        ICode::getCode,
                        it -> it)));
    }

    static <K, E extends ICode<K>> E ofNonNull(Function<K, E> mapping, @Nullable K code) {
        var res = mapping.apply(code);
        if (res == null) {
            throw new IllegalArgumentException("Illegal code: " + code);
        }
        return res;
    }

    static <E extends Enum<E>> E ofNonNull(Class<E> clz, @Nullable Integer code) {
        var res = ofNullable(clz, code);
        if (res == null) {
            throw new IllegalArgumentException("Illegal code: " + code);
        }
        return res;
    }

    @Nullable
    static <K, E extends ICode<K>> E ofNullable(Function<K, E> mapping, @Nullable K code) {
        return mapping.apply(code);
    }

    @Nullable
    static <E extends Enum<E>> E ofNullable(Class<E> clz, @Nullable Integer code) {
        if (code == null) {
            return null;
        }
        try {
            return clz.getEnumConstants()[code];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}