package com.poly.wahabi.polyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.poly.wahabi.polyapp.model.Mydata;

import java.util.ArrayList;
import java.util.List;

public class TableControllerMydata extends DatabaseHandler {

    public TableControllerMydata(Context context) {
        super(context);
    }

    public boolean create(Mydata mydata) {

        ContentValues values = new ContentValues();

        values.put("mdate", mydata.getMdate());
        values.put("posx", mydata.getPosx());
        values.put("posy", mydata.getPosy());
        values.put("D1", mydata.getD1());
        values.put("D2", mydata.getD2());
        values.put("D3", mydata.getD3());
        values.put("D4", mydata.getD4());
        values.put("D5", mydata.getD5());
        values.put("D6", mydata.getD6());

        SQLiteDatabase db = this.getWritableDatabase();

       boolean createSuccessful = db.insert("mydata", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public List<Mydata> read() {

        ArrayList<Mydata> recordsList = new ArrayList<Mydata>();

        String sql = "SELECT * FROM mydata ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String mdate = cursor.getString(cursor.getColumnIndex("mdate"));
                double posx =cursor.getDouble(cursor.getColumnIndex("posx"));
                double posy = cursor.getDouble(cursor.getColumnIndex("posy"));
                double D1 = cursor.getDouble(cursor.getColumnIndex("D1"));
                double D2 = cursor.getDouble(cursor.getColumnIndex("D2"));
                double D3 = cursor.getDouble(cursor.getColumnIndex("D3"));
                double D4 = cursor.getDouble(cursor.getColumnIndex("D4"));
                double D5 = cursor.getDouble(cursor.getColumnIndex("D5"));
                double D6 = cursor.getDouble(cursor.getColumnIndex("D6"));




                Mydata data = new Mydata();
                data.setId(id);
                data.setMdate(mdate);
                data.setPosx(posx);

                data.setPosy(posy);
                data.setD1(D1);
                data.setD2(D2);
                data.setD3(D3);
                data.setD4(D4);
                data.setD5(D5);
                data.setD6(D6);

                recordsList.add(data);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }
    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("mydata", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}