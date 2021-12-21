package com.lao.UI;



import com.lao.Util.CalendarUtil;
import com.lao.Util.JDBCUtil;
import com.lao.Util.Log;
import com.lao.Util.RemindThread;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.*;


public class CalendarFrame extends JFrame implements ActionListener {
    JButton[] buttonDay = new JButton[42];
    JTextField text = new JTextField(10);
    JLabel[] titleName = new JLabel[7];
    JButton button = new JButton("确定");
    String[] name = {"    周日", "    周一", "    周二", "    周三", "    周四", "    周五", "    周六"};
    JButton nextMonth, previousMonth;
    int year = 2021, month = 12; //启动程序显示的日期信息
    CalendarUtil calendar;
    JLabel showMessage = new JLabel("", JLabel.CENTER);
    JLabel lbl1 = new JLabel("请输入年份：");
    JLabel lbl2 = new JLabel("   ");
    String[] day;
    int userId;


    public CalendarFrame(int id) {

        userId = id;
        setBackground(new Color(0, 0, 0));
        JPanel pCenter = new JPanel();
        pCenter.setBackground(new Color(255, 255, 255));

        //将pCenter的布局设置为7行7列的GridLayout 布局。
        pCenter.setLayout(new GridLayout(7, 7));

        //pCenter添加组件titleName[i]
        for (int i = 0; i < 7; i++) {
            titleName[i] = new JLabel(name[i]);
            pCenter.add(titleName[i]);
        }

        calendar = new CalendarUtil();
        calendar.setYear(year);
        calendar.setMonth(month);
        day = calendar.getCalendar();

        //pCenter添加组件labelDay[i]
        for (int i = 0; i < 42; i++) {

            buttonDay[i] = new JButton(day[i]);
            pCenter.add(buttonDay[i]);
            buttonDay[i].setContentAreaFilled(false);
            buttonDay[i].setBorderPainted(false);
            buttonDay[i].addActionListener(this);
        }

        text.addActionListener(this);


        nextMonth = new JButton("下月");
        previousMonth = new JButton("上月");

        //注册监听器
        nextMonth.addActionListener(this);
        previousMonth.addActionListener(this);
        button.addActionListener(this);

        JPanel pNorth = new JPanel(),
                pSouth = new JPanel();
        pNorth.add(showMessage);
        pNorth.add(lbl2);
        pNorth.add(previousMonth);
        pNorth.add(nextMonth);
        pSouth.add(lbl1);
        pSouth.add(text);
        pSouth.add(button);
        showMessage.setText("日历：" + calendar.getYear() + "年" + calendar.getMonth() + "月");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(pCenter);
        getContentPane().add(scrollPane, BorderLayout.CENTER);// 窗口添加scrollPane在中心区域
        getContentPane().add(pNorth, BorderLayout.NORTH);// 窗口添加pNorth 在北面区域
        getContentPane().add(pSouth, BorderLayout.SOUTH);// 窗口添加pSouth 在南区域。
        this.setBounds(100, 100, 360, 300);
        this.setTitle("日历小程序");
        this.setLocationRelativeTo(null);//窗体居中显示
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JDBCUtil jdbcUtil=new JDBCUtil();
                jdbcUtil.init();
                if(jdbcUtil.isOnLine()) {
                    new Log().updateLog();
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                RemindThread.remindThread(id);
                new Log().updateDatabase();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextMonth) {
            month = month + 1;
            if (month > 12)
                month = 1;
            calendar.setMonth(month);
            String[] day = calendar.getCalendar();

            for (int i = 0; i < 42; i++) {
                buttonDay[i].setText(day[i]);
            }
        } else if (e.getSource() == previousMonth) {
            month = month - 1;
            if (month < 1)
                month = 12;
            calendar.setMonth(month);
            String[] day = calendar.getCalendar();

            for (int i = 0; i < 42; i++) {
                buttonDay[i].setText(day[i]);
            }
        } else if (e.getSource() == button) {
            month = month + 1;
            if (month > 12) {
                month = 1;
            }
            calendar.setYear(Integer.parseInt(text.getText()));
            String[] day = calendar.getCalendar();
            for (int i = 0; i < 42; i++) {
                buttonDay[i].setText(day[i]);
            }
        } else {
            for (int i = 0; i < 42; i++) {
                if (e.getSource() == buttonDay[i]) {
                    Date date = new Date(calendar.getYear() - 1900, calendar.getMonth() - 1, Integer.parseInt(buttonDay[i].getText()));
                    new note(userId, date);

                }
            }
        }
        showMessage.setText("日历：" + calendar.getYear() + "年" + calendar.getMonth() + "月");
    }

    public static void main(String[] args) {
        new CalendarFrame(2);
    }
}

