package www.studyviewer.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbmanager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDatabase";
    private static final String TABLE_NAME  = "MyTable";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "PHONE";
    private static final String COL4 = "EMAIL";






    public dbmanager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 + " TEXT, "
                + COL3 + " TEXT, "
                + COL4 + " TEXT" + ");";
        db.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String NAME,String PHONE,String EMAIL)
    {
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COL2,NAME);
        values.put(COL3,PHONE);
        values.put(COL4,EMAIL);

        long res = db.insert(TABLE_NAME,null,values);

        if(res==-1){
            return false;
        }else
        {
            return true;
        }

    }

    public void deleteData(String name, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                    COL2 + "= '" + name + "'" +
                    " AND " + COL3 + "= '" + phone + "'" +
                    " AND " + COL4 + "= '" + email + "'";
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public Cursor getData(){
        SQLiteDatabase db =this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }

    public Cursor delete(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }

    public ArrayList<Model> getAlllistData() {


        ArrayList<Model> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT * FROM " + TABLE_NAME ;



        Cursor cursor = db.rawQuery(query, null);
        // Cursor cursor1 = db.rawQuery(query1, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String email = cursor.getString(3);


            Model dataModel = new Model(id,name, phone, email);
            arrayList.add(dataModel);

        }

        db.close();
        return arrayList;
    }
}
