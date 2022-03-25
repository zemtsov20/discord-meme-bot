package com.rmb.RandMemeBot.Components;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;

import static com.rmb.RandMemeBot.Utils.Patterns.*;

@Component
public class ImgUrlFromLink {

    public String getImgUrl(String url, int mode) {
        String htmlContent = "";
        try {
            htmlContent = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        } catch (MalformedURLException e) {
            System.out.println("Wrong url! " + e.getMessage() + " for url " + url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Matcher urlMatcher;
        if(mode == 0) {
            int random = new Random().nextInt(0, 100);
            List<String> imgs = new ArrayList<>();

            urlMatcher = ETU_IMG_URL.matcher(htmlContent);
            while(urlMatcher.find()) {
                imgs.add("https://etu.ru" + urlMatcher.group(1) + "|" + urlMatcher.group(2));
            }

            if(!imgs.isEmpty())
                return imgs.get(random % imgs.size());
        }
        else {
            urlMatcher = IMG_URL.matcher(htmlContent);
            if(urlMatcher.find())
                return urlMatcher.group(1);
        }

        return "";
    }
}
