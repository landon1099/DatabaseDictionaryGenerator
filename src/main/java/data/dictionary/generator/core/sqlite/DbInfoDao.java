package data.dictionary.generator.core.sqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import data.dictionary.generator.bean.DbInfo;
import data.dictionary.generator.swing.DialogUtils;

import java.awt.*;
import java.sql.*;

/**
 * @author mjh
 * @create 2019-12-12
 */
public class DbInfoDao {

    private static String DATA_BASE_URL = "jdbc:sqlite:ddg.db";
    private static ConnectionSource connectionSource = null;
    private static Dao<DbInfo, String> dbInfoDao = null;

    //启动校验
    public void runCheckTable() {
        boolean tableExist = this.isTableExist();
        if (tableExist == false) {
            try {
                initDbInfoDao();
                TableUtils.createTable(connectionSource, DbInfo.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //初始化Dao
    public void initDbInfoDao() {
        try {
            connectionSource = new JdbcConnectionSource(DATA_BASE_URL);
            dbInfoDao = DaoManager.createDao(connectionSource, DbInfo.class);
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
    }

    //获取Dao（不重新连接）
    public void getDbInfoDao() {
        try {
            dbInfoDao = DaoManager.createDao(connectionSource, DbInfo.class);
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 初次创建表
     */
    /*public void createTable() {
        initDbInfoDao();
        try {
            TableUtils.createTable(connectionSource, DbInfo.class);
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
    }*/

    /**
     * 查询
     * @return
     */
    public String[][] selectDbInfoList() {

        initDbInfoDao();

        String[][] result = null;
        //查询总数
        int size = 0;
        try {
            size = (int) dbInfoDao.countOf();
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
        if (size == 0) {
            return null;
        }
        result = new String[size][4];
        int index = 0;
        for (DbInfo dbInfo : dbInfoDao) {
            int id = dbInfo.getId();
            String url = dbInfo.getUrl();
            String username = dbInfo.getUsername();
            String password = dbInfo.getPassword();
            result[index][0] = id+"";
            result[index][1] = url==null ? "":url;
            result[index][2] = username==null ? "":username;
            result[index][3] = password==null ? "":password;
            index++;
        }
        return result;
    }

    //插入
    public void insertDbInfo(DbInfo dbInfo) {

        initDbInfoDao();

        try {
            dbInfoDao.create(dbInfo);
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
    }

    //更新
    public void updateDbinfo(DbInfo dbInfo) {
        initDbInfoDao();

        try {
            dbInfoDao.update(dbInfo);
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
    }

    //删除
    public void deleteDbinfo(DbInfo dbInfo) {
        initDbInfoDao();

        try {
            dbInfoDao.delete(dbInfo);
        } catch (SQLException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
    }

    //判断表是否存在
    public boolean isTableExist() {
        Connection c = null;
        Statement stmt = null;
        int count = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(DATA_BASE_URL);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='dbinfo'");
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
        return count==0 ? false : true;
    }

}
