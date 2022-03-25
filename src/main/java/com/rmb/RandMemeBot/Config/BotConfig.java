package com.rmb.RandMemeBot.Config;

import com.rmb.RandMemeBot.Bots.Bot;
import com.rmb.RandMemeBot.Services.JoinOnServer;
import com.rmb.RandMemeBot.Services.MemeBotListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@RequiredArgsConstructor
@Configuration
public class BotConfig {
    @Value("${discord_access_token}")
    private String token;

    @Autowired
    private MemeBotListener memeBotListener;

    @Autowired
    private JoinOnServer joinOnServer;

    @Bean
    public Bot bot() {
        try {
            JDA jda = JDABuilder.createDefault(token).build();
            jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
            jda.getPresence().setActivity(Activity.watching("в душу"));
            jda.addEventListener(memeBotListener);
            jda.addEventListener(joinOnServer);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        return new Bot();
    }

}
