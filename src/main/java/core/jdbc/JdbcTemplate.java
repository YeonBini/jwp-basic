package core.jdbc;

import core.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate  {
//    final private Logger log = LoggerFactory.getLogger(JdbcTemplate.class);

    public void update(String sql, PreparedStatementSetter pss){
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pss.setValues(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void update(String sql, Object... parameters) {
        update(sql, createPreparedStatementSetter(parameters));
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss){

        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pss.setValues(pstmt);
            ResultSet rs = pstmt.executeQuery();

            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(rowMapper.mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        return query(sql, rowMapper, createPreparedStatementSetter(parameters));
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pss){
        List<T> result = query(sql, rowMapper, pss);
        if(result == null) return null;
        return result.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... parameters){
        List<T> result = query(sql, rowMapper, parameters);
        if(result == null) return null;
        return result.get(0);
    }


    private PreparedStatementSetter createPreparedStatementSetter(Object[] parameters) {
        return pstmt -> {
            int length = parameters.length;
            for(int i=1; i<= length; i++) {
                try {
                    pstmt.setString(i, parameters[i-1].toString());
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage());
                }
            }
        };
    }
}
