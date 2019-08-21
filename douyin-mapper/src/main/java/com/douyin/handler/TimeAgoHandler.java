package com.douyin.handler;

import com.douyin.utils.DateUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.handler
 * @date 2019/8/21 15:16
 */
public class TimeAgoHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        ;
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Date date = resultSet.getDate(s);
        return DateUtil.parseTime(date,new java.util.Date());
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Date date = resultSet.getDate(i);
        return DateUtil.parseTime(date,new java.util.Date());
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Date date = callableStatement.getDate(i);
        return DateUtil.parseTime(date,new java.util.Date());
    }

}
