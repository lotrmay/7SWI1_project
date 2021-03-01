package cz.osu.carservice.models.managers;

import cz.osu.carservice.models.mySQL.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {
    private final Database database;

    public DatabaseManager() {
        this.database = new Database();
    }

    public String testSelect(int id){
        String test="";

        try{
            this.database.openConnection();
            Statement statement = this.database.getConnection().createStatement();

            String sql = "SELECT * FROM `customer` WHERE `id` LIKE "+ id + " LIMIT 1";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                test = resultSet.getString(3) + " " + resultSet.getString(4);
            }

        }catch (Exception e){
            System.err.println("Chyba při komunikaci s databází!");
            System.err.println(e.getMessage());
        }finally {
            this.database.closeConnection();
        }

        return test;
    }

    public void insertIntoDatabase(String sql, ArrayList<String> data) {
        try {
            this.database.openConnection();

            PreparedStatement insertCommand = this.database.getConnection().prepareStatement(sql);

            for (int x = 1;x<=data.size();x++) {
                insertCommand.setString(x, data.get(x-1));
            }

            insertCommand.executeUpdate();

        } catch (Exception e) {
            System.err.println("Got an SQL insert exception!");
            System.err.println(e.getMessage());
        }finally {
            this.database.closeConnection();
        }
    }
}
