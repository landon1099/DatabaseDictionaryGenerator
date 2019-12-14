package data.dictionary.generator;

import data.dictionary.generator.core.DDGRun;
import data.dictionary.generator.swing.DialogUtils;
import jxl.write.WriteException;

/**
 * @author mjh
 * @create 2019-12-09
 */
public class DDG {

    // src 路径下
    public static String SRC_ROOT_PATH = System.getProperty("user.dir") + "/" /*+ "/src/"*/;
    public static String GEN_PATH = "";
    // 数据库连接
    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static String url = "";
    public static String username = "";
    public static String password = "";
    public static int totalTask = 0;  //总任务数
    public static int taskIndex = 0;  //当前任务数

    // 执行类（init、run）
    public static DDGRun ddgRun = new DDGRun();

    public static void run() {
        try {
            ddgRun.run();
        } catch (WriteException e) {
            DialogUtils.msg(e.toString());
            e.printStackTrace();
        }
    }

}
