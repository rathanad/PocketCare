package com.palle.rathan.pocketcare.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.palle.rathan.pocketcare.model.Transaction;

import java.util.ArrayList;

/**
 * Created by Rathan on 9/24/2016.
 */
public class MyDatabase {

    private SQLiteDatabase database;
    private PocketCareDbHelper helper;

    public MyDatabase(Context context) {
        helper = new PocketCareDbHelper(context);
    }

    private SQLiteDatabase openReadableDatabaseInstance() {
        return helper.getReadableDatabase();
    }

    private SQLiteDatabase openWritableDatabaseInstance() {
        return helper.getWritableDatabase();
    }

    private void closeDatabaseConnection() {
        database.close();
        helper.close();
    }





    //region Other Database functions
   /* public long updateTableName(long id, String column1, int column2){

        database = openWritableDatabaseInstance();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableNameEntry.COLUMN_1_NAME, column1);
        contentValues.put(TableNameEntry.COLUMN_2_NAME, column2);


        String selection = TableNameEntry._ID +" = ? ";
        String[] selectionArgs = {String.valueOf(id)};

        long value = database.update(TableNameEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        closeDatabaseConnection();

        return value;
    }*/


    /*public long deleteAllTableDetails() {
        database = openWritableDatabaseInstance();

        long l= database.delete(TableNameEntry.TABLE_NAME, null, null);

        closeDatabaseConnection();

        return l;
    }*/

    /*public ArrayList<Table> getTableDataInArrayList() {
        database = openReadableDatabaseInstance();

        Cursor c =  database.query(TableNameEntry.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Table> arrayListTables = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                //String stringDate = Utils.SIMPLE_DATE_FORMAT.format(date.getTime());
                Table table = new Table(c.getInt(c.getColumnIndex(TableNameEntry._ID)),
                        c.getString(c.getColumnIndex(TableNameEntry.COLUMN_1_NAME)),
                        c.getInt(c.getColumnIndex(TableNameEntry.COLUMN_2_NAME)));
                arrayListTables.add(table);
            }while (c.moveToNext());
        }
        closeDatabaseConnection();

        return arrayListTables;
    }*/
    //endregion

    public boolean checkIfUsernameAndPasswordExist(String username, String password){
        database = openReadableDatabaseInstance();

        String selection = PocketCareContract.UserCredentialsEntry.COLUMN_USERNAME +" = ? AND "+ PocketCareContract.UserCredentialsEntry.COLUMN_PASSWORD +" = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = database.query(PocketCareContract.UserCredentialsEntry.TABLE_NAME, null, selection,
                selectionArgs, null, null, null);

        boolean userExists = false;

        if(cursor.moveToFirst()){
            userExists = true;
        } else {
            userExists = false;
        }

        closeDatabaseConnection();

