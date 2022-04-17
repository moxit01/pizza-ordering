package com.example.pizzaordering;

import android.provider.BaseColumns;

public class OrderDetailContract {

    public static final class OrderDetailEntry implements BaseColumns {
        public static final String TABLE_NAME = "orderdetail";
        public static final String COLUMN_TOPPING_NAME = "toppingname";
        public static final String COLUMN_TOPPING_PRICE = "toppingprice";
        public static final String COLUMN_ORDER_ID = "orderid";
        public static final String COLUMN_TOTAL = "partySize";
    }
}