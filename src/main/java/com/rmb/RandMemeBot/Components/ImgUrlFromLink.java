package com.rmb.RandMemeBot.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import static com.rmb.RandMemeBot.Utils.Patterns.ETU_IMG_URL;
import static com.rmb.RandMemeBot.Utils.Patterns.IMG_URL;

@Component
public class ImgUrlFromLink {
    @Autowired
    private GetHtmlFromUrl getHtmlFromUrl;

    public String getImgUrl(String url) {
        String htmlContent = getHtmlFromUrl.getHtml(url);

        Matcher urlMatcher = IMG_URL.matcher(htmlContent);
        if(urlMatcher.find())
            return urlMatcher.group(1);

        return "";
    }
}
