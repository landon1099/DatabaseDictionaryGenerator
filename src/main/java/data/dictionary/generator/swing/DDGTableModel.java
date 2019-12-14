package data.dictionary.generator.swing;

import javax.swing.table.DefaultTableModel;

/**
 * @author mjh
 * @create 2019-12-12
 */
public class DDGTableModel extends DefaultTableModel {

    /*public DDGTableModel() {}

    public DDGTableModel(String[][] tableVales, String[] columnNames) {
        super(tableVales, columnNames);
    }*/

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
