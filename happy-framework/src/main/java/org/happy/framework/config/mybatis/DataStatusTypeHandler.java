package org.happy.framework.config.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.happy.common.enums.DataStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@MappedJdbcTypes(JdbcType.OTHER) // or VARCHAR if it's treated as string
@MappedTypes(DataStatus.class)
public class DataStatusTypeHandler extends BaseTypeHandler<DataStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DataStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.name(), Types.OTHER);
    }

    @Override
    public DataStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String val = rs.getString(columnName);
        return val != null ? DataStatus.valueOf(val) : null;
    }

    @Override
    public DataStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String val = rs.getString(columnIndex);
        return val != null ? DataStatus.valueOf(val) : null;
    }

    @Override
    public DataStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String val = cs.getString(columnIndex);
        return val != null ? DataStatus.valueOf(val) : null;
    }
}
