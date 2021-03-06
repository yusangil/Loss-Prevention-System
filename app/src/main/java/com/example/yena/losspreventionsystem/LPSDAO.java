package com.example.yena.losspreventionsystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.acl.Group;
import java.util.ArrayList;

/**
 * Created by yena on 2016-04-16.
 */
public class LPSDAO {

    public static ArrayList<ItemInfo> getItemInfo(Context context){
        ArrayList<ItemInfo> itemList = new ArrayList<ItemInfo>();

        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //itemList.clear();
        //TODO orderby 나중에 해야징
        Cursor cursor = db.rawQuery("select * from " + DBTable.ItemInfoTable.TABLE_NAME + ";", null);
        while(cursor.moveToNext()){
            ItemInfo item = new ItemInfo(cursor);
            itemList.add(item);
        }
        return itemList;
    }

    public static ArrayList<GroupInfo> getGroupInfo(Context context){
        ArrayList<GroupInfo> groupList = new ArrayList<GroupInfo>();

        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //groupList.clear();
        Cursor cursor = db.rawQuery("select * from " + DBTable.GroupInfoTable.TABLE_NAME + ";", null);
        while(cursor.moveToNext()){
            GroupInfo group = new GroupInfo(cursor);
            groupList.add(group);
        }
        return groupList;
    }

    public static ArrayList<GroupInfo> getGroupListOfItem(Context context, ItemInfo itemInfo){
        ArrayList<GroupInfo> groupList = new ArrayList<GroupInfo>();

        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //groupList.clear();
        Cursor cursor = db.rawQuery("select * from " + DBTable.ItemGroupTable.TABLE_NAME +
                " where " + DBTable.ItemGroupTable.BEACON_ID + " = '" + itemInfo.beaconID + "';", null);
        while(cursor.moveToNext()){
            GroupInfo group = new GroupInfo(cursor);
            groupList.add(group);
        }
        return groupList;
    }

    public static ArrayList<ItemInfo> getItemListOfGroup(Context context, GroupInfo groupInfo){
        ArrayList<ItemInfo> itemList = new ArrayList<ItemInfo>();

        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //groupList.clear();
        Cursor cursor = db.rawQuery("select * from " + DBTable.ItemGroupTable.TABLE_NAME +
                " where " + DBTable.ItemGroupTable.GROUP_ID + " = " + groupInfo.id + ";", null);
        while(cursor.moveToNext()){
            ItemInfo item = new ItemInfo(cursor);
            itemList.add(item);
        }
        return itemList;
    }

    public static void insertItemInfo(Context context, ItemInfo itemInfo){
        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into " + DBTable.ItemInfoTable.TABLE_NAME +
                " values ('" + itemInfo.beaconID + "', '" + itemInfo.name + "', " + itemInfo.distance + ", " + itemInfo.alarmStatus + ");");
    }

    public static void deleteItemInfo(Context context, ItemInfo itemInfo){
        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + DBTable.ItemInfoTable.TABLE_NAME +
                " where  " + DBTable.ItemInfoTable.BEACON_ID +" = '" + itemInfo.beaconID + "';");
    }

    public static void insertGroupInfo(Context context, GroupInfo groupInfo){
        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into " + DBTable.GroupInfoTable.TABLE_NAME +
                " values (null, '" + groupInfo.name + "');");
    }

    public static void deleteGroupInfo(Context context, GroupInfo groupInfo){
        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + DBTable.GroupInfoTable.TABLE_NAME +
                " where " + DBTable.GroupInfoTable.GROUP_ID +" = " + groupInfo.id + ";");
    }

    public static void insertItemGroup(Context context, ItemInfo itemInfo, GroupInfo groupInfo){
        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into " + DBTable.ItemGroupTable.TABLE_NAME+
                " values (null, '" + itemInfo.beaconID + "', " + groupInfo.id +");");
    }

    public static void deleteItemGroup(Context context, ItemInfo itemInfo, GroupInfo groupInfo){
        LPSDBHelper dbHelper = new LPSDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + DBTable.ItemGroupTable.TABLE_NAME +
                " where " + DBTable.ItemInfoTable.BEACON_ID + " = '" + itemInfo.beaconID + "' and " + DBTable.GroupInfoTable.GROUP_ID + " = " + groupInfo.id + ";");
    }
}
