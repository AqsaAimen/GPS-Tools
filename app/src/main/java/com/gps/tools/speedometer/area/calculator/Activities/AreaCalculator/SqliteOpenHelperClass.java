package com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.measurement.api.AppMeasurementSdk.ConditionalUserProperty;

public class SqliteOpenHelperClass extends SQLiteOpenHelper {
    private String area = "area";
    private String date = DublinCoreProperties.DATE;


   // private String f3952id = TtmlNode.ATTR_ID;
    private String latlongList = "LatLongList";
    private String name = ConditionalUserProperty.NAME;
    private String table = "student_tbl";
    private String time = "time";

    public SqliteOpenHelperClass(Context context) {
        super(context, "place_db", null, 2);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE table ");
        sb.append(this.table);
        sb.append("(");
      //  sb.append(this.f3952id);
        sb.append(" integer primary key autoincrement, ");
        sb.append(this.name);
        sb.append(" varchar,   ");
        sb.append(this.date);
        sb.append(" varchar, ");
        sb.append(this.time);
        sb.append(" varchar, ");
        sb.append(this.area);
        sb.append(" varchar, ");
        sb.append(this.latlongList);
        sb.append(" varchar)");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 > i) {
            StringBuilder sb = new StringBuilder();
            sb.append("DROP TABLE if exists ");
            sb.append(this.table);
            sQLiteDatabase.execSQL(sb.toString());
        }
    }

    public boolean SavingData(String str, String str2, String str3, String str4, String str5) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.name, str);
        contentValues.put(this.date, str2);
        contentValues.put(this.time, str3);
        contentValues.put(this.area, str4);
        contentValues.put(this.latlongList, str5);
        return writableDatabase.insert(this.table, null, contentValues) != -1;
    }

    public Cursor gettingAllData() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("Select * from ");
        sb.append(this.table);
        return readableDatabase.rawQuery(sb.toString(), null);
    }

    public void deleteData(SqliteModel sqliteModel) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(this.table, "id = ?", new String[]{String.valueOf(sqliteModel.getId())});
        writableDatabase.close();
    }

    public void DeleteAllData() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(this.table, null, null);
        writableDatabase.close();
    }
}
