package data.dictionary.generator.swing;

import data.dictionary.generator.DDG;
import data.dictionary.generator.bean.DbInfo;
import data.dictionary.generator.common.CommonUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author mjh
 * @create 2019-12-13
 */
public class ProgressBar extends JFrame {

    public ProgressBar() {
        this.setTitle("导出" + DDG.username);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(550, 260, 250, 100);
        final JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(14, 5, 5, 5));
        this.setContentPane(jPanel);
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        //进度条
        final JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        jPanel.add(progressBar);

        //打开按钮
        final JButton openBtn = new JButton("打开");
        openBtn.addActionListener(new ActionListener() {//添加事件
            public void actionPerformed(ActionEvent e) {
                if (CommonUtils.isBlank(DDG.GEN_PATH)) {
                    DialogUtils.msg("文件不存在！");
                    return;
                }
                File file = new File(DDG.GEN_PATH);
                if (file.exists()) {
                    try {
                        Runtime.getRuntime().exec(
                                "rundll32 SHELL32.DLL,ShellExec_RunDLL "
                                        + "Explorer.exe /select," + file.getAbsolutePath());
                    } catch (IOException e2) {
                        DialogUtils.msg(e2.toString());
                        e2.printStackTrace();
                    }
                } else {
                    DialogUtils.msg("文件不存在！");
                    return;
                }
            }
        });
        openBtn.setVisible(false);
        jPanel.add(openBtn);

        //定时进度条任务
        new Thread() {
            public void run() {
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //任务结束
                        if (DDG.totalTask != 0 && DDG.totalTask == DDG.taskIndex) {
                            progressBar.setString("导出完成！");
                            openBtn.setVisible(true);
                            timer.cancel();
                        }
                        if (DDG.totalTask != 0) {
                            double startNum = Double.parseDouble(DDG.taskIndex + "") / Double.parseDouble(DDG.totalTask + "") * 100;
                            progressBar.setValue((int) startNum);
                        }
                    }
                }, 0, 1000);
            }
        }.start();

        this.setVisible(true);
    }

}
