package com.rmb.RandMemeBot.Bots;

public class Bot implements DiscordBot {
    public static String prefix = "бот ";
    public static String[] teachersSendPhrases = {"Мда.. Ты ", "Поздравляю, ты ",
            "Это очень грустно, но ты ", "Мне искренне жаль, ты ", "Да это же легенда! Ты ", "Нет слов.. Ты "};
    public static String[] photoSendPhrases = {"Забирай", "На", "Держи", "На здоровье", "Вот"};
    public static String[] unknownCommandPhrases = {"?", "Че?", "Не понял", "Ты мне?", "А?", "М?"};
    public static String[] etuLinks = {
            "https://etu.ru/ru/universitet/administraciya/",
            "https://etu.ru/ru/fakultety/fkti/sostav/kafedra-cse/rukovodstvo-sostav-kafedry/",
            "https://etu.ru/ru/fakultety/fkti/sostav/kafedra-cs/rukovodstvo-sostav-kafedry",
            "https://etu.ru/ru/fakultety/fkti/sostav/kafedra-is/rukovodstvo-sostav-kafedry"
    };

}
