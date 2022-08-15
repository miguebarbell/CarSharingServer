package carsharing;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnector db = new DatabaseConnector(args);
        Menu menu = new Menu(db);
        menu.mainMenu();
    }
}
