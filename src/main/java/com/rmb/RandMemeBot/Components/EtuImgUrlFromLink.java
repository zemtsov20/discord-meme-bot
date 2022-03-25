package com.rmb.RandMemeBot.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import static com.rmb.RandMemeBot.Utils.Patterns.ETU_IMG_URL;

@Component
public class EtuImgUrlFromLink {

    private final List<String> imgs = new ArrayList<>();

    @Autowired
    private GetHtmlFromUrl getHtmlFromUrl;

    private final String[] etuLinks = {
            "https://etu.ru/ru/universitet/administraciya/",
            "https://etu.ru/ru/fakultety/fkti/sostav/kafedra-cse/rukovodstvo-sostav-kafedry/",
            "https://etu.ru/ru/fakultety/fkti/sostav/kafedra-cs/rukovodstvo-sostav-kafedry",
            "https://etu.ru/ru/fakultety/fkti/sostav/kafedra-is/rukovodstvo-sostav-kafedry"
    };

    public String getImgUrl() {
        if(imgs.isEmpty())
            newUrls();
        if(!imgs.isEmpty())
            return imgs.remove(new Random().nextInt(0, imgs.size()));

        return "";
    }

    private void newUrls() {
        Matcher urlMatcher = ETU_IMG_URL.matcher(getHtmlFromUrl
                .getHtml(etuLinks[new Random().nextInt(0, etuLinks.length)]));
        while(urlMatcher.find()) {
            imgs.add("https://etu.ru" + urlMatcher.group(1) + "|" + urlMatcher.group(2));
        }
    }
}
