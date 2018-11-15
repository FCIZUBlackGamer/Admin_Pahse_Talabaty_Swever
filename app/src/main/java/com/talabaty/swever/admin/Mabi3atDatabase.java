package com.talabaty.swever.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Mabi3atDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Mabi3atPermission";

    private static final String TABLE_NAME = "TABLE_Local";

    private static final String UID = "id";

    private static final String View = "read";

    private static final String Detalis = "Detalis";

    private static final String Sends = "Sends";

    private static final String Refuse = "Refuse";

    private static final String PreAndDirect = "PreAndDirect"; //http://www.selltlbaty.sweverteam.com/
    private static final String PreCancel = "PreCancel"; //http://www.selltlbaty.sweverteam.com/
    private static final String Accept = "Accept"; //http://www.selltlbaty.sweverteam.com/
    private static final String PreCancelToNewOrder = "PreCancelToNewOrder"; //http://www.selltlbaty.sweverteam.com/
    private static final String Received = "Received"; //http://www.selltlbaty.sweverteam.com/
    private static final String Transport = "Transport"; //http://www.selltlbaty.sweverteam.com/
    private static final String TransportAccept = "TransportAccept"; //http://www.selltlbaty.sweverteam.com/
    private static final String Screens2Id = "Screens2Id"; //http://www.selltlbaty.sweverteam.com/

    private static final int DATABASE_VERSION = 2;
    Context cont;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME +
            "( " + UID + " integer primary key , "
            + View + " varchar(255) not null, "
            + Detalis + " varchar(255) , "
            + Sends + " varchar(20) , "
            + Refuse + " varchar(255) not null, "
            + PreAndDirect + " varchar(255), "
            + PreCancel + " varchar(255), "
            + Accept + " varchar(255), "
            + PreCancelToNewOrder + " varchar(255), "
            + Received + " varchar(255), "
            + Transport + " varchar(255), "
            + TransportAccept + " varchar(255), "
            + Screens2Id + " varchar(255) );";

    // Database Deletion
    private static final String DATABASE_DROP = "drop table if exists " + TABLE_NAME + ";";

    public Mabi3atDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(DATABASE_CREATE);

            // Toast.makeText(cont,"تم إنشاء سله تسوق", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(cont, "database doesn't created " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DATABASE_DROP);
            onCreate(db);
            Toast.makeText(cont, "تم تحديث الصلاحيات", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(cont, "database doesn't upgraded " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean InsertData(String Vie, String Detali, String Send, String Refus, String PreAndDirec,
                              String PreCance, String Accep, String PreCancelToNewOrde, String Receive, String Transpor,
                              String TransportAccep, String Screens2I) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(View, Vie);
        contentValues.put(Detalis, Detali);
        contentValues.put(Sends, Send);
        contentValues.put(Refuse, Refus);
        contentValues.put(PreAndDirect, PreAndDirec);
        contentValues.put(PreCancel, PreCance);
        contentValues.put(Accept, Accep);
        contentValues.put(PreCancelToNewOrder, PreCancelToNewOrde);
        contentValues.put(Received, Receive);
        contentValues.put(Transport, Transpor);
        contentValues.put(TransportAccept, TransportAccep);
        contentValues.put(Screens2Id, Screens2I);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return result == -1 ? false : true;
    }

    public Cursor ShowData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " ;", null);
        return cursor;
    }

    public boolean UpdateData(String id, String Vie, String Detali, String Send, String Refus, String PreAndDirec,
                              String PreCance, String Accep, String PreCancelToNewOrde, String Receive, String Transpor,
                              String TransportAccep, String Screens2I) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID, id);
        contentValues.put(View, Vie);
        contentValues.put(Detalis, Detali);
        contentValues.put(Sends, Send);
        contentValues.put(Refuse, Refus);
        contentValues.put(PreAndDirect, PreAndDirec);
        contentValues.put(PreCancel, PreCance);
        contentValues.put(Accept, Accep);
        contentValues.put(PreCancelToNewOrder, PreCancelToNewOrde);
        contentValues.put(Received, Receive);
        contentValues.put(Transport, Transpor);
        contentValues.put(TransportAccept, TransportAccep);
        contentValues.put(Screens2Id, Screens2I);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});

        return true;
    }

    public boolean UpdateData(String id, String lang, String PROUCT_STATE, int c) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID, id);
        contentValues.put(PROUCT_STATE, PROUCT_STATE);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});

        return true;
    }

    public int DeleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
    public void EmptyData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ TABLE_NAME);
    }
}
