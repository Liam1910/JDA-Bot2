package com.leonyk.functions;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<String> badWords() {
        List<String> bad = new ArrayList<>();

        bad.add("negga");
        bad.add("nigger");
        bad.add("nigga");
        bad.add("neggar");
        bad.add("asshole");
        bad.add("arschloch");
        //bad.add("");

        return bad;
    }


    public static Boolean BadLanguageCheck(@NotNull String message) {
        String check = message.toLowerCase();

        for (String word : badWords()) {
            if (check.contains(word)) {
                return true;
            }
        }
        return false;
    }


    public static void addRole(SlashCommandInteractionEvent event, User user, Role role) {
        event.getGuild().addRoleToMember(user, role).queue();
        event.reply("The Role " + role.getAsMention() + " has been added to the User " + user.getAsMention() + "!").setEphemeral(true).queue();
    }

    public static void removeRole(SlashCommandInteractionEvent event, User user, Role role) {
        event.getGuild().removeRoleFromMember(user, role).queue();
        event.reply("The Role " + role.getAsMention() + " has been removed from the User " + user.getAsMention() + "!").setEphemeral(true).queue();
    }
}
