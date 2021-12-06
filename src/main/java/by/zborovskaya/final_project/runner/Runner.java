package by.zborovskaya.final_project.runner;

import java.util.Locale;
import java.util.ResourceBundle;

public class Runner {
//    public static void main(String[] args){
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
////                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        String url = "jdbc:mysql://localhost:3306/advertisements_db";
//        Properties prop = new Properties();
//        prop.put("user", "adv_user");
//        prop.put("password", "12345");
//        prop.put("autoReconnect", "true");
//        prop.put("characterEncoding", "UTF-8");
//        prop.put("useUnicode", "true");
//        prop.put("useSSL", "true");
//        prop.put("useJDBCCompliantTimezoneShift", "true");
//        prop.put("useLegacyDatetimeCode", "false");
//        prop.put("serverTimezone", "UTC");
//        prop.put("serverSslCert", "classpath:server.crt");
//        List<Client> clients = new ArrayList<>();
//        try (Connection connection = DriverManager.getConnection(url, prop);
//             Statement statement = connection.createStatement()) {
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT user_id,email, first_name,last_name,phone,city_id FROM clients");
//            while (resultSet.next()) {
//                int id = resultSet.getInt("user_id");
//                String email = resultSet.getString("email");
//                String first_name = resultSet.getString("first_name");
//                String last_name = resultSet.getString("last_name");
//                int number = resultSet.getInt("phone");
//                int cityId = resultSet.getInt("city_id");
//                clients.add(new Client(id,email,first_name, last_name, number,cityId));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        for(Client client:clients)
//        System.out.println(client.toString());
//    }
public static void main(String[] args) {
    Locale locale = new Locale("en");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localisation/local", locale);
    System.out.println(resourceBundle.getString("local.locbutton.name.en"));
}
}
