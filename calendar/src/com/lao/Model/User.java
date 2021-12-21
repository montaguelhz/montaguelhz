package com.lao.Model;

import com.lao.Util.JDBCUtil;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.apache.commons.codec.binary.Hex;


@Setter
@Getter
public class User {
    private int UserId;
    private String passWord;
    private String nickName;
    private ArrayList<Diary> diaries;

    public static int login(String getNickName, String getPassWord) {
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init();
        int id = jdbcUtil.check(getNickName, getPassWord);
        jdbcUtil.close();
        return id;
    }

    public static boolean register(String getNickName, String getPassWord) {
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.init();
        boolean isRegister = jdbcUtil.register(getNickName, getPassWord);
        jdbcUtil.close();
        return isRegister;
    }

//    public static String computeMD5(String data) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            return Hex.encodeHexString(md.digest(data.getBytes(StandardCharsets.UTF_8)));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


}
