package com.ajitesh.android.hattibus;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.util.Log;

import android.database.*;
import android.database.sqlite.*;
import android.webkit.WebStorage;
import android.widget.Toast;

import java.io.*;



/**
 * Created by Divya on 10/26/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.ajitesh.android.hattibus/databases/";
    private static String DB_NAME = "HATTIBUS.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */

    public MySQLiteHelper(Context context) throws Exception{
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        Log.i("Constructor:","Called");
        createDataBase();
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
        Log.i("createDataBase:","Started...");
        boolean dbExist = checkDataBase();
        if(dbExist){

            Log.i("createDataBase:","database already exists...");
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
        Log.i("copyDataBase:","Started...");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.i("copyDataBase:","Completed...");
    }

    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public ArrayList<String> getAllBusStops() {

        openDataBase();
        ArrayList<String> hattilist =  new ArrayList<String>();

        Cursor cursor = this.myDataBase.query("BUSSTOPS", new String[] {"BUSSTOPNAME"}, null, null, null, null, null);


        if(cursor.getCount() >0)
        {
            String[] str = new String[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext())
            {
                hattilist.add(cursor.getString(cursor.getColumnIndex("BUSSTOPNAME")));
                i++;
            }
        }
        else {
            hattilist.add("No data in DB");
        }
        close(); //CLOSE THE DB

        return hattilist;
    }


    public  Cursor getDirectRoutes(String origin, String destination) {
        openDataBase();
        String query = "SELECT _ROUTEID,ORIGIN,DESTINATION FROM BUSROUTES WHERE ORIGIN = '" + origin + "' AND DESTINATION= '"+ destination +"'";
        Cursor cursor = this.myDataBase.rawQuery(query, null);
        Log.i("getDirectRoutes:", "Count=" + cursor.getCount());
        close(); //CLOSE THE DB
        return  cursor;
    }

}