package com.omottec.demoapp;

/**
 * Created by qinbingbing on 9/8/16.
 */
public final class Constants {
    private Constants() {}

    public static final String KEY_USER = "key_user";

    public static final String FILE_USERS = "users";

    public static final String KEY_MSG = "key_msg";

    public static final int MSG_FROM_CLIENT = 1;

    public static final int MSG_FROM_SERVER = 2;

    public static final int MSG_BOOK_ADDED = 3;

    public static final int SOCKET_PORT = 7777;
    public static final int MSG_SERVER_CONNECTED = 1;
    public static final int MSG_SERVER_MESSAGE_2_CLIENT = 2;
    public static final int MSG_CLIENT_MESSAGE_2_SERVER = 3;
    public static final String CLIENT_2_SERVER = "c--->s: ";
    public static final String SERVER_2_CLIENT = "s--->c: ";

}
