
package bd.azure;

public class BasicDataSource {

    BasicDataSource dataSource = new BasicDataSource();

    public BasicDataSource() {
        dataSource.setDriverClassName(
                "com.microsoft.sqlserver.jdbc.SQLServerDriver");
// exemplo para MySql: "com.mysql.cj.jdbc.Driver"
        dataSource.setUrl(
                "jdbc:sqlserver://meubanco.database.windows.net/meubanco");
// exemplo para MySql: "jdbc:mysql://localhost:3306/meubanco"
        dataSource.setUsername(
                "admin-projeto-monitority");
        dataSource.setPassword(
                "#Gfgrupo09");
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }


}
