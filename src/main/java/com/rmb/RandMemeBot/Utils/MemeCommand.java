package com.rmb.RandMemeBot.Utils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum MemeCommand {
    etuMeme("электро", 1),
    miraclesRF("чуд",2),
    picRandomMeme("ранд",3),
    ssns("сснс",4),
    auf("ауф",5),
    glad("глад",6),
    stetem("стетем",7);
    private final String name;
    private final int value;

    MemeCommand(String s, int v)  {
        name = s;
        this.value = v;
    }

    public static int getIfPresent(String str) {
        var memeCommand = Arrays.stream(values())
                .filter(m -> str.contains(m.name))
                .findFirst();
        return memeCommand.map(command -> command.value).orElse(0);
    }

    public int getValue() {return this.value;}

    public String toString() {
        return this.name;
    }
}
