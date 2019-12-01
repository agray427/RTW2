package com.agray427.rewardsthatwork.utils;

import java.util.Random;

public final class InviteCodeUtil {
    private static final int LENGTH = 6;
    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private static final Random r = new Random();

    public static String generate(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int ic = r.nextInt(CHAR_SET.length());
            builder.append(CHAR_SET.charAt(ic));
        }
        return builder.toString();
    }
}
