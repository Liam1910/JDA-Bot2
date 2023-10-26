package com.leonyk.jda_bot2.listeners;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Role welcomeRole = event.getGuild().getRoleById(1166341224190443590L);
        Role botRole = event.getGuild().getRoleById(1167040088342012015L);

        if (event.getUser().isBot()) {
            event.getGuild().addRoleToMember(event.getUser(), botRole).queue();
        }
        else if (welcomeRole != null) {
            event.getGuild().addRoleToMember(event.getMember(), welcomeRole).queue();
        }

        event.getGuild().getTextChannelsByName("welcome", true).get(0).sendMessage("Welcome to the Server, " + event.getMember().getAsMention() + "!").queue();
    }

    @Override
    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {
        User user = event.getUser();
        String message = "**" + user.getAsTag() + "** updated their status to " + event.getNewOnlineStatus().getKey() + "!";
        event.getGuild().getTextChannelsByName("bot-channel", true).get(0).sendMessage(message).queue();
    }
}

