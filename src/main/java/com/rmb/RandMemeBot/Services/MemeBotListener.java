package com.rmb.RandMemeBot.Services;

import com.rmb.RandMemeBot.Bots.Bot;
import com.rmb.RandMemeBot.Components.EtuImgUrlFromLink;
import com.rmb.RandMemeBot.Components.ImgUrlFromLink;
import com.rmb.RandMemeBot.Components.JokeHtmlParser;
import com.rmb.RandMemeBot.Components.Sender;
import com.rmb.RandMemeBot.Components.VkParsers.VkImageParser;
import com.rmb.RandMemeBot.Components.VkParsers.VkJokeParser;
import com.rmb.RandMemeBot.Utils.MainCommand;
import com.rmb.RandMemeBot.Utils.MemeCommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class MemeBotListener extends ListenerAdapter {

    private final VkImageParser[] vkImageParsers = new VkImageParser[8];

    private static final int[] GROUP_IDS = {
            29606875,    // 0 - kingdom
            160814627,   // 1 - electromem
            155153648,   // 2 - chudesa_rf
            109290951,   // 3 - picrandom
            29640829,    // 4 - ssns
            39399586,    // 5 - auf
            148503450,   // 6 - misli borova
            70715027     // 7 - stetem
    };

    @Autowired
    private VkJokeParser jokeParser;

    @Autowired
    private JokeHtmlParser jokeHtmlParser;

    @Autowired
    private Sender sender;

    @Autowired
    private ImgUrlFromLink imgUrlFromLink;

    @Autowired
    private EtuImgUrlFromLink etuImgUrlFromLink;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("I am ready to go!");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay().toLowerCase(Locale.ROOT);
        if(!message.contains(Bot.prefix))
            return;

        if(message.contains(MainCommand.help.toString())) {
            sender.sendInfo(event);
        }

        else if(message.contains(MainCommand.meme.toString())) {
            int memeCommandId = MemeCommand.getIfPresent(message);
            if(memeCommandId == 4 && !event.getGuild().getId().equals("360849306648969236")) {
                event.getChannel().sendMessage("Вы не из ССНС(").queue();
                return;
            }
            if(vkImageParsers[memeCommandId] == null)
                vkImageParsers[memeCommandId] = new VkImageParser(GROUP_IDS[memeCommandId]);
            sender.sendImg(vkImageParsers[memeCommandId].getItem(), event);
        }

        else if (message.contains(MainCommand.germanJoke.toString())) {
            String jokeStr = jokeHtmlParser.getJoke();

            EmbedBuilder joke = new EmbedBuilder().setTitle("Антифашистский анектод #" + RandomStringUtils.random(2, false, true))
                    .setDescription(jokeStr);
            event.getChannel().sendMessageEmbeds(joke.build()).queue();
            int time = jokeStr.length() / 7;
            event.getChannel().sendMessage(jokeStr).tts(true).queue(
                    message1 -> message1.delete().queueAfter(time, TimeUnit.SECONDS));
        }

        else if (message.contains(MainCommand.picture.toString())) {
            int random = new Random().nextInt(0,100);
            String urlStr = "https://picsum.photos/" + (random % 5 + 5) + "00/" + (random % 3 + 6) + "00.jpg";
            sender.sendImg(urlStr, event);
        }

        else if(message.contains(MainCommand.screenshot.toString())) {
            String url = imgUrlFromLink.getImgUrl("https://prnt.sc/" + RandomStringUtils.random(6, false, true));

            if(url.isEmpty()) {
                event.getChannel().sendMessage("Не повезло(\nРандомный скрин удален").queue();
                return;
            }

            sender.sendImg(url, event);
        }

        else if(message.contains(MainCommand.etu.toString())) {
            if(!event.getGuild().getId().equals("542358750086955031")) {
                event.getChannel().sendMessage("Ты не из ЛЭТИ").queue();
                return;
            }
            String url = etuImgUrlFromLink.getImgUrl();
            if(url.isEmpty()) {
                event.getChannel().sendMessage("Ты не из ЛЭТИ\nЛОВИТЕ ИТМОШНИКА").queue();
                return;
            }
            int idxOf = url.indexOf('<') == -1 ? url.length() : url.indexOf('<');
            sender.sendEtuImg(url.substring(0,url.indexOf('|')), url.substring(url.indexOf('|') + 1, idxOf), event);
        }

        else if(message.contains(MainCommand.joke.toString())) {
            String jokeStr = jokeParser.getItem();

            EmbedBuilder joke = new EmbedBuilder().setTitle("Анекдот #" + RandomStringUtils.random(4, false, true))
                    .setDescription(jokeStr);
            event.getChannel().sendMessageEmbeds(joke.build()).queue();
            int time = jokeStr.length() / 7;
            event.getChannel().sendMessage(jokeStr).tts(true).queue(
                    message1 -> message1.delete().queueAfter(time, TimeUnit.SECONDS));
        }

        else {
            int random = new Random().nextInt(Bot.unknownCommandPhrases.length);
            event.getChannel().sendMessage(Bot.unknownCommandPhrases[random]).queue();
        }

    }
}
