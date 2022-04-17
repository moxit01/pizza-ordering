package com.example.pizzaordering;

import android.provider.BaseColumns;

public class OrderContract {

    public static final class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_DATE_TIME = "guestName";
        public static final String COLUMN_TOTAL = "partySize";


    }
}