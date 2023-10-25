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
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();

        String message = user.getAsTag() + " reacted to a message with " + emoji + " in the " + channelMention + " channel!";
        event.getGuild().getTextChannelsByName("bot-channel", true).get(0).sendMessage(message).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.equals("ping")) {
            event.getChannel().sendMessage("pong").queue();
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Role role = event.getGuild().getRoleById(1166341224190443590L);
        if (role != null) {
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
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

