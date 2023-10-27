package com.leonyk.jda_bot2.commands;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.leonyk.functions.Util.*;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command) {
            case "roles":
                String response = "";
                for (Role role : event.getGuild().getRoles()) {
                    response += role.getAsMention() + "\n";
                }

                event.deferReply().setEphemeral(true).queue();
                event.getHook().sendMessage(response).queue();
                break;
            case "say":
                OptionMapping messageOption = event.getOption("message");
                String message = messageOption.getAsString();
                Boolean sendReply = true;

                MessageChannel channel;
                OptionMapping channelOption = event.getOption("channel");
                if (channelOption != null) {
                    channel = channelOption.getAsChannel().asGuildMessageChannel();

                } else {
                    channel = event.getChannel();
                }

                if (BadLanguageCheck(messageOption.getAsString())) {
                    String msg = "There is a bad word in the sentence, not gonna say it!";
                    event.reply(msg).setEphemeral(true).queue();
                    sendReply = false;
                }

                if (sendReply) {
                    channel.sendMessage(message).queue();
                    event.reply("Your message was send!").setEphemeral(true).queue();
                }
                break;
            case "hug": {
                OptionMapping userOption = event.getOption("user");
                User user = userOption.getAsUser();
                User sender = event.getUser();

                event.reply("Aww, " + sender.getAsMention() + " hugged " + user.getAsMention() + " , what a cute gesture :people_hugging:").queue();
                break;
            }
            case "idea": {
                OptionMapping ideaOption = event.getOption("idea");
                String idea = ideaOption.getAsString();

                User runner = event.getUser();

                addToFile(idea + " (" + runner.getAsTag() + ")", "/home/liam/IdeaProjects/JDA-Bot2/src/main/resources/ideas.txt");

                event.reply("Your idea has been added to a list, i will try to make it possible...").setEphemeral(true).queue();
                break;
            }
        }
    }

    // Guild Command
    public static List<CommandData> commands() {
        List<CommandData> commandData = new ArrayList<>();

        OptionData say_message = new OptionData(OptionType.STRING, "message", "The message you want the bot to say", true);
        OptionData say_channel = new OptionData(OptionType.CHANNEL, "channel", "Sends the message into a specific channel", false).setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);
        OptionData hug_person = new OptionData(OptionType.USER, "user", "The User that you want to express you're emotions to", true);
        OptionData idea = new OptionData(OptionType.STRING, "idea", "The idea", true);

        commandData.add(Commands.slash("roles", "Displays all roles on the server"));
        commandData.add(Commands.slash("say", "Make the bot say a message").addOptions(say_message, say_channel));
        commandData.add(Commands.slash("hug", "hug a person").addOptions(hug_person));
        commandData.add(Commands.slash("idea", "Give us an idea on what to add to the Bot!").addOptions(idea));

        return commandData;
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(commands()).queue();
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        event.getGuild().updateCommands().addCommands(commands()).queue();
    }

    /*
     * // Register server specific commands
     *
     *                                     SERVER_ID
     * if (event.getGuild().getIdLong() == 123456789L {
     *   event.getGuild().updateCommands().addCommands(ListOfSpecialCommands()).queue();
     * }
     */


    // Global Command
    /*
     * @Override
     * public void onReady(@NotNull ReadyEvent event) {
     *    event.getJDA().updateCommands().addCommands(commands()).queue();
     * }
     */
}