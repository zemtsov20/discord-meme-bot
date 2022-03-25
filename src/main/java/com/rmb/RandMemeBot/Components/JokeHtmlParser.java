package com.rmb.RandMemeBot.Components;

import com.rmb.RandMemeBot.Utils.Patterns;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JokeHtmlParser {
    List<String> jokesList = new ArrayList<>();
    String[] links = {
            "https://navlasov.livejournal.com/224484.html",
            "https://navlasov.livejournal.com/224520.html",
            "https://navlasov.livejournal.com/224930.html"};

    public JokeHtmlParser() {
        newJokes();
    }

    public String getJoke() {
        return jokesList.remove(new Random().nextInt(0, jokesList.size()));
    }

    private void newJokes() {
        for (String link : links) {
            getJokesFrom(getHtml(link));
        }
    }

    private void getJokesFrom(String html) {
        Matcher matcher = Patterns.JOKE.matcher(html);
        while(matcher.find())
            jokesList.add(matcher.group(1));
    }

    private String getHtml(String url) {
        String htmlContent = "";
        try {
            htmlContent = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        } catch (MalformedURLException e) {
            System.out.println("Wrong url! " + e.getMessage() + " for url " + url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return htmlContent;
    }
}
