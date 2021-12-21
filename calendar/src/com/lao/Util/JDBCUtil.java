package com.lao.Util;


import com.lao.Model.Diary;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



@Getter
@Setter
public class JDBCUtil {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String user = "root";
    private static String url = "jdbc:mysql://localhost:3306/note?useUnicode=true&characterEncoding=utf8";
    private static String password = "Js220116";
    private static int nowUserId=-1;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(url, user, password);
    }


    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
    }


    private Statement statement;
    private Connection connection;
    private ResultSet res;
    private boolean isOnLine;

    public void init() {

            try {
                connection = JDBCUtil.getConnection();
                statement = connection.createStatement();
                res = null;
                isOnLine = true;
            } catch (Exception e) {
                isOnLine = false;
            }


    }

    public void close() {
        if (isOnLine) {
            try {
                JDBCUtil.close(connection, statement, res);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int check(String getNickName, String getPassWord) {
        if (isOnLine) {
            try {
                String sql = "select * from user where passWord=\'" + getPassWord + "\' and nickName=\'" + getNickName + "\';";
                res = statement.executeQuery(sql);
                if (!res.next()) {
                    return -1;

                } else {
                    int userId = res.getInt("userId");
                    nowUserId=userId;
                    return userId;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        } else {
            Log log = new Log();
            return log.getUserId();
        }
    }

    public boolean register(String getNickName, String getPassWord) {
        if (isOnLine) {
            try {
                String sql = "select * from user where nickName=\'" + getNickName + "\';";
                res = statement.executeQuery(sql);
                if (res.next()) return false;
                sql = "insert into user (passWord,nickName) values (\'" + getPassWord + "\',\'" + getNickName + "\');";
                statement.executeUpdate(sql);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public Diary getTheDiary(int userId, Date date) {
        if (isOnLine) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = format.format(date);
                String sql = "select * from diary where userId=\'" + userId + "\' and Date_Format(dateTime,\'%Y-%m-%d\')=" +
                        "\'" + dateStr + "\';";
                res = statement.executeQuery(sql);
                if (res.next()) {
                    Diary diary = new Diary();
                    diary.setDiaryId(res.getInt(1));
                    diary.setTitle(res.getString(2));
                    diary.setContent(res.getString(3));
                    diary.setUserId(res.getInt(4));
                    String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(res.getTimestamp(5));
                    try {
                        diary.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return diary;
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Log log = new Log();
            return log.getTheDiary(date);
        }
    }

    public ArrayList<Diary> getNowAfterDiary(int userId) {
        if (isOnLine) {
            try {
                java.util.Date date = new java.util.Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = format.format(date);
                String sql = "select * from diary where userId=" + userId + " and dateTime>=" +
                        "\'" + dateStr + "\' order by dateTime;";
                res = statement.executeQuery(sql);
                ArrayList<Diary> diaries = new ArrayList<>();
                while (res.next()) {
                    Diary diary = new Diary();
                    diary.setDiaryId(res.getInt(1));
                    diary.setTitle(res.getString(2));
                    diary.setContent(res.getString(3));
                    diary.setUserId(res.getInt(4));
                    String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(res.getTimestamp(5));
                    try {
                        diary.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    diaries.add(diary);
                }
                return diaries;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Log log = new Log();
            return log.getNowAfterDiary();
        }
    }

    public ArrayList<Diary> getAllDiary(int userId) {
        if (isOnLine) {
            try {

                String sql = "select * from diary where userId=" + userId + " order by dateTime;";
                res = statement.executeQuery(sql);
                ArrayList<Diary> diaries = new ArrayList<>();
                while (res.next()) {
                    Diary diary = new Diary();
                    diary.setDiaryId(res.getInt(1));
                    diary.setTitle(res.getString(2));
                    diary.setContent(res.getString(3));
                    diary.setUserId(res.getInt(4));
                    String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(res.getTimestamp(5));
                    try {
                        diary.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    diaries.add(diary);
                }
                return diaries;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Log log = new Log();
            return log.getAllDiary();
        }
    }

    public void updateTheDiary(Diary diary) {
        if (diary == null) return;
        if (isOnLine) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(diary.getDateTime());
            try {
                String sql = String.format("update diary set dateTime=%s,content=%s,title=%s " +
                                "where userId=%s and diaryId=%s",
                        "\'" + date + "\'", "\'" + diary.getContent() + "\'", "\'" + diary.getTitle() + "\'",
                        diary.getUserId(), diary.getDiaryId());

                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Log log = new Log();
            log.updateTheDiary(diary);
        }
    }

    public void insertTheDiary(Diary diary) {
        if (diary == null) return;
        if (isOnLine) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(diary.getDateTime());
            try {

                String sql = "insert into diary (dateTime,title,content,userId) value (\'" + date
                        + "\',\'" + diary.getTitle() + "\',\'" + diary.getContent() + "\',\'" + diary.getUserId() + "\');";
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            Log log=new Log();
            log.insertTheDiary(diary);
        }
    }

    public static int getNowUserId() {
        return nowUserId;
    }

}