package com.rmb.RandMemeBot.Components;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class VkImageParser {

    private final List<String> items = new ArrayList<>();
    private final List<Integer> notGot = new ArrayList<>();
    private int groupId;
    private int offset = 0;
    String album = "wall";

    public VkImageParser() {}

    public VkImageParser(int groupId) {
        this.groupId = groupId;
        if (groupId == 29640829)
            this.album = "141420217";
        newImgUrls();
    }

    public String getImage()  {
        if(items.isEmpty())
            newImgUrls();
        int rand = new Random().nextInt(0, 100);
        return items.remove(rand % items.size());
    }

    private void newImgUrls() {
        if(notGot.isEmpty())
            for (int i = 0; i < 6; i++)
                notGot.add(i);
        try {
            var json = new JSONObject(IOUtils.toString(
                    new URL("https://api.vk.com/method/photos.get?v=5.131&" +
                            "access_token=" + VkJokeParser.VK_TOKEN +
                            "&owner_id=-" + groupId + "&album_id=" + album + "&rev=1" + "&offset=" + offset +
                            "&count=50"), StandardCharsets.UTF_8));
            this.offset = notGot.remove(new Random().nextInt(0, notGot.size()));

            JSONArray imgsArr = json.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < imgsArr.length(); i++) {
                JSONArray sizesArr = imgsArr.getJSONObject(i).getJSONArray("sizes");
                for (int j = 0; j < sizesArr.length(); j++) {
                    JSONObject obj = sizesArr.getJSONObject(j);
                    if(obj.get("type").equals("x") || obj.get("type").equals("y")) {
                        items.add(obj.get("url").toString());
                        break;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
