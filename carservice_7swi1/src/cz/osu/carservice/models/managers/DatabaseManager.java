package cz.osu.carservice.models.managers;

import cz.osu.carservice.models.mySQL.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private final Database database;

    public DatabaseManager() {
        this.database = new Database();
    }

    public ArrayList<Map<String, Object>> selectFromDatabase(String sql) {
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> row = null;
        try {
            this.database.openConnection();
            Statement statement = this.database.getConnection().createStatement();
            ResultSet rs=statement.executeQuery(sql);


            ResultSetMetaData metaData = rs.getMetaData();
            Integer columnCount = metaData.getColumnCount();

            while (rs.next()) {
                row = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }

        } catch (Exception e) {
            System.err.println("Chyba při komunikaci s databází!");
            System.err.println(e.getMessage());
        } finally {
            this.database.closeConnection();
        }

        return resultList;
    }

    public void insertIntoDatabase(String sql, ArrayList<String> data) {
        try {
            this.database.openConnection();

            PreparedStatement insertCommand = this.database.getConnection().prepareStatement(sql);

            for (int x = 1; x <= data.size(); x++) {
                insertCommand.setString(x, data.get(x - 1));
            }

            insertCommand.executeUpdate();

        } catch (Exception e) {
            System.err.println("Got an SQL insert exception!");
            System.err.println(e.getMessage());
        } finally {
            this.database.closeConnection();
        }
    }
}
