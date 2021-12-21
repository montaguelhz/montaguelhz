package com.lao.Util;

import com.lao.Model.Diary;
import com.lao.UI.Remind;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RemindThread {
    static java.util.Timer[] timer;
    static ArrayList<Diary> notExpiredDiary;

    public static void remindThread(int userId) {

        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init();
        notExpiredDiary = jdbcUtil.getNowAfterDiary(userId);
        jdbcUtil.close();
        timer = new Timer[notExpiredDiary.size()];
        for (int i = 0; i < notExpiredDiary.size(); i++) {
            timer[i]=new Timer();
            timer[i].schedule(new MyTimeTask(notExpiredDiary.get(i)), notExpiredDiary.get(i).getDateTime());
        }

    }


    public static void clearTimer() {

        for (int i = 0; i < notExpiredDiary.size(); i++) {
            timer[i].cancel();
        }
    }

}

class MyTimeTask extends TimerTask {
    Diary diary;
    public MyTimeTask(Diary targetDiary) {
        diary=targetDiary;
    }

    public void run() {
        new Remind(diary);
    }
}