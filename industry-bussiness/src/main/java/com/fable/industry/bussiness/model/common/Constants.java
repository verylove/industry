package com.fable.industry.bussiness.model.common;

/**
 * 通用常量
 * add by duyang 2018 1 26
 */
public interface Constants {

    interface USER {
        /**
         * 用户Session
         */
        String USERSESSION = "userInfo";
    }

    interface LOG_ADDRESS {
        String LOGINADDRESS = "/index/main";
        //OID 地址
        String OIDADDRESS = "todo/todo";
    }

    interface ISSUCCESS {
        String SUCCESS = "1";
        String ERROR = "2";
    }

    interface ISORNOT {
        String YES = "1";
        String NO = "2";
    }



}
