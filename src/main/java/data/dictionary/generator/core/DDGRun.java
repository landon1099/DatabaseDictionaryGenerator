package data.dictionary.generator.core;

import data.dictionary.generator.DDG;
import data.dictionary.generator.common.CommonUtils;
import data.dictionary.generator.common.JXLUtils;
import data.dictionary.generator.core.db.DBConnection;
import data.dictionary.generator.common.InitMain;
import data.dictionary.generator.swing.DialogUtils;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author mjh
 * @create 2019-12-09
 */
public class DDGRun implements InitMain {

    public void init() {
        DDG.GEN_PATH = "";
    }

    public void run() throws WriteException {
        long start = System.currentTimeMillis();

        init();
        //list >> map(tableList、columnList、pKList) >> list >> map
        List<Map<String, List<Map<String, Object>>>> allInfos = DBConnection.getAllInfos(DDG.DRIVER, DDG.url, DDG.username, DDG.password);
        exportExcel(allInfos);

        long end = System.currentTimeMillis();
        System.out.println("----执行用时：" + (end - start));
    }

    //导出 excel
    public void exportExcel(List<Map<String, List<Map<String, Object>>>> list) throws WriteException {
        WritableWorkbook wwb = null;
        if (list != null && list.size() > 0) {
            try {
                wwb = Workbook.createWorkbook(new File(DDG.SRC_ROOT_PATH + DDG.username + ".xls"));
                //文本样式
                JXLUtils jxlUtils = new JXLUtils();
                WritableCellFormat wcf0 = jxlUtils.getWcf0();
                WritableCellFormat wcf1 = jxlUtils.getWcf1();
                WritableCellFormat wcf2 = jxlUtils.getWcf2();
                WritableCellFormat wcf3 = jxlUtils.getWcf3();

                WritableCellFormat wcf4HyperLink = jxlUtils.getWcf4HyperLink();
                //目录
                WritableSheet sheet0 = wwb.createSheet("目录", 0);//sheet名：目录，索引：0
                sheet0.mergeCells(2, 2, 6, 2);
                sheet0.addCell(new Label(2, 2, "目录", wcf1));
                sheet0.addCell(new Label(2, 3, "序号", wcf0));
                sheet0.addCell(new Label(3, 3, "表名", wcf0));
                sheet0.addCell(new Label(4, 3, "表注释", wcf0));
                sheet0.addCell(new Label(5, 3, "类型", wcf0));
                sheet0.addCell(new Label(6, 3, "备注", wcf0));
                //目录单元格长度
                sheet0.setRowView(2, 500);//行高比例： 1:20
                sheet0.setColumnView(2, 10);
                sheet0.setColumnView(3, 35);
                sheet0.setColumnView(4, 35);
                sheet0.setColumnView(5, 10);
                sheet0.setColumnView(6, 35);

                List<Map<String, Object>> tableList = null;
                List<Map<String, Object>> columnList = null;
                List<Map<String, Object>> pKList = null;
                int tableCount = 1;
                for (Map<String, List<Map<String, Object>>> stringListMap : list) {
                    tableList = stringListMap.get("tableList");
                    columnList = stringListMap.get("columnList");
                    pKList = stringListMap.get("pKList");

                    //目录页
                    String tableName = "";
                    String tableRemarks = "";
                    String tableType = "";
                    WritableSheet childSheet = null;
                    if (tableList != null && tableList.size() > 0) {
                        tableName = (String) tableList.get(0).get("tableName");
                        tableRemarks = (String) tableList.get(0).get("tableRemarks");
                        tableType = (String) tableList.get(0).get("tableType");
                        if (CommonUtils.isBlank(tableName)) {
                            tableName = UUID.randomUUID().toString().replaceAll("-", "");
                        }
                        childSheet = wwb.createSheet(tableName, tableCount);
                        sheet0.addCell(new Label(2, 3 + tableCount, tableCount + "", wcf2));
                        sheet0.addHyperlink(new WritableHyperlink(3, 3 + tableCount, "", childSheet, 0, 0));
                        sheet0.addCell(new Label(3, 3 + tableCount, tableName, wcf4HyperLink));
                        sheet0.addCell(new Label(4, 3 + tableCount, tableRemarks, wcf3));
                        sheet0.addCell(new Label(5, 3 + tableCount, tableType, wcf2));
                        sheet0.addCell(new Label(6, 3 + tableCount, "", wcf3));
                    }

                    //子表页 -- 字段表
                    int columnCount = 1;
                    if (columnList != null && columnList.size() > 0 && childSheet != null) {
                        childSheet.addHyperlink(new WritableHyperlink(0, 0, "", sheet0, 0, 0));
                        childSheet.addCell(new Label(0, 0, "返回", wcf4HyperLink));
                        childSheet.addCell(new Label(1, 2, "表名：", jxlUtils.getWcf(Colour.LIGHT_GREEN)));
                        childSheet.addCell(new Label(2, 2, tableName, wcf1));
                        childSheet.addCell(new Label(1, 3, "注释：", jxlUtils.getWcf(Colour.LIGHT_GREEN)));
                        childSheet.addCell(new Label(2, 3, tableRemarks, wcf1));
                        childSheet.addCell(new Label(1, 5, "序号", wcf0));
                        childSheet.addCell(new Label(2, 5, "名称", wcf0));
                        childSheet.addCell(new Label(3, 5, "类型", wcf0));
                        childSheet.addCell(new Label(4, 5, "是否可为空", wcf0));
                        childSheet.addCell(new Label(5, 5, "默认/表达式", wcf0));
                        childSheet.addCell(new Label(6, 5, "注释", wcf0));

                        for (Map<String, Object> map : columnList) {
                            String columnName = (String) map.get("columnName");
                            String typeName = (String) map.get("typeName");
                            String isNullAble = (String) map.get("isNullAble");
                            String columnDef = (String) map.get("columnDef");
                            String remarks = (String) map.get("remarks");
                            Integer columnSize = (Integer) map.get("columnSize");//列大小
                            childSheet.addCell(new Label(1, 5 + columnCount, columnCount + "", wcf2));
                            childSheet.addCell(new Label(2, 5 + columnCount, columnName, wcf3));
                            childSheet.addCell(new Label(3, 5 + columnCount,
                                    getTypeName(typeName, columnSize), wcf3));
                            childSheet.addCell(new Label(4, 5 + columnCount, isNullAble, wcf2));
                            childSheet.addCell(new Label(5, 5 + columnCount, columnDef, wcf2));
                            childSheet.addCell(new Label(6, 5 + columnCount, remarks, wcf3));
                            columnCount++;
                        }
                    }

                    //子表页 -- 主键表
                    int PKCount = 1;
                    if (pKList != null && pKList.size() > 0 && childSheet != null) {
                        childSheet.addCell(new Label(8, 5, "PK_NAME", wcf0));
                        childSheet.addCell(new Label(9, 5, "COLUMN_NAME", wcf0));
                        childSheet.addCell(new Label(10, 5, "KEY_SEQ", wcf0));
                        for (Map<String, Object> map : pKList) {
                            //String PKTABLE_CAT = (String) map.get("PKTABLE_CAT");
                            //String PKTABLE_SCHEM = (String) map.get("PKTABLE_SCHEM");
                            //String PKTABLE_NAME = (String) map.get("PKTABLE_NAME");
                            String COLUMN_NAME = (String) map.get("COLUMN_NAME");
                            BigDecimal KEY_SEQ = (BigDecimal) map.get("KEY_SEQ");
                            String PK_NAME = (String) map.get("PK_NAME");
                            //childSheet.addCell(new Label(9, 5 + PKCount, PKTABLE_CAT, wcf2));
                            //childSheet.addCell(new Label(10, 5 + PKCount, PKTABLE_SCHEM, wcf2));
                            //childSheet.addCell(new Label(9, 5 + PKCount, PKTABLE_NAME, wcf2));
                            childSheet.addCell(new Label(8, 5 + PKCount, PK_NAME, wcf3));
                            childSheet.addCell(new Label(9, 5 + PKCount, COLUMN_NAME, wcf3));
                            childSheet.addCell(new Label(10, 5 + PKCount, KEY_SEQ + "", wcf2));
                            PKCount++;
                        }
                    }

                    //子表单元格长度
                    childSheet.setColumnView(1, 10);
                    childSheet.setColumnView(2, 30);
                    childSheet.setColumnView(3, 20);
                    childSheet.setColumnView(4, 16);
                    childSheet.setColumnView(5, 16);
                    childSheet.setColumnView(6, 25);
                    childSheet.setColumnView(8, 18);
                    childSheet.setColumnView(9, 18);
                    childSheet.setColumnView(10, 18);

                    tableCount++;
                }
                wwb.write();
                DDG.GEN_PATH = DDG.SRC_ROOT_PATH + DDG.username + ".xls";
            } catch (IOException e) {
                DialogUtils.msg(e.toString());
                e.printStackTrace();
            } finally {
                try {
                    if (wwb != null) {
                        wwb.close();
                    }
                } catch (IOException e) {
                    DialogUtils.msg(e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    //获取字段类型+长度
    public String getTypeName(String typeName, Integer columnSize) {
        if (!"DATE".equals(typeName)
                && !"BLOB".equals(typeName)
                && !"CLOB".equals(typeName)
                && !"TIMESTAMP".equals(typeName)) {
            typeName += "(" + columnSize + ")";
        }
        return typeName;
    }

}
