package com.rmb.RandMemeBot.Utils;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern IMG_URL
            = Pattern.compile("twitter:image:src.*(https.*(jpeg|jpg|png))",
            Pattern.CASE_INSENSITIVE);
    public static final Pattern ETU_IMG_URL
            = Pattern.compile("img src=\"(/.*\\.jpg)\".*\\n.*\\n.*\">(.*<)",
            Pattern.CASE_INSENSITIVE);
    public static final Pattern IMG_TYPE
            = Pattern.compile("(jpeg|jpg|png)");
    public static final Pattern JOKE
            = Pattern.compile("/>-(.*?)<br");
}
