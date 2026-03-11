import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 드라이버 로딩
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 커넥션 얻기
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb",
                "myuser",
                "mypass"
        );

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM member");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Member user = new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getInt("age")
            );
            System.out.println(user);
        }

        // 커넥션 닫기
        connection.close();

    }
}

