import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

public class TableUtil {
  public static String createInsertStatementSql
    (Class<?> zclass, String tableName){
    StringBuilder fields= new StringBuilder();
    StringBuilder vars= new StringBuilder();

    for(Field field : zclass.getDeclaredFields())
    {
      String name=field.getName();
      if(fields.length()>1)
      {
        fields.append(",");
        vars.append(",");
      }
      fields.append(name);
      vars.append("?");
    }

    String Sql="INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES(" + vars.toString() + ")";

    return Sql;
  }

  public static PreparedStatement createInsertPreParedStatement
    (Connection conn, Object object, String tableName){
    PreparedStatement stmt = null;

    try {
      Class<?> zclass = object.getClass();
      String Sql = createInsertStatementSql(zclass, tableName);

      stmt = (PreparedStatement) conn.prepareStatement(Sql);

      Field[] fields = zclass.getDeclaredFields();

      for(int i = 0; i < fields.length; i++){
        Field field = fields[i];
        field.setAccessible(true);

        Object value = field.get(object);
        stmt.setObject((i+1), value);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return stmt;

  }

  public static String createSelectStatementSql
    (Class<?> zclass, String tableName){
    StringBuilder fields= new StringBuilder();

    for(Field field: zclass.getDeclaredFields()){
      String name=field.getName();
      if(fields.length()>1)
      {
        fields.append(",");
      }
      fields.append(name);
    }

    String Sql = "SELECT " + fields.toString() + " FROM " + tableName;
    System.out.println(Sql);
    return Sql;
  }

  public static PreparedStatement createSelectPreParedStatement(
    Connection conn, List<Object> objects, String tableName){
    PreparedStatement stmt = null;

    try {
      Class<?> zclass = objects.getClass();
      String Sql = createInsertStatementSql(zclass, tableName);

      stmt = (PreparedStatement) conn.prepareStatement(Sql);
      Field[] fields = zclass.getDeclaredFields();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return stmt;
  }

}
