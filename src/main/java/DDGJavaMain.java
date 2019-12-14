import data.dictionary.generator.DDG;
import jxl.write.WriteException;

/**
 * @author mjh
 * @create 2019-12-09
 */
public class DDGJavaMain {

    public static void main(String[] args) throws WriteException {

        dcsapp();
        DDG.run();

    }

    public static void dcsapp() {
        //数据库配置
        DDG.url = "jdbc:oracle:thin:@localhost:1521:dcsorcl";
        DDG.username = "scott";
        DDG.password = "123";
    }

}
