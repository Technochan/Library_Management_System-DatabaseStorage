package com.zsgs.chandru.librarymanagement.databasejdbctypestorage;

import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.createdatabaseandtables.SetupJdbcDatabase;
import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databaseconnectionobject.DatabaseConnection;
import com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databasemanage.RetrieveDataFromDatabase;

import java.sql.*;

public class JdbcDatabaseCheck {

    public void checkDatabase()  {
        try(Connection connection = new DatabaseConnection().getConnection()) {
            if (!createDatabaseIfNotExists("libraryManagementSystem",connection)){
                new SetupJdbcDatabase().createDatabaseSetup("libraryManagementSystem");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean createDatabaseIfNotExists(String dbName, Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getCatalogs();
        boolean databaseExists = false;
        while (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(dbName)) {
                databaseExists = true;
                break;
            }
        }
        return databaseExists;
    }

}
