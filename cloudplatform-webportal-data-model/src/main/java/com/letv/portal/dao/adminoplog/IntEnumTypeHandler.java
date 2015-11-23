package com.letv.portal.dao.adminoplog;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.letv.portal.model.adminoplog.IntEnum;

public class IntEnumTypeHandler<E extends Enum<E> & IntEnum> extends
		BaseTypeHandler<E> {

	private Class<E> type;
	private final E[] enums;

	public IntEnumTypeHandler(Class<E> type) {
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null) {
			throw new IllegalArgumentException(type.getSimpleName()
					+ " does not represent an enum type.");
		}
	}

	private E toEnum(int i) {
		for (E e : enums) {
			if (e.toInt() == i) {
				return (E) e;
			}
		}
		throw new IllegalArgumentException("Cannot convert " + i + " to "
				+ type.getSimpleName() + ".");
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.toInt());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		int i = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return toEnum(i);
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		int i = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return toEnum(i);
		}
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		int i = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return toEnum(i);
		}
	}

}
