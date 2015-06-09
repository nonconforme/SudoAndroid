package com.thinkmobiles.sudo.core;

/**
 * Created by Sasha on 20.11.2014.
 */
public abstract class APIConstants {
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ SERVER URLs ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //public static final String SERVER_URL                     = "http://192.168.88.155:8830";
    /*public static final String SERVER_URL = "http://134.249.164.53:8829";*/
   public static final String SERVER_URL = "http://134.249.164.53:8830";

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ API urls ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-----
    public static final int API_START_MOBILE                    = 1;
    public static final int API_END_MOBILE                      = 2;
    public static final int API_POST                            = 3;
    public static final int API_POST_FAVOURITE                  = 4;
    public static final int API_GET_FAVOURITE                   = 5;
    public static final int API_FAVOURITE_ID                    = 6;


    public static final String PUSH = "/push/channel";

    public static final String URL_START_MOBILE                 = "/startMobile";
    public static final String URL_END_MOBILE                   = "/endMobile";
    public static final String URL_SIGN_UP                      = "/signUp";
    public static final String URL_SIGN_OUT                     = "/signOut";
    public static final String URL_SIGN_IN                      = "/signIn";
    public static final String URL_GET_NUMBERS                  = "/number/search";
    public static final String API_BAY_NUMBER                   = "/buy";
    public static final String ACTION_IS_AUTHENTICATED          = "/isAuthenticated";
    public static final String URL_ADDRESSBOOK                  = "/addressbook";
    public static final String URL_LAST_CHATS                   = "/lastchats";
    public static final String URL_PRICE                        = "/price";
    public static final String API_COUNTRIES                    = "/countries";
    public static final String API_SEARCH                       = "/search";
    public static final String URL_MESSAGE                      = "/message";
    public static final String URL_CHANGE_PASS                  = "/changepass";
    public static final String API_LAST_CONVERSATION            = "/lastConversations";
    public static final String API_CONVERSATION                 = "/conversations";
    public static final String API_SEND                         = "/send";
    public static final String URL_USER                         = "/user";
    public static final String URL_NUMBER                       = "/number";
    public static final String URL_NUMBERS                       = "/numbers";
    public static final String API_BLOCK_NUMBERS                = "/block";
    public static final String VOICE_MESSAGES                      = "/voiceMessages";



    public static final String READ                              = "/read";





    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ DEFAULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static final String SEPARATOR_SLASH                  = "/";
    public static final String SEPARATOR_EQUALS                 = "=";
    public static final String SEPARATOR_APMERSANT              = "&";
    public static final String SEPARATOR_QUESTION               = "?";


    public static final String ERROR_RESULT                     = "error";
    public static final String ERROR_DESCRIPTION                = "error_description";
    public static final String ERROR_CODE                       = "error_code";
    public static final String OK_RESULT                        = "ok";
}
