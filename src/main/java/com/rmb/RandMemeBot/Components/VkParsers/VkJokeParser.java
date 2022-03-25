package com.rmb.RandMemeBot.Components.VkParsers;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Component
public class VkJokeParser extends VkParser {
    static String VK_TOKEN;

    public VkJokeParser(@Value("${vk_access_token}") String token) {
        VK_TOKEN = token;
        this.offset = 50;
        newItem();
    }

    private void newItem() {
        if(notGot.isEmpty())
            for (int i = 2; i < 20; i++)
                notGot.add(i);
        try {
            var json = new JSONObject(IOUtils.toString(
                    new URL("https://api.vk.com/method/wall.get?v=5.131&" +
                            "access_token=" + VK_TOKEN + "&domain=" + "anekdotikategoriib" +
                            "&offset=" + offset + "&count=50"), StandardCharsets.UTF_8));
            this.offset = notGot.remove(new Random().nextInt(0, notGot.size())) * 50;
            JSONArray postsArr = json.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < postsArr.length(); i++) {
                JSONObject obj = postsArr.getJSONObject(i);
                if(obj.get("text").toString().length() < 200 && !obj.get("text").toString().contains("/"))
                    items.add(obj.get("text").toString());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
