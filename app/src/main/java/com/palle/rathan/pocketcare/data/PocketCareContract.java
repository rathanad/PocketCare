package com.palle.rathan.pocketcare.data;

import android.provider.BaseColumns;

/**
 * Created by Rathan on 9/24/2016.
 */
public class PocketCareContract {
    private PocketCareContract(){

    }

    public static final class UserCredentialsEntry implements BaseColumns {
        public static final String TABLE_NAME = "user_credentials";
        public static final String COLUMN_USERNAME = "column_username";
        public static final String COLUMN_PASSWORD = "column_password";
    }

    public static final class TransactionDetailsEntry implements BaseColumns{

        public static final String TABLE_NAME ="transaction_details";
        public static final String NAME ="column_name";
        public static final String DATE ="column_date";
        public static final String AMOUNT ="column_amount";
        public static final String CATEGORY ="column_category";
        public static final String TYPE ="column_type";
    }

    public static final class MyCategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "my_category";
        public static final String COLUMN_CATEGORY_NAME = "category_name";
    }

    /*public static final class IncomeDetailsEntry implements BaseColumns{

        public static final String TABLE_NAME ="income_details";
        public static final String NAME ="column_name";
        public static final String DATE ="column_date";
        public static final String AMOUNT ="column_amount";
        public static final String CATEGORY ="column_category"
    }*/
}
