package cz.osu.carservice.models.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    //database URL and driver path
    private static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL="jdbc:mysql://localhost/carservice_7swi1";

    //login information
    private static final String USERNAME="root";
    private static final String PASSWORD="";

    private Connection connection;
    public Connection getConnection() {
        return connection;
    }

    public Database() {
        this.connection = null;
    }

    public void openConnection() {
        try{
            //Driver registration
            Class.forName(JDBC_DRIVER);

            //Open connection
            this.connection = DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD);
        }catch (Exception e){
            System.err.println("Chyba při komunikaci s databází!");
            System.err.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if(connection != null) {
                this.connection.close();
                this.connection = null;
            }
        }catch (Exception e){
            System.err.println("Chyba při komunikaci s databází!");
            System.err.println(e.getMessage());
        }
    }
}
