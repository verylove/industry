package com.fable.industry.api.utils;

/**
 * 通用常量
 * Created by Wolf.J on 2015-10-16.
 */
public interface Constants {
    //通用Bool  int/str值
    int INT_TRUE = 1;
    int INT_FALSE = 0;
    String STR_TRUE = "1";
    String STR_FALSE = "0";

    //通用树的根PID-这是个虚ID.
    String ROOT_PID="-1";
    //通用树的根ID
    String ROOT_ID="0";

    //组织管理基础类型
    String ORG_MNG_TYPE_ORG = "1";
    String ORG_MNG_TYPE_DEP = "2";
    String ORG_MNG_TYPE_POST = "3";
    String ORG_MNG_TYPE_USER = "4";

    /**
     * 用户类型
     *
     * @author nimj
     */
    public enum UserType {
        BusinessUser("1", "业务用户"), SystemUser("2", "系统用户");

        public String code;
        public String text;

        private UserType(String code, String text) {
            this.code = code;
            this.text = text;
        }
    }
}
