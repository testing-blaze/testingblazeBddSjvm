package com.automation.ryder.library.core;

import java.sql.*;
import java.util.Map;

public class DataBases {
    private SqlDB sqlDB;

    public SqlDB SQL() {
        return sqlDB == null ? sqlDB = new SqlDB() : sqlDB;
    }


    public class SqlDB {

        public Connection TogetConnection(String connectionString, String username, String password) throws SQLException, ClassNotFoundException {
            return DriverManager.getConnection(connectionString, username, password);
        }


        public ResultSet ToQuery(Connection con, String sql) throws SQLException {
            Statement statement = con.createStatement();
            return statement.executeQuery(sql);
        }

        public ResultSet ToExecuteSqlQueryBatch(Connection con, String sql, int batchSize) throws SQLException {
            Statement statement = con.createStatement();
            statement.setFetchSize(batchSize);
            return statement.executeQuery(sql);
        }

        public int ToUpdateQuery(Connection con, String sql) throws SQLException {
            Statement state = con.createStatement();
            return state.executeUpdate(sql);
        }

        public PreparedStatement ForPreparedStatementQuery(Connection con, String sql) throws SQLException {
            Statement state = con.createStatement();
            state.executeUpdate(sql);
            return con.prepareStatement(sql);
        }

        public CallableStatement ForCallablePreparedStatement(Connection con, String sql) throws SQLException {
            con.setAutoCommit(false);
            CallableStatement state = con.prepareCall(sql);
            state.execute();
            return con.prepareCall(sql);
        }

        public int ToExecuteStoredProcWithParams(Connection con, Map<String, String> inputParams, String Storedprocname) {
            String quest = null;
            for (int i = 0; i < inputParams.size(); i++) {
                quest = quest + "?,";
            }
            quest = quest + "?";
            try {
                String sql = "{call " + Storedprocname + "(" + quest + ")}";
                CallableStatement stmt = con.prepareCall(sql);
                int count = 0;
                for (String k : inputParams.keySet()) {
                    count = count + 1;
                    switch (inputParams.get(k)) {
                        case "String":
                            stmt.setString(count, k);
                            break;
                        case "Integer":
                            stmt.setInt(count, Integer.parseInt(k));
                            break;
                        default:
                            stmt.setString(count, k);

                    }
                }
                count = count + 1;
                stmt.registerOutParameter(count, Types.INTEGER);
                return stmt.executeUpdate();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return 0;
        }

        public ResultSet ToExecuteStoredProcedureQuery(
                Connection conn, String spName, Map<String, Object> paramItems) {
            try {
                StringBuffer sqlBuf = new StringBuffer("DECLARE	@return_value int EXEC @return_value =  ");
                sqlBuf.append(spName);
                int paramCount = 1;
                for (String paramName : paramItems.keySet()) {
                    sqlBuf.append(
                            (paramCount++ > 1 ? ", " : " ") +
                                    (paramName.startsWith("@") ? "" : "@") + paramName + "=?");
                }

                sqlBuf.append(" SELECT	'Return Value' = @return_value");
                String sql = sqlBuf.toString();
                PreparedStatement ps = conn.prepareStatement(sql);
                paramCount = 1;
                for (String paramName : paramItems.keySet()) {
                    ps.setObject(paramCount++, paramItems.get(paramName));
                }
                ResultSet rs = ps.executeQuery();
                return rs;
            } catch (Exception e) {
                return null;
            }
        }

    }
}
