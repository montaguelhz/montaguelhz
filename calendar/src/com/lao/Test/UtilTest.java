package com.lao.Test;

import com.lao.Model.Diary;
import com.lao.UI.CalendarFrame;
import com.lao.UI.note;
import com.lao.Util.JDBCUtil;
import com.lao.Util.Log;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class UtilTest {

    public Statement statement;
    public Connection connection;
    public ResultSet res;

    @Test
    public void test1() {
        try {
            connection = JDBCUtil.getConnection();

            statement = connection.createStatement();

            String sql = "select * from user";
            res = statement.executeQuery(sql);
            while (res.next()) {
                int userId = res.getInt(1);
                String passWord = res.getString(2);
                String nickName = res.getString(3);
                System.out.println("userId: " + userId + "|passWord: " + passWord + "|nickName: " + nickName);
            }
        } catch (SQLException |
                ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                JDBCUtil.close(connection, statement, res);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void test2() {
        new note(1,new Date(100,6,20));
    }


    @Test
    public void test3() {
        JDBCUtil jdbcUtil =new JDBCUtil();
        jdbcUtil.init();
        Diary diary=new Diary();
        diary.setDiaryId(1);
        diary.setTitle("test");
        diary.setContent("test");
        diary.setUserId(1);
        diary.setDateTime(new java.util.Date());
        jdbcUtil.insertTheDiary(diary);
        jdbcUtil.close();
    }

    @Test
    public void t5(){
        JDBCUtil jdbcUtil =new JDBCUtil();
        jdbcUtil.init();
        jdbcUtil.getTheDiary(1,new java.sql.Date(new java.util.Date().getTime()));
        jdbcUtil.close();
    }

    @Test
    public void t6(){
        new note(1,new java.sql.Date(new java.util.Date().getTime()));
    }

    @Test
    public void t7() {
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init();
        if (jdbcUtil.isOnLine()) {
            ArrayList<Diary> logDiary = jdbcUtil.getAllDiary(1);
            BufferedWriter bufferedWriter = null;
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        new FileOutputStream("src\\log.txt"), "utf8");
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                try {
                    bufferedWriter.write(Integer.toString(1));
                    bufferedWriter.newLine();


                    for (int i = 0; i < logDiary.size(); i++) {

                        bufferedWriter.write(Integer.toString(logDiary.get(i).getDiaryId()));
                        bufferedWriter.newLine();
                        bufferedWriter.write(logDiary.get(i).getTitle());
                        bufferedWriter.newLine();
                        bufferedWriter.write(logDiary.get(i).getContent());
                        bufferedWriter.newLine();
                        bufferedWriter.write(Integer.toString(logDiary.get(i).getUserId()));
                        bufferedWriter.newLine();
                        bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logDiary.get(i).getDateTime()));
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}


