package com.unist.npc.queuing;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jeonghyun Lee on 2015. 9. 20..
 */
public class DBManager_table_manager extends SQLiteOpenHelper {
    ArrayList<Integer> stateList = new ArrayList<Integer>();
    public Context mcontext;
    public DBManager_table_manager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Table_Manager(tableID INTEGER PRIMARY KEY ,startTime INTEGER ,No_of_people INTEGER ,Grouped_tables INTEGER ,State INTEGER);");
        try {
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(1, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(2, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(3, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(4, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(5, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(6, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(7, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(8, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(9, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(10, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(11, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(12, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(13, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(14, 0, 0, 0, 0);");
            sqLiteDatabase.execSQL("INSERT INTO Table_Manager VALUES(15, 0, 0, 0, 0);");
        }
        catch (Exception e){
            Log.e(e.toString(), "dB가 만들어 지지 않는다 으허");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void update(String _query) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(_query);
            db.close();
        }
        catch(Exception e){
            Log.e(e.toString(),e.toString());
        }
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }


    public long returnstartTime(int table_id) {
        SQLiteDatabase db = getReadableDatabase();
        long starttime;

        Cursor cursor = db.rawQuery("select startTime from Table_Manager", null);
        for(int i =1; i != table_id ; i++){
            cursor.moveToNext();
        }
        starttime = cursor.getInt(0);


        return starttime;
    }

    public String return_No_Of_People(int table_id) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select No_of_people from Table_Manager", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public String return_Grouped_tables(int table_id) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "nothing";

        Cursor cursor = db.rawQuery("select Grouped_tables from Table_Manager", null);
        while(cursor.moveToNext()) {
            str = cursor.getString(0);
        }

        return str;
    }
    public ArrayList<Integer> returnState() {
        SQLiteDatabase db = getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery("SELECT State FROM  Table_Manager", null);
            do {
                stateList.add(cursor.getInt(0));
            }while (cursor.moveToNext());


        }
        catch (Exception e){
            Log.e("ㄴ이ㅓㅏㄹ님러나이","sakdhsajkdhkasjhfdksaj");
        }

        return stateList;
    }

}
