package com.cheesuscrust.Product;

import android.provider.BaseColumns;

public class UsersMaster {
    private UsersMaster(){}


    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_P_ID = "p_id";
        public static final String COLUMN_NAME_P_NAME = "p_name";
        public static final String COLUMN_NAME_P_DESCRIPTION = "p_description";
        public static final String COLUMN_NAME_P_S_price = "p_s_price";
        public static final String COLUMN_NAME_P_M_price = "p_m_price";
        public static final String COLUMN_NAME_P_L_price = "p_l_price";
        public static final String COLUMN_NAME_P_TYPE = "p_type";
        public static final String COLUMN_NAME_P_IMG = "p_img";



    }
}
