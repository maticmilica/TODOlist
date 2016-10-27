package mmilica.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mmilica on 27.10.2016..
 */

public class DataBase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "tasks";
    public static final String TASK_NAME = "name";
    public static final String TASK_DESC = "description";
    public static final String TASK_BELONG = "belong";
    public static final String TASK_CHECKED = "checked";

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                TASK_NAME + " TEXT, " +
                TASK_DESC + " TEXT, " +
                TASK_BELONG + " TEXT, " +
                TASK_CHECKED + " TEXT);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(Row row)
    {
        //Log.d("insert_pre", ""+getCount());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TASK_NAME, row.taskName);
        values.put(TASK_DESC, row.description);
        values.put(TASK_BELONG, row.belong);
        values.put(TASK_CHECKED, row.isChecked);

        db.insert(TABLE_NAME, null, values);
        //Log.d("insert_posle", "" + getCount());

    }

    public int getCount(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        return cursor.getCount();
    }

    public ArrayList<Row> read()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        ArrayList<Row> rows = new ArrayList<Row>();
        int i = 0;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(TASK_NAME);
            String taskName = cursor.getString(index);

            index = cursor.getColumnIndex(TASK_DESC);
            String taskDesc = cursor.getString(index);

            index = cursor.getColumnIndex(TASK_BELONG);
            String taskBelong = cursor.getString(index);

            index = cursor.getColumnIndex(TASK_CHECKED);
            String taskChecked = cursor.getString(index);

            Row row = new Row(taskName, taskDesc, taskBelong, Boolean.valueOf(taskChecked));
            rows.add(0, row);
        }
        return rows;
    }
}