        return userExists;
    }

    public boolean checkIfUsernameExists(String username){
        database = openReadableDatabaseInstance();

        String selection = PocketCareContract.UserCredentialsEntry.COLUMN_USERNAME +" = ?";
        String[] selectionArgs = {username};

        Cursor cursor = database.query(PocketCareContract.UserCredentialsEntry.TABLE_NAME, null, selection,
                selectionArgs, null, null, null);

        boolean userExists = false;

        if(cursor.moveToFirst()){
            userExists = true;
        } else {
            userExists = false;
        }

        closeDatabaseConnection();

        return userExists;
    }



    public long createUserCredentials(String username, String password) {

        database = openWritableDatabaseInstance();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PocketCareContract.UserCredentialsEntry.COLUMN_USERNAME, username);
        contentValues.put(PocketCareContract.UserCredentialsEntry.COLUMN_PASSWORD, password);
        long value = database.insert(PocketCareContract.UserCredentialsEntry.TABLE_NAME, null, contentValues);

        closeDatabaseConnection();

        return value;

    }

    /**
     * Call this method from summary fragment
     * @param type
     * @return
     */
    public ArrayList<Transaction> getTransactionsForType(String type){

        database = openReadableDatabaseInstance();

        String[] projections = null;//{PocketCareContract.TransactionDetailsEntry.NAME, PocketCareContract.TransactionDetailsEntry.AMOUNT};
        String selection = PocketCareContract.TransactionDetailsEntry.TYPE +" = ? ";
        String[] selectionArgs = {type};
        String groupBy = PocketCareContract.TransactionDetailsEntry.DATE + " ASC ";

        Cursor cursor = database.query(PocketCareContract.TransactionDetailsEntry.TABLE_NAME, projections, selection, selectionArgs, null, null, groupBy);

        ArrayList<Transaction> arrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Transaction transaction = new Transaction(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(cursor.getColumnIndex(PocketCareContract.TransactionDetailsEntry.TYPE)),
                        cursor.getString(cursor.getColumnIndex(PocketCareContract.TransactionDetailsEntry.CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(PocketCareContract.TransactionDetailsEntry.DATE)),
                        cursor.getDouble(cursor.getColumnIndex(PocketCareContract.TransactionDetailsEntry.AMOUNT)));

                arrayList.add(transaction);

            }while (cursor.moveToNext());
        }

        closeDatabaseConnection();

        return arrayList;


    }


    /**
     * Call from transaction fragment to create a new transaction
     * @return
     */
    public long createTransaction(String name, double amount, String category, String date, String type) {


        database = openWritableDatabaseInstance();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PocketCareContract.TransactionDetailsEntry.NAME,name);
        contentValues.put(PocketCareContract.TransactionDetailsEntry.AMOUNT,amount);
        contentValues.put(PocketCareContract.TransactionDetailsEntry.CATEGORY, category);
        contentValues.put(PocketCareContract.TransactionDetailsEntry.DATE, date);
        contentValues.put(PocketCareContract.TransactionDetailsEntry.TYPE, type);

        long value = database.insert(PocketCareContract.TransactionDetailsEntry.TABLE_NAME, null, contentValues);

        closeDatabaseConnection();

        return value;

    }

    public ArrayList<String> getAllCategories(){

        database = openReadableDatabaseInstance();

        String[] projections = {PocketCareContract.MyCategoryEntry.COLUMN_CATEGORY_NAME};

        String orderBy = PocketCareContract.MyCategoryEntry.COLUMN_CATEGORY_NAME+ " ASC";

        Cursor cursor = database.query(PocketCareContract.MyCategoryEntry.TABLE_NAME, projections, null, null, null, null, orderBy);

        ArrayList<String> category = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                category.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        closeDatabaseConnection();

        return category;

    }


    public long insertCategory(String category) {

        database = openWritableDatabaseInstance();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PocketCareContract.MyCategoryEntry.COLUMN_CATEGORY_NAME, category);

        long value = database.insert(PocketCareContract.MyCategoryEntry.TABLE_NAME, null, contentValues);

        closeDatabaseConnection();

        return value;

    }


    public boolean checkIfCategoryExist(String category){
        database = openReadableDatabaseInstance();

        String selection = PocketCareContract.MyCategoryEntry.COLUMN_CATEGORY_NAME + " = ? " ;
        String[] selectionArgs = {category};

        Cursor cursor = database.query(PocketCareContract.MyCategoryEntry.TABLE_NAME, null, selection,
                selectionArgs, null, null, null);

        boolean categoryExists = false;

        if(cursor.moveToFirst()){
            categoryExists = true;
        } else {
            categoryExists = false;
        }

        closeDatabaseConnection();

        return categoryExists;
    }


    private class PocketCareDbHelper extends SQLiteOpenHelper {

        //region SQL Statements
        private static final String SQL_CREATE_USER_CREDENTIALS_TABLE = "CREATE TABLE " + PocketCareContract.UserCredentialsEntry.TABLE_NAME + "("
                + PocketCareContract.UserCredentialsEntry._ID + " INTEGER PRIMARY KEY, "
                + PocketCareContract.UserCredentialsEntry.COLUMN_USERNAME + " TEXT NOT NULL, "
                + PocketCareContract.UserCredentialsEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";

        private static final String SQL_CREATE_TRANSACTION = "CREATE TABLE "+ PocketCareContract.TransactionDetailsEntry.TABLE_NAME+"("
                + PocketCareContract.TransactionDetailsEntry._ID +" INTEGER PRIMARY KEY, "
                + PocketCareContract.TransactionDetailsEntry.NAME +" TEXT NOT NULL, "
                + PocketCareContract.TransactionDetailsEntry.AMOUNT +" NUMBER NOT NULL, "
                + PocketCareContract.TransactionDetailsEntry.CATEGORY +" TEXT, "
                + PocketCareContract.TransactionDetailsEntry.DATE +" TEXT NOT NULL, "
                + PocketCareContract.TransactionDetailsEntry.TYPE +" TEXT NOT NULL);";

        private static final String SQL_CREATE_CATEGORY = "CREATE TABLE "+PocketCareContract.MyCategoryEntry.TABLE_NAME+" ("
                + PocketCareContract.MyCategoryEntry._ID + " INTEGER PRIMARY KEY, "
                + PocketCareContract.MyCategoryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";

        private static final String SQL_DROP_USER_CREDENTIALS_TABLE = "DROP TABLE " + PocketCareContract.UserCredentialsEntry.TABLE_NAME + ";";
        private static final String SQL_DROP_TRANSACTIONS_TABLE = "DROP TABLE " + PocketCareContract.TransactionDetailsEntry.TABLE_NAME + ";";
        //private static final String SQL_DROP_CATEGORIES_TABLE = "DROP TABLE " + PocketCareContract.MyCategoryEntry.TABLE_NAME + ";";
        //endregion

        private static final String DATABASE_NAME = "PocketCare.db";

        private static final int DATABASE_VERSION = 2;

        public PocketCareDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USER_CREDENTIALS_TABLE);
            db.execSQL(SQL_CREATE_TRANSACTION);
            db.execSQL(SQL_CREATE_CATEGORY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                db.execSQL(SQL_DROP_USER_CREDENTIALS_TABLE);
                db.execSQL(SQL_DROP_TRANSACTIONS_TABLE);
                //db.execSQL(SQL_DROP_CATEGORIES_TABLE);
                onCreate(db);
            }
        }
    }



}
