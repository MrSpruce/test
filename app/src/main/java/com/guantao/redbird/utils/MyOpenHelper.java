package com.guantao.redbird.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //创建用户表
        db.execSQL("CREATE TABLE [login](\n" +
                "    [login_id] INTEGER PRIMARY KEY not null, \n" +
                "    [login_name] VARCHAR(32) not null,\n" +
                "    [login_pwd] VARCHAR(16) not null\n" +
                "    );");
        db.execSQL("CREATE TABLE [department_information](\n" +
                "    [department_id] INTEGER PRIMARY KEY not null, \n" +
                "    [department_name] VARCHAR(64) not null,\n" +
                "    [department_pwd] VARCHAR(1024)\n" +
                "    );");
        db.execSQL("CREATE TABLE [user_information](\n" +
                "    [user_id] VARCHAR(32) PRIMARY KEY not null, \n" +
                "    [user_name] VARCHAR(32) not null,\n" +
                "    [user_pic] BINARY(10),\n" +
                "    [user_sign] BINARY(10),\n" +
                "    [department_id] INTEGER,\n" +
                "    [duty] VHARCHAR(32),\n" +
                "    foreign key(department_id) references department_information(department_id)\n" +
                "    );");
        db.execSQL("CREATE TABLE [login_user](\n" +
                "    [user_id] VARCHAR(32) PRIMARY KEY not null, \n" +
                "    [login_id] INTEGER,\n" +
                "    foreign key(user_id) references user_information(user_id),\n" +
                "    foreign key(login_id) references login(login_id)\n" +
                "    );");
        //在login表中模拟三条虚拟用户数据
        db.execSQL("insert into login values(1,\"abc\",\"15832088888\");");
        db.execSQL("insert into login values(2,\"def\",\"15832088888\");");
        db.execSQL("insert into login values(3,\"haha\",\"15832088888\");");
        db.execSQL("insert into login values(4,\"root\",\"15832088888\");");

        //建立采集信息表，以上都是用户信息表
        db.execSQL("CREATE TABLE [registration_clue](\n" +
                "    [clue_id] varchar(32) PRIMARY KEY NOT NULL, \n" +
                "    [clue_number] INTEGER, \n" +
                "    [clue_source] VARCHAR(32), \n" +
                "    [contact] varchar(32), \n" +
                "    [contact_phone] varchar(16), \n" +
                "    [contact_address] varchar(64), \n" +
                "    [illegal_address] varchar(64), \n" +
                "    [clue_content] varchar(2048), \n" +
                "    [operator_suggestion] varchar(256), \n" +
                "    [operator_suggestion_time] TIME, \n" +
                "    [operator_sign] varchar(32), \n" +
                "    [leader_suggestion] varchar(256), \n" +
                "    [leader_suggestion_time] TIME, \n" +
                "    [leader_sigh] varchar(32), \n" +
                "    [create_time] TIME, \n" +
                "    [is_finished] INTEGER, \n" +
                "    [clue_geometry] varchar(1024), \n" +
                "    [clue_title] varchar(64), \n" +
                "    [illegal_address_village] varchar(64), \n" +
                "    [login_user_name] varchar(10), \n" +
                "    [illegal_address_town] varchar(64));");
        //信息采集表中模拟三条假数据
        db.execSQL("insert into registration_clue values(\"001\",\"asdf\",\"threeinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"def\",\"sixinfo\"\n" +
                ");");
        db.execSQL("insert into registration_clue values(\"002\",\"asdf\",\"threeinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"abc\",\"sixinfo\"\n" +
                ");");
        db.execSQL("insert into registration_clue values(\"003\",\"asdf\",\"threeinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"fiveinfo\",\"sixinfo\",\n" +
                "\"fourinfo\",\"abc\",\"sixinfo\"\n" +
                ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        super.onOpen(db);
    }
}
