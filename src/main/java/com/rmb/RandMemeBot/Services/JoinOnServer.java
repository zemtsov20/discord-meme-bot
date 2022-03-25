package com.rmb.RandMemeBot.Services;

import com.rmb.RandMemeBot.Components.Sender;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinOnServer extends ListenerAdapter {
    @Autowired
    private Sender sender;

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        sender.sendInfo(event);
    }
}
