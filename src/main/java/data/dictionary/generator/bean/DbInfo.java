package data.dictionary.generator.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author mjh
 * @create 2019-12-11
 */
@DatabaseTable(tableName = "dbinfo")
public class DbInfo {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "url")
    private String url;

    @DatabaseField(columnName = "username")
    private String username;

    @DatabaseField(columnName = "password")
    private String password;

    public DbInfo(){}

    public DbInfo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
