package data.dictionary.generator.swing;

import javax.swing.*;

/**
 * @author mjh
 * @create 2019-12-13
 */
public class DialogUtils {

    /**
     * 警告提示框
     * @param msg
     */
    public static void msg(String msg) {
        if (JOptionPane.OK_OPTION==JOptionPane
                .showOptionDialog(null, msg, "提示", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"确认"}, null)){
            //System.out.println("ok");
        } else {
            //System.out.println("sss");
        }
    }

}
