package cz.osu.carservice.models.managers;

import cz.osu.carservice.models.mySQL.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    private Database database;

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

    public void testInsert(String name,String surname) {
        try {
            this.database.openConnection();

            PreparedStatement insertCommand = this.database.getConnection()
                    .prepareStatement(
                            "INSERT INTO `customer` (`id`, `id_address`, `firstName`, `surname`, `telephoneNumber`, `email`) VALUES (NULL, '1', ?, ?, '5525', 'fgfg@sez.cz'); ");
            insertCommand.setString(1, name);
            insertCommand.setString(2, surname);
            insertCommand.executeUpdate();

        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }finally {
            this.database.closeConnection();
        }
    }
}
