package org.happy.framework.config.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.happy.common.core.domain.code.ICode;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class MybatisEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private final Class<E> enumClassType;
    private final Class<?> propertyType;
    private final Function<E, ?> getInvoker;

    public MybatisEnumTypeHandler(Class<E> enumClassType) {
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enumClassType = enumClassType;
        if (ICode.class.isAssignableFrom(enumClassType)) {
            try {
                var getCode = enumClassType.getDeclaredMethod("getCode");
                this.propertyType = getCode.getReturnType();
                this.getInvoker = e -> {
                    try {
                        return getCode.invoke(e);
                    } catch (ReflectiveOperationException ex) {
                        throw new RuntimeException("Failed to invoke getCode method", ex);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.propertyType = String.class;
            this.getInvoker = Enum::name;
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
            throws SQLException {
        if (jdbcType == null) {
            ps.setObject(i, this.getValue(parameter), Types.OTHER);
        } else {
            // see r3589
            ps.setObject(i, this.getValue(parameter), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName, this.propertyType);
        if (null == value && rs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getObject(columnIndex, this.propertyType);
        if (null == value && rs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object value = cs.getObject(columnIndex, this.propertyType);
        if (null == value && cs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    private E valueOf(Object value) {
        E[] es = this.enumClassType.getEnumConstants();
        return Arrays.stream(es).filter((e) -> equalsValue(value, getValue(e))).findAny().orElse(null);
    }

    protected boolean equalsValue(Object sourceValue, Object targetValue) {
        String sValue = String.valueOf(sourceValue).trim();
        String tValue = String.valueOf(targetValue).trim();
        if (sourceValue instanceof Number && targetValue instanceof Number
            && new BigDecimal(sValue).compareTo(new BigDecimal(tValue)) == 0) {
            return true;
        }
        return Objects.equals(sValue, tValue);
    }

    private Object getValue(E object) {
        return this.getInvoker.apply(object);
    }
}