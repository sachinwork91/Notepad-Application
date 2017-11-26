package com.mynotepad.android.mynotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sachin on 2016-10-02.
 */
public class NotebookDbAdapter {



    private static final String DATABASE_NAME="notebookk123.db";
    private static final int DATABASE_VERSION=1;

    public static final String NOTE_TABLE="note_table1";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_MESSAGE="message";
    public static final String COLUMN_CATEGORY="category";
    public static final String COLUMN_DATE="date";
    public static final String COLUMN_COLOR="color";
    public static final String COLUMN_BOLD="bold";
    public static final String COLUMN_PATH="path";

    private String[] allColumns={COLUMN_ID,COLUMN_TITLE,COLUMN_MESSAGE , COLUMN_CATEGORY,COLUMN_DATE, COLUMN_COLOR , COLUMN_BOLD, COLUMN_PATH};


   public static final String CREATE_TABLE_NOTE="create table "+NOTE_TABLE+ " ( "+
           COLUMN_ID+ " integer primary key autoincrement, "+
           COLUMN_TITLE+" text not null, "+
           COLUMN_MESSAGE+" text not null, "+
           COLUMN_CATEGORY+" text not null, "+
           COLUMN_DATE+" , "+
           COLUMN_COLOR + " integer, "+
           COLUMN_BOLD + " integer, "+
           COLUMN_PATH + " text ); ";


    private SQLiteDatabase sqlDB;
    private Context context;

    private NotebookDbHelper notebookDbHelper;

    public NotebookDbAdapter(Context ctx)
    {

        context=ctx;

    }


    public NotebookDbAdapter open() throws android.database.SQLException{

        notebookDbHelper=new NotebookDbHelper(context);
        sqlDB=notebookDbHelper.getWritableDatabase();
        return this;
    }

    public  void close(){  notebookDbHelper.close();
    }

    public MyNote createNote(String title, String message, MyNote.Category category, int colorNote, int text_bold, String imagepath)
    {

        ContentValues values=new ContentValues();

        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_CATEGORY, category.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+"");
        values.put(COLUMN_COLOR, colorNote);
        values.put(COLUMN_BOLD, text_bold);
        values.put(COLUMN_PATH,imagepath);
        long insertId=sqlDB.insert(NOTE_TABLE,null,values);

        Cursor cursor=sqlDB.query(NOTE_TABLE, allColumns ,COLUMN_ID  +"=" +insertId,null,null,null, null);
        cursor.moveToFirst();
        MyNote newMyNote = cursorToNote(cursor);
        cursor.close();


        return newMyNote;

    }


      public long deleteNote(long idToDelete)
      {
          return sqlDB.delete(NOTE_TABLE, COLUMN_ID+" = "+idToDelete, null);
      }

    public long updateNote(long idToUpdate, String newTitle , String newMessage, MyNote.Category newCategory, int colorNote, int text_bold, String imagepath){
        ContentValues values= new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_MESSAGE, newMessage);
        values.put(COLUMN_CATEGORY, newCategory.name());
        values.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+"");
        values.put(COLUMN_COLOR, colorNote);
        values.put(COLUMN_BOLD, text_bold);
        values.put(COLUMN_PATH, imagepath);

        return sqlDB.update(NOTE_TABLE, values, COLUMN_ID +" = "+ idToUpdate, null);

    }

    public ArrayList<MyNote> getAllNotes(){
        ArrayList<MyNote> myNotes = new ArrayList<MyNote>();

        //getting the information from the database for the myNotes in it
        Cursor cursor=sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        //Looping through all the objects of the database and adding them to the list
        for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
           MyNote myNote =cursorToNote(cursor);
            myNotes.add(myNote);
        }
        cursor.close();
        return myNotes;

    }

    public ArrayList<MyNote> getFileteredNotes(String filer){
        ArrayList<MyNote> myNotes = new ArrayList<MyNote>();

        //getting the information from the database for the myNotes in it
        Cursor cursor=sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        //Looping through all the objects of the database and adding them to the list
        for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
            MyNote myNote =cursorToNote(cursor);
            myNotes.add(myNote);
        }
        cursor.close();
        return myNotes;

    }

//This function is used to convert the cursor to note
    private MyNote cursorToNote(Cursor cursor)
    {
        MyNote newMyNote =new MyNote(cursor.getString(1), cursor.getString(2), MyNote.Category.valueOf(cursor.getString(3)),
             cursor.getLong(0),cursor.getLong(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7));

        return newMyNote;
    }


//Creating a Helper class to help with the database stuff
 private static class NotebookDbHelper extends SQLiteOpenHelper{

      NotebookDbHelper(Context ctx){
        super(ctx, DATABASE_NAME, null , DATABASE_VERSION);

      };

        @Override
        public void onCreate(SQLiteDatabase db){
          //Create note table
            db.execSQL(CREATE_TABLE_NOTE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
     Log.d("Upgrading DataBase", "Upgrading Database");
        Log.w(NotebookDbHelper.class.getName(),
                "Upgrading database from Version"+ oldVersion+" to "+newVersion);
            db.execSQL("DROP TABLE IF EXISTS "+ NOTE_TABLE);
            onCreate(db);

        }

    };

    public ArrayList<MyNote> getoneNote(String str){
        ArrayList<MyNote> myNotes = new ArrayList<MyNote>();


         String search="%"+str+"%";
        String selectQuery="select * from note_table1 where title like '"+search+ "' or message like '"+search+"'";

        //getting the information from the database for the myNotes in it
//        Cursor cursor=sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        Cursor cursor=sqlDB.rawQuery(selectQuery,null);

        //Looping through all the objects of the database and adding them to the list
        for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
            MyNote myNote =cursorToNote(cursor);
            myNotes.add(myNote);
            }
        cursor.close();
        return myNotes;


    }

    public ArrayList<MyNote> getNoteForLabel(String str){
        ArrayList<MyNote> myNotes = new ArrayList<MyNote>();


        String search="%"+str+"%";
        String selectQuery="select * from note_table1 where category like '"+search+ "'";

        //getting the information from the database for the myNotes in it
//        Cursor cursor=sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        Cursor cursor=sqlDB.rawQuery(selectQuery,null);

        //Looping through all the objects of the database and adding them to the list
        for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
            MyNote myNote =cursorToNote(cursor);
            myNotes.add(myNote);

        }
        cursor.close();
        return myNotes;


    }



}
