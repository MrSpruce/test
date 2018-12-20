package com.guantao.redbird.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ComUtils {

    /**
     * 比较文件名，获取文件数组
     *
     * @param files
     * @param picType
     * @return
     */
    public static ArrayList getFileNamesbyPicType(File[] files, String picType) {
        ArrayList<String> picArrayList = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            String sFileName = files[i].getName();
            if (sFileName.contains(picType) == true)
                picArrayList.add(sFileName);
        }
        return picArrayList;
    }

    /**
     * 字符串转时间
     *
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 时间转换成时间戳
     *
     * @param time
     * @return
     */
    public static long dateToTimestamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime() / 1000;
            return ts;
        } catch (ParseException e) {
            return 0;
        }
    }

}
