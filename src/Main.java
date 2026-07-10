import database.DBConnection;
import ui.Home;
public class Main {
    public static void main(String[] args) {
        DBConnection.getConnection();
        new Home();
    }
}