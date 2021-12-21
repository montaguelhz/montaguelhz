package com.lao.UI;


import com.lao.Util.JDBCUtil;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import javax.swing.*;

import static com.lao.Model.User.login;

public class Login extends JFrame implements ActionListener {
    //定义组件
    JPanel jp1, jp2, jp3;//面板
    JLabel jlb1, jlb2;//标签
    JButton jb1, jb2;//按钮
    JTextField jtf;//文本
    JPasswordField jpf;//密码

    public static void main(String[] args) {
        new Login();
    }

    //构造函数
    public Login() {
        //创建面板
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        //创建标签
        jlb1 = new JLabel("用户名");
        jlb2 = new JLabel("密    码");

        //创建按钮
        jb1 = new JButton("登录");
        jb2 = new JButton("注册");
        jb1.addActionListener(this);
        jb2.addActionListener(this);


        //创建文本框
        jtf = new JTextField(10);
        //创建密码框
        jpf = new JPasswordField(10);

        //设置布局管理
        this.setLayout(new GridLayout(3, 1));//网格式布局

        //加入各个组件
        jp1.add(jlb1);
        jp1.add(jtf);

        jp2.add(jlb2);
        jp2.add(jpf);

        jp3.add(jb1);
        jp3.add(jb2);

        //加入到JFrame
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);

        //锁定窗体
        this.setResizable(false);

        this.setLayout(new GridLayout(4, 1));
        //给窗口设置标题
        this.setTitle("登录页");
        //设置窗体大小
        this.setSize(300, 200);
        //设置窗体初始位置
        this.setLocation(200, 150);
        //设置当关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示窗体
        this.setVisible(true);
        this.setResizable(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                JDBCUtil jdbcUtil=new JDBCUtil();
                jdbcUtil.init();
                int id = login(jtf.getText(), new String(jpf.getPassword())) ;
                if(!jdbcUtil.isOnLine()){
                JOptionPane.showMessageDialog(null,"本地登录",
                        "提示",JOptionPane.PLAIN_MESSAGE);
                dispose();
                new CalendarFrame(id);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "登录")) {
            int id = login(jtf.getText(), new String(jpf.getPassword())) ;
            if (id != -1) {

                JOptionPane.showMessageDialog(null,"登录成功",
                        "提示",JOptionPane.PLAIN_MESSAGE);
                this.dispose();
                new CalendarFrame(id);
            } else JOptionPane.showMessageDialog(null,"登录失败",
                    "提示",JOptionPane.PLAIN_MESSAGE);
        } else if (Objects.equals(e.getActionCommand(), "注册")) {
            new Register();
        }
    }


}