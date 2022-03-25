package com.rmb.RandMemeBot.Utils;

public enum MainCommand {
    help("инф"),
    meme("мем"),
    picture("пик"),
    screenshot("скрин"),
    etu("кто я из лэти"),
    joke("анек"),
    germanJoke("денаци");
    private final String name;

    MainCommand(String s)  {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
