package com.lao.UI;

import com.lao.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Success extends JFrame implements ActionListener {
    public Success() {
        JPanel jp=new JPanel();
        JButton jb=new JButton("注册成功");
        jb.addActionListener(this);
        //设置布局管理
        this.setLayout(new GridLayout(1, 1));
        jp.add(jb);
        this.add(jp);
        //锁定窗体
        this.setResizable(false);

        this.setLayout(new GridLayout(4, 1));
        //给窗口设置标题
        this.setTitle("注册成功");
        //设置窗体大小
        this.setSize(300, 200);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "注册成功"))
            this.dispose();
    }
}
