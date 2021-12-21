package com.lao.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.lao.Model.Diary;
import com.lao.Util.JDBCUtil;
import com.lao.Util.RemindThread;

public class note extends JFrame implements ActionListener {
    JPanel jp1, jp2, jp3, jp4, jp5;

    JTextArea jt1;
    JButton jb1;
    JLabel jl1, jl2, jl3, jl4,jl5;
    JTextField jt2, jt3, jt4, jt5;
    JScrollPane jsp;
    Date date;
    Boolean haveDiary;
    int userId,diaryId;
    public note(int id, Date date) {
        this.date=date;
        userId=id;
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp5 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jt1 = new JTextArea(7,18);
        jb1 = new JButton("确认");
        jl5=new JLabel("标题");
        jt2 = new JTextField(16);
        jt3 = new JTextField(4);
        jt4 = new JTextField(4);
        jt5 = new JTextField(4);
        jsp=new JScrollPane(jt1);
        jb1.addActionListener(this);
        jt1.setLineWrap(true);
        jt1.setBackground(Color.WHITE);
        Dimension size=jt1.getPreferredSize();
        jsp.setBounds(200,300,size.width,size.height);
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init();
        Diary diary = jdbcUtil.getTheDiary(id, new java.sql.Date(date.getTime()));
        jdbcUtil.close();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=format.format(date);

        jl1 = new JLabel(dateStr);
        jl2 = new JLabel("时");
        jl3 = new JLabel("分");
        jl4 = new JLabel("秒");
        if (diary != null) {
            jt2.setText(diary.getTitle());
            jt1.append(diary.getContent());

            jt3.setText(Integer.toString(diary.getDateTime().getHours()));
            jt4.setText(Integer.toString(diary.getDateTime().getMinutes()));
            jt5.setText(Integer.toString(diary.getDateTime().getSeconds()));
            diaryId=diary.getDiaryId();
            haveDiary=true;
        }else{

            haveDiary=false;
        }
        jp1.add(jl1);
        jp2.add(jl5);
        jp2.add(jt2);
        jp3.add(jsp);
        jp4.add(jt3);
        jp4.add(jl2);
        jp4.add(jt4);
        jp4.add(jl3);
        jp4.add(jt5);
        jp4.add(jl4);
        jp5.add(jb1);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
        //锁定窗体

        this.setResizable(false);

        this.setLayout(new GridLayout(5, 1));
        //给窗口设置标题
        this.setTitle("日程安排");
        //设置窗体大小
        this.setSize(400, 400);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Diary diary1=new Diary();
        diary1.setUserId(userId);
        if(diaryId!=-1) diary1.setDiaryId(diaryId);
        diary1.setTitle(jt2.getText());
        diary1.setContent(jt1.getText());
        int h=Integer.parseInt(jt3.getText());
        int m=Integer.parseInt(jt4.getText());
        int s=Integer.parseInt(jt5.getText());
        date.setHours(h);
        date.setMinutes(m);
        date.setSeconds(s);
        diary1.setDateTime(date);
        if(e.getSource()==jb1) {
            JDBCUtil jdbcUtil=new JDBCUtil();
            jdbcUtil.init();
            if(haveDiary){
                jdbcUtil.updateTheDiary(diary1);
            }else{
                jdbcUtil.insertTheDiary(diary1);
            }
            jdbcUtil.close();
            RemindThread.clearTimer();
            RemindThread.remindThread(userId);
            this.dispose();
        }

    }

}
