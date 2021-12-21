package com.lao.UI;

import com.lao.Model.Diary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Remind extends JFrame implements ActionListener {
    JLabel jl1;
    JLabel jl2;
    JTextField jt1;
    JTextArea jt2;
    JButton jb1;

    public Remind(Diary diary) {

        jl1 = new JLabel("标题");
        jl2 = new JLabel("日程");
        jt1 = new JTextField(diary.getTitle());
        jt2 = new JTextArea(diary.getContent());
        jb1 = new JButton("确认");
        jb1.addActionListener(this);
        jl1.setBounds(23, 35, 80, 25);
        jt1.setBounds(60, 35, 180, 25);
        jt1.setEnabled(false);
        jl2.setBounds(23, 65, 80, 25);
        jt2.setBounds(60, 65, 200, 150);
        jt2.setEnabled(false);
        jb1.setBounds(80, 230, 120, 25);
        this.setLayout(null);
        this.setBounds(300, 300, 280, 320);
        this.add(jl1);
        this.add(jl2);
        this.add(jt1);
        this.add(jt2);
        this.add(jb1);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String date = format.format(diary.getDateTime());
        this.setTitle(date);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jb1)
            this.dispose();
    }
}
