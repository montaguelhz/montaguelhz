package com.lao.Util;

import com.lao.Model.Diary;
import lombok.Getter;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Getter
public class Log {
    ArrayList<Diary> logDiary;
    int userId;

    public Log() {
        BufferedReader bufferedReader = null;
        logDiary=new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    new FileInputStream("src\\log.txt"), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);

            String s;
            s = bufferedReader.readLine();
            userId = Integer.parseInt(s);
            while ((s = bufferedReader.readLine()) != null) {
                Diary temp = new Diary();
                temp.setDiaryId(Integer.parseInt(s));
                s = bufferedReader.readLine();
                temp.setTitle(s);
                s = bufferedReader.readLine();
                temp.setContent(s);
                s = bufferedReader.readLine();
                temp.setUserId(Integer.parseInt(s));
                s = bufferedReader.readLine();
                try {
                    temp.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                logDiary.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Diary getTheDiary(Date date) {
        if (logDiary != null) {
            for (Diary diary : logDiary) {
                if (new SimpleDateFormat("yyyy-MM-dd").format(diary.getDateTime()).equals(
                        new SimpleDateFormat("yyyy-MM-dd").format(date))) {
                    return diary;
                }
            }
        }
        return null;
    }

    public ArrayList<Diary> getNowAfterDiary() {
        ArrayList<Diary> diaryArrayList = new ArrayList<>();
        for (Diary diary : logDiary) {
            if (diary.getDateTime().after(new java.util.Date())) diaryArrayList.add(diary);
        }
        return diaryArrayList;
    }

    public ArrayList<Diary> getAllDiary() {
        return logDiary;
    }

    public void updateTheDiary(Diary targetDiary) {
        for (int i = 0; i < logDiary.size(); i++) {
            if (new SimpleDateFormat("yyyy-MM-dd").format(logDiary.get(i).getDateTime()).equals(
                    new SimpleDateFormat("yyyy-MM-dd").format(targetDiary.getDateTime()))) {
                logDiary.set(i,targetDiary);
            }
        }
        save();
    }

    public void insertTheDiary(Diary diary) {
        logDiary.add(diary);
        save();
    }

    public void updateLog() {
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init();
        if (jdbcUtil.isOnLine()) {
            userId=JDBCUtil.getNowUserId();
            logDiary = jdbcUtil.getAllDiary(userId);
            save();
        }
    }

    private void save() {
        BufferedWriter bufferedWriter = null;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    new FileOutputStream("src\\log.txt"), "utf8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            try {
                bufferedWriter.write(Integer.toString(userId));
                bufferedWriter.newLine();


                for (Diary diary : logDiary) {

                    bufferedWriter.write(Integer.toString(diary.getDiaryId()));
                    bufferedWriter.newLine();
                    bufferedWriter.write(diary.getTitle());
                    bufferedWriter.newLine();
                    bufferedWriter.write(diary.getContent());
                    bufferedWriter.newLine();
                    bufferedWriter.write(Integer.toString(diary.getUserId()));
                    bufferedWriter.newLine();
                    bufferedWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diary.getDateTime()));
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void updateDatabase() {
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init();
        if (jdbcUtil.isOnLine() && userId==JDBCUtil.getNowUserId()) {
            ArrayList<Diary> data;

            data = jdbcUtil.getAllDiary(userId);
            boolean isExist;
            if (logDiary != null) {
                for (Diary diary : logDiary) {
                    isExist = false;
                    for (Diary datum : data) {
                        if (new SimpleDateFormat("yyyy-MM-dd").format(diary.getDateTime()).equals(
                                new SimpleDateFormat("yyyy-MM-dd").format(datum.getDateTime()))) {
                            isExist = true;
                            jdbcUtil.updateTheDiary(diary);
                        }
                    }
                    if (!isExist) {
                        jdbcUtil.insertTheDiary(diary);
                    }
                }
            }
        }
    }
}
