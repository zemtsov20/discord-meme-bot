package com.rmb.RandMemeBot.Components.VkParsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class VkParser {
    protected final List<String> items = new ArrayList<>();
    protected final List<Integer> notGot = new ArrayList<>();
    protected int offset;

    public String getItem() {
        if(items.isEmpty())
            newItem();
        int rand = new Random().nextInt(0, 100);
        return items.remove(rand % items.size());
    }

    private void newItem() { }
}
