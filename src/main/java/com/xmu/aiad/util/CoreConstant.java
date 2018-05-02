package com.xmu.aiad.util;

/**
 * Created by PoemWhite on 2016/11/1.
 */
public class CoreConstant {

    // COOKIE SESSION变量KEY
    public static final String USER_COOKIE = "username";
    public static final String USER_SESSION = "user";


    //JsonResult 常量
    public static final String JSON_CODE_SUCCESS = "200";
    public static final String JSON_CODE_FAILURE = "201";
    public static final String JSON_CODE_UNLOGIN = "202";//用户未登录
    public static final String JSON_CODE_UNAUTH = "401";//无权限

    public static final String JSON_MSG_SUCCESS = "success";
    public static final String JSON_MSG_FAILURE = "error";



    public static class NoticeReadStatus//通知阅读状态
    {
        public static final String NOT_READ="0";
        public static final String HAVE_READ="1";
    }

    public static class NoticeType  //通知类型
    {
        public static final String APPLY_TO_CIRCLE="01";    //有人申请加入艺术圈通知
        public static final String CIRCLE_APPLY_RESULT="02";//艺术圈审核结果通知
        public static final String ADD_CIRCLE="03";        //加入艺术圈通知
        public static final String EXIT_CIRCLE="04";    //有人退出艺术圈通知
        public static final String PROMOTE_TO_CIRCLE_ADMIN="05";//被升为管理员通知
        public static final String REMOVE_CIRCLE_ADMIN="06";    //被降为普通成员通知
        public static final String REMOVE_CIRCLE="07";          //艺术圈被T通知
        public static final String DELETE_CIRCLE_NEWS="08";     //艺术圈动态被管理员删除通知

        public static final String FOCUS_BY_OTHERS="09";        //被人关注通知

        public static final String COMMENT_BY_OTHERS="10";      //评论通知
        public static final String REPLY_BY_OTHER="11";         //动态回复通知

        public static final String REPORT_REPLY_BY_ADMIN="12";  //管理员回复举报

        public static final String COLLECTION_DELETE_BY_ADMIN="13"; //管理员删除作品集
        public static final String CIRCLE_DELETE_BY_ADMIN="14";     //管理员删除艺术圈
        public static final String NEWS_DELETE_BY_ADMIN="15";       //管理员删除动态
        public static final String COMMENT_DELETE_BY_ADMIN="16";     //管理员删除评论

    }


    public static class ReportTargetType
    {
        public static final String USER_REPORT="01";
        public static final String NEWS_REPORT="02";
        public static final String CIRCLE_REPORT="03";
        public static final String COLLECTION_REPORT="04";
        public static final String COMMENT_REPORT="05";
    }

    public static class ReportReadStatus
    {
        public static final String NOT_DEAL="0";
        public static final String HAS_DEAL="1";
    }


    public static class UserAccountStatus
    {
        public static final String LOCKED="1";
        public static final String NORMAL="0";
    }

    public static class Status {
        public static final String DELETE = "0";
        public static final String NORMAL = "1";
    }

    public static class MemberType{
        public static final String CREATER="01";
        public static final String MANAGER="02";
        public static final String NORMAL="03";
    }

    public static class MemberApplyType{
        public static final String APPLYING="01";
        public static final String ADMIT="02";
        public static final String DISAGREE="03";
    }

    public static class NewsAssociateCircleCollectionType
    {
        public static final String collectionNews="01";
        public static final String cicleNews="02";
    }

    public static class NewsShowScope
    {
        public static final String ONLY_MYSELF="03";
        public  static final String ONLY_FOCUS="02";
        public static final String PUBLIC="01";
    }

    public static class UploadTargetDir
    {
        public static final String COLLECTION="collection";
        public static final String NEWS="news";
        public static final String USER="user";
        public static final String CIRCLE="circle";
    }

    /**
     * 正则表达式
     * @author PoemWhite
     *
     */
    public static class REGEX {

        public static final String PHONE = "^[1][0-9]{10}$";//手机号码
        public static final String EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

    }
}

