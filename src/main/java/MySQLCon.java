import java.sql.*;

public class MySQLCon {
  public static void main(String[] args){
    try {
      // connect local database
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/reflextion_test",
        "nmd",
        "123123"
      );

      //execute Insert command
      Person person = new Person(2, "B");
      PreparedStatement stmt = TableUtil.createInsertPreParedStatement((com.mysql.jdbc.Connection) con, person,"person" );
      stmt.execute();
      con.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  //    Class<?> zclass= Person.class;
  //    String table="person";
  //    String selectSql = TableUtil.createSelectStatementSql(zclass, table);
  }
}
