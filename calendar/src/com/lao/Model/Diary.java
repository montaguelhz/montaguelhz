package com.lao.Model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Diary implements Comparable<Diary>{

    private int DiaryId;
    private String title;
    private String content;
    private int UserId;
    private Date dateTime;

    public Diary()
    {

    }



    public int compareTo(Diary diary)
    {
        if(dateTime==null || diary.dateTime==null) return 0;
        if(dateTime.before(diary.dateTime))
        {
            return -1;
        }
        else if(dateTime.after(diary.dateTime))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public String toString()
    {
        if(dateTime!=null){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=dateFormat.format(dateTime);
        return "标题："+title+";日期："+dateStr;
        }
        return null;
    }
}
