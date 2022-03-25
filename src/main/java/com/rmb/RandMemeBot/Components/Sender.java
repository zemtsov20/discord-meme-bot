package com.rmb.RandMemeBot.Components;

import com.rmb.RandMemeBot.Bots.Bot;
import com.rmb.RandMemeBot.Utils.MainCommand;
import com.rmb.RandMemeBot.Utils.MemeCommand;
import com.rmb.RandMemeBot.Utils.Patterns;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;

@Component
public class Sender {
    private final EmbedBuilder info = new EmbedBuilder()
            .setColor(new Color(76,30,120))
            .setTitle("Общие сведения")
            .setDescription(  "**Обращайтесь ко мне по имени: **" + Bot.prefix + "\n"
                            + "**Справка:                     **" + MainCommand.help + "\n"
                            + "**Рандомный мем:               **" + MainCommand.meme + "\n"
                            + "**Рандомная картинка:          **" + MainCommand.picture + "\n"
                            + "**Рандомный скрин:             **" + MainCommand.screenshot + "\n"
                            + "**Рандомный анекдот:           **" + MainCommand.joke + "\n"
                            + "*для лэтишников:                *" + MainCommand.etu)
            .setFooter("ВИДЫ МЕМОВ: " + MemeCommand.etuMeme + ", " +
                    MemeCommand.picRandomMeme + ", " +
                    MemeCommand.miraclesRF + ", " +
                    MemeCommand.ssns + ", " +
                    MemeCommand.stetem + ", " +
                    MemeCommand.auf + ", " +
                    MemeCommand.glad + ",\n" +
                                "стандартный (ничего указывать не надо)");

    public void sendImg(String urlStr, MessageReceivedEvent event) {
        int     random = new Random().nextInt(0, Bot.photoSendPhrases.length);
        URL     url;
        try {
            url = new URL(urlStr);
            InputStream is = url.openStream();
            event.getChannel()
                    .sendMessage(Bot.photoSendPhrases[random])
                    .addFile(is, "img." + imgType(urlStr)).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String imgType(String imgUrl) {
        Matcher matcher = Patterns.IMG_TYPE.matcher(imgUrl);
        if(matcher.find())
            return matcher.group(1);
        return "jpg";
    }

    public void sendEtuImg(String urlStr, String name, MessageReceivedEvent event) {
        int     random = new Random().nextInt(0, Bot.teachersSendPhrases.length);
        URL     url;
        try {
            url = new URL(urlStr);
            InputStream is = url.openStream();
            event.getChannel()
                    .sendMessage(Bot.teachersSendPhrases[random] + name)
                    .addFile(is, urlStr.replaceAll(".*\\w[.]", "img.")).queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendInfo(MessageReceivedEvent event) {
        event.getChannel().sendMessageEmbeds(info.build()).queue();
    }

    public void sendInfo(GuildJoinEvent event) {
        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessageEmbeds(info.build()).queue();
    }
}
