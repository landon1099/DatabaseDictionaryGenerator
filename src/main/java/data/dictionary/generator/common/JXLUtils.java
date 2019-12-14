package data.dictionary.generator.common;

import jxl.write.*;

/**
 * @author mjh
 * @create 2019-12-10
 */
public class JXLUtils {

    //wcf:宋体、11、加粗、边框、居中、自定义表头颜色
    public WritableCellFormat getWcf(jxl.format.Colour colour) throws WriteException {
        // 字体样式：wf1:加粗，wf2:不加粗
        WritableFont wf1 = new WritableFont(WritableFont.createFont("宋体"), 11,WritableFont.BOLD); // 设置字体宋体,字号11,加粗显示
        WritableCellFormat wcf1 = new WritableCellFormat(wf1);
        wcf1.setWrap(true); // 开启自动换行
        wcf1.setBackground(colour);//设置表头颜色
        wcf1.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
        wcf1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直居中
        wcf1.setBorder(Border.ALL, BorderLineStyle.THIN); // 边框
        return wcf1;
    }

    //wcf0:宋体、11、加粗、边框、居中、灰色底色
    public WritableCellFormat getWcf0() throws WriteException {
        // 字体样式：wf1:加粗，wf2:不加粗
        WritableFont wf1 = new WritableFont(WritableFont.createFont("宋体"), 11,WritableFont.BOLD); // 设置字体宋体,字号11,加粗显示
        WritableCellFormat wcf1 = new WritableCellFormat(wf1);
        wcf1.setWrap(true); // 开启自动换行
        wcf1.setBackground(jxl.format.Colour.GRAY_25);//设置灰色表头
        wcf1.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
        wcf1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直居中
        wcf1.setBorder(Border.ALL, BorderLineStyle.THIN); // 边框
        return wcf1;
    }

    //wcf1:宋体、11、加粗、边框、居中
    public WritableCellFormat getWcf1() throws WriteException {
        // 字体样式：wf1:加粗，wf2:不加粗
        WritableFont wf1 = new WritableFont(WritableFont.createFont("宋体"), 11,WritableFont.BOLD); // 设置字体宋体,字号11,加粗显示
        WritableCellFormat wcf1 = new WritableCellFormat(wf1);
        wcf1.setWrap(true); // 开启自动换行
        wcf1.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
        wcf1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直居中
        wcf1.setBorder(Border.ALL, BorderLineStyle.THIN); // 边框
        return wcf1;
    }

    //wcf2:宋体、11、不加粗、边框、居中
    public WritableCellFormat getWcf2() throws WriteException {
        WritableFont wf2 = new WritableFont(WritableFont.createFont("宋体"), 11);//设置字体宋体,字号11
        WritableCellFormat wcf2 = new WritableCellFormat(wf2);
        //wcf2.setWrap(true); // 开启自动换行
        wcf2.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
        wcf2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直居中
        wcf2.setBorder(Border.ALL,BorderLineStyle.THIN); // 边框
        return wcf2;
    }

    //wcf3:宋体、11、不加粗、边框、不居中
    public WritableCellFormat getWcf3() throws WriteException {
        WritableFont wf3 = new WritableFont(WritableFont.createFont("宋体"), 11);//设置字体宋体,字号11
        WritableCellFormat wcf3 = new WritableCellFormat(wf3);
        //wcf3.setWrap(true); // 开启自动换行
        //wcf3.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
        wcf3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直居中
        wcf3.setBorder(Border.ALL,BorderLineStyle.THIN); // 边框
        return wcf3;
    }

    //wcf4:宋体、11、不加粗、无边框、居中
    public WritableCellFormat getWcf4() throws WriteException {
        WritableFont wf3 = new WritableFont(WritableFont.createFont("宋体"), 11);//设置字体宋体,字号11
        WritableCellFormat wcf3 = new WritableCellFormat(wf3);
        wcf3.setWrap(true); // 开启自动换行
        wcf3.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
        wcf3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直居中
        //wcf3.setBorder(Border.ALL,BorderLineStyle.THIN); // 边框
        return wcf3;
    }


    // WcfHL：宋体、11、蓝色、边框
    public WritableCellFormat getWcf4HyperLink() throws WriteException {
        WritableFont wf3 = new WritableFont(WritableFont.createFont("宋体"), 11);//设置字体宋体,字号11
        wf3.setColour(jxl.format.Colour.BLUE);//设置颜色
        WritableCellFormat wcf3 = new WritableCellFormat(wf3);
        wcf3.setWrap(true); // 开启自动换行
        //wcf3.setAlignment(jxl.format.Alignment.CENTRE); // 水平居中
        wcf3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直居中
        wcf3.setBorder(Border.ALL,BorderLineStyle.THIN); // 边框
        return wcf3;
    }

}
