package data.dictionary.generator.core.db;

import data.dictionary.generator.DDG;
import data.dictionary.generator.swing.DialogUtils;

import java.sql.*;
import java.util.*;

/**
 * @author mjh
 * @create 2019-08-21
 */
public class DBConnection {

    //获取连接
    private static Connection getConnections(String driver, String url, String user, String pwd) throws Exception {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", user);
            props.put("password", pwd);
            Class.forName(driver);
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    //获取表名（视图名）、注释、表结构、主键信息
    public static List<Map<String, List<Map<String, Object>>>> getAllInfos(String driver, String url, String user, String pwd) {
        List result = new ArrayList();
        List tableList = null;
        List columnList = null;
        List pKList = null;
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        Map<String, Object> map = null;
        Map<String, List> resultMap = null;
        try {
            conn = getConnections(driver, url, user, pwd);
            dbmd = conn.getMetaData();

            //获取总任务数
            ResultSet rsTask = dbmd.getTables(null, user.toUpperCase(), null, new String[]{"TABLE", "VIEW"});
            int tmpCout = 0;
            while (rsTask.next()) {
                String tableName = rsTask.getString("TABLE_NAME");//表名
                //忽略oracle开启了flashback（闪回），删除的表
                if (tableName.startsWith("BIN$")) {
                    continue;
                }
                tmpCout++;
            }
            DDG.totalTask = tmpCout;

            //1.获取表信息
            ResultSet rs = dbmd.getTables(null, user.toUpperCase(), null, new String[]{"TABLE", "VIEW"});
            while (rs.next()) {

                resultMap = new HashMap<String, List>();
                tableList = new ArrayList();
                columnList = new ArrayList();
                map = new HashMap<String, Object>();
                String tableName = rs.getString("TABLE_NAME");//表名
                //忽略oracle开启了flashback（闪回），删除的表
                if (tableName.startsWith("BIN$")) {
                    continue;
                }
                String tableType = rs.getString("TABLE_TYPE");//表类型
                String tableRemarks = rs.getString("REMARKS");//表备注
                map.put("tableName", tableName);//表名
                map.put("tableType", tableType);//表类型
                map.put("tableRemarks", tableRemarks);//表注释
                tableList.add(map);
                resultMap.put("tableList", tableList);
                System.out.println("=========执行表：" + tableName + ",表类型：" + tableType + ",表备注：" + tableRemarks);
                //2.1获取表结构
                ResultSet columnsRS = dbmd.getColumns(null, user.toUpperCase(), tableName, null);
                while (columnsRS.next()) {
                    map = new HashMap();
                    String columnName = columnsRS.getString("COLUMN_NAME");//列名
                    String remarks = columnsRS.getString("REMARKS");//列注释
                    String typeName = columnsRS.getString("TYPE_NAME");//字段类型名称(例如：VACHAR2)
                    String dataType = columnsRS.getString("DATA_TYPE");//字段数据类型(对应java.sql.Types中的常量)
                    int columnSize = columnsRS.getInt("COLUMN_SIZE");  //列大小
                    //int decimalDigits = columnsRS.getInt("DECIMAL_DIGITS");  //小数位数
                    String columnDef = columnsRS.getString("COLUMN_DEF");  //默认值
                    /**
                     *  0 (columnNoNulls) - 该列不允许为空
                     *  1 (columnNullable) - 该列允许为空
                     *  2 (columnNullableUnknown) - 不确定该列是否为空
                     */
//                int nullAble = columnsRS.getInt("NULLABLE");  //是否允许为null
                    /**
                     * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
                     * YES -- 该列可以有空值;
                     * NO -- 该列不能为空;
                     * 空字符串--- 不知道该列是否可为空
                     */
                    String isNullAble = columnsRS.getString("IS_NULLABLE");//是否为空
                    //String tableSchemaName = rs.getString("TABLE_SCHEM");//表模式（可能为空）,在oracle中获取的是命名空间
                    map.put("remarks", remarks);//列注释
                    map.put("columnName", columnName.toLowerCase());//列名（小写）
                    map.put("typeName", typeName);//字段类型名称(例如：VACHAR2)
                    map.put("dataType", dataType);//字段数据类型(对应java.sql.Types中的常量)
                    map.put("columnSize", columnSize);//列大小
                    map.put("columnDef", columnDef);//默认值
                    map.put("isNullAble", isNullAble);//是否为空：yes/no

                    columnList.add(map);
                }
                resultMap.put("columnList", columnList);
                //2.2获取表中主键
                pKList = new ArrayList();
                ResultSet primaryKeysRS = dbmd.getPrimaryKeys(null, user.toUpperCase(), tableName);
                while (primaryKeysRS.next()) {
                    map = new HashMap<String, Object>();
                    map.put("PKTABLE_CAT", primaryKeysRS.getObject(1));
                    map.put("PKTABLE_SCHEM", primaryKeysRS.getObject(2));
                    map.put("PKTABLE_NAME", primaryKeysRS.getObject(3));
                    map.put("COLUMN_NAME", primaryKeysRS.getObject(4));
                    map.put("KEY_SEQ", primaryKeysRS.getObject(5));
                    map.put("PK_NAME", primaryKeysRS.getObject(6));
                    pKList.add(map);
                }
                resultMap.put("pKList", pKList);
                result.add(resultMap);

                DDG.taskIndex++;
            }
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                DialogUtils.msg(e.toString());
                e.printStackTrace();
            }
        }
        return result;
    }

}
