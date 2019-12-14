package data.dictionary.generator.swing;

import data.dictionary.generator.DDG;
import data.dictionary.generator.bean.DbInfo;
import data.dictionary.generator.common.CommonUtils;
import data.dictionary.generator.core.sqlite.DbInfoDao;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author mjh
 * @create 2019-12-11
 */
public class DDGJFrame extends JFrame {

    private DefaultTableModel tableModel;   //表格模型对象
    private JTable jTable;
    private JTextField urlText;
    private JTextField userNmText;
    private JTextField passwordText;
    private DbInfoDao dbInfoDao;

    public DDGJFrame() {
        super();
        dbInfoDao = new DbInfoDao();
        dbInfoDao.runCheckTable();

        setTitle("DatabaseDictionaryGenerator");
        setBounds(300, 100, 700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final String[] columnNames = {"ID", "URL", "用户名", "密码"};   //列名

        final String[][] tableVales = dbInfoDao.selectDbInfoList();
        tableModel = new DDGTableModel();
        tableModel.setDataVector(tableVales, columnNames);
        jTable = new JTable(tableModel);
        //设置标题
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 30));
        tableHeader.setFont(new Font(null, Font.BOLD, 12));
        //设置表格内容居中
        DefaultTableCellRenderer dtr = new DefaultTableCellRenderer();
        dtr.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, dtr);
        jTable.setRowHeight(28);  //设置行高
        JScrollPane scrollPane = new JScrollPane(jTable);   //支持滚动
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
        //设置id列隐藏
        TableColumn idColumn = jTable.getColumnModel().getColumn(0);
        idColumn.setWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setMinWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0); //设置表的标题的宽度也为0,这个很重要
        jTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        //设置url列长度
        TableColumn urlColumn = jTable.getColumnModel().getColumn(1);
        urlColumn.setWidth(350);
        urlColumn.setMaxWidth(350);
        urlColumn.setMinWidth(350);
        jTable.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(350); //设置表的标题的宽度也为0,这个很重要
        jTable.getTableHeader().getColumnModel().getColumn(1).setMinWidth(350);

        //鼠标事件
        jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = jTable.getSelectedRow(); //获得选中行索引
                Object idObj = tableModel.getValueAt(selectedRow, 0);
                Object urlObj = tableModel.getValueAt(selectedRow, 1);
                Object userNmObj = tableModel.getValueAt(selectedRow, 2);
                Object passwordObj = tableModel.getValueAt(selectedRow, 3);
                urlText.setText(urlObj.toString());  //给文本框赋值
                userNmText.setText(userNmObj.toString());
                passwordText.setText(passwordObj.toString());
            }
        });
        scrollPane.setViewportView(jTable);
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 80));
        getContentPane().add(panel, BorderLayout.SOUTH);
        panel.add(new JLabel("URL: "));
        urlText = new JTextField("", 55);
        panel.add(urlText);
        panel.add(new JLabel("用户名: "));
        userNmText = new JTextField("", 10);
        panel.add(userNmText);
        panel.add(new JLabel("密码: "));
        passwordText = new JPasswordField("", 10);
        panel.add(passwordText);

        //添加按钮
        final JButton addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {//添加事件
            public void actionPerformed(ActionEvent e) {
                //String[] rowValues = {"", urlText.getText(), userNmText.getText(), passwordText.getText()};
                dbInfoDao.insertDbInfo(new DbInfo(urlText.getText(), userNmText.getText(), passwordText.getText()));
                //tableModel.addRow(rowValues);  //添加一行
                //刷新列表数据，设置id隐藏
                String[][] tableVales = dbInfoDao.selectDbInfoList();
                tableModel.setDataVector(tableVales, columnNames);
                //设置id列隐藏
                TableColumn idColumn = jTable.getColumnModel().getColumn(0);
                idColumn.setWidth(0);
                idColumn.setMaxWidth(0);
                idColumn.setMinWidth(0);
                jTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0); //设置表的标题的宽度也为0,这个很重要
                jTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
                //设置url列长度
                TableColumn urlColumn = jTable.getColumnModel().getColumn(1);
                urlColumn.setWidth(350);
                urlColumn.setMaxWidth(350);
                urlColumn.setMinWidth(350);
                jTable.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(350); //设置表的标题的宽度也为0,这个很重要
                jTable.getTableHeader().getColumnModel().getColumn(1).setMinWidth(350);
            }
        });
        panel.add(addButton);

        //修改按钮
        final JButton updateButton = new JButton("修改");
        updateButton.addActionListener(new ActionListener() {//添加事件
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable.getSelectedRow();//获得选中行的索引
                if (selectedRow != -1)   //是否存在选中行
                {
                    Object idObj = tableModel.getValueAt(selectedRow, 0);

                    DbInfo dbInfo = new DbInfo(urlText.getText(), userNmText.getText(), passwordText.getText());
                    dbInfo.setId(Integer.parseInt(idObj.toString()));
                    dbInfoDao.updateDbinfo(dbInfo);
                    //修改指定的值：
                    tableModel.setValueAt(urlText.getText(), selectedRow, 1);
                    tableModel.setValueAt(userNmText.getText(), selectedRow, 2);
                    tableModel.setValueAt(passwordText.getText(), selectedRow, 3);

                }
            }
        });
        panel.add(updateButton);

        //删除按钮
        final JButton delButton = new JButton("删除");
        delButton.addActionListener(new ActionListener() {//添加事件
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable.getSelectedRow();//获得选中行的索引
                if (selectedRow != -1)  //存在选中行
                {
                    String idObj = (String) jTable.getValueAt(selectedRow, 0);
                    DbInfo dbInfo = new DbInfo();
                    dbInfo.setId(Integer.parseInt(idObj.toString()));
                    dbInfoDao.deleteDbinfo(dbInfo);
                    tableModel.removeRow(selectedRow);  //删除行
                }
            }
        });
        panel.add(delButton);

        //下一步按钮
        final JButton nextButton = new JButton("下一步");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = urlText.getText();
                String userNm = userNmText.getText();
                String password = passwordText.getText();
                if (CommonUtils.isNotBlank(url) && CommonUtils.isNotBlank(userNm) && CommonUtils.isNotBlank(password)) {
                    //TODO 列表展示页面
                    DDG.url = url;
                    DDG.username = userNm;
                    DDG.password = password;

                    Thread genThread = new Thread(new Runnable() {
                        public void run() {
                            DDG.totalTask = 0;
                            DDG.taskIndex = 0;
                            ProgressBar progressBar = new ProgressBar();
                            DDG.run();
                        }
                    });
                    genThread.start();

                } else {
                    DialogUtils.msg("数据库信息不能为空！");
                }
            }
        });
        panel.add(nextButton);
    }
}
