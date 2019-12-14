package data.dictionary.generator.bean;

import java.util.List;

/**
 * @author mjh
 * @create 2019-08-22
 */
public class Context {

    private DbBean dbBean;//jdbcConnection 数据库连接

    private String dbConnectionType;//jdbcConnectionType 数据库连接方式（默认oracle）

    private String xmlLocation;//xml 生成地址

    private String serviceLocation;//service 生成地址

    private String dbLocation;//db 生成地址

    private String dtoLocation;//dto 生成地址

    private List<String> table;//表名

    public DbBean getDbBean() {
        return dbBean;
    }

    public void setDbBean(DbBean dbBean) {
        this.dbBean = dbBean;
    }

    public String getDbConnectionType() {
        return dbConnectionType;
    }

    public void setDbConnectionType(String dbConnectionType) {
        this.dbConnectionType = dbConnectionType;
    }

    public String getXmlLocation() {
        return xmlLocation;
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public String getDbLocation() {
        return dbLocation;
    }

    public void setDbLocation(String dbLocation) {
        this.dbLocation = dbLocation;
    }

    public String getDtoLocation() {
        return dtoLocation;
    }

    public void setDtoLocation(String dtoLocation) {
        this.dtoLocation = dtoLocation;
    }

    public List<String> getTable() {
        return table;
    }

    public void setTable(List<String> table) {
        this.table = table;
    }

}
