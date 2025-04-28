import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//test basique pour la connexion à la bdd
public class DatabaseTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/pvz?useSSL=false&serverTimezone=UTC";
        String username = "epf";
        String password = "mot_de_passe";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Connexion à la base de données réussie !");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}
