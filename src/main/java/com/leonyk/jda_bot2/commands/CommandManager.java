package com.leonyk.jda_bot2.commands;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
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

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("welcome")) {
            String userTag = event.getUser().getAsTag();
            event.reply("Welcome to the Server, **" + userTag + "**!").setEphemeral(true).queue();
        }
        else if (command.equals("roles")) {
            String response = "";
            for (Role role : event.getGuild().getRoles()) {
                response += role.getAsMention() + "\n";
            }

            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage(response).queue();
        }
        else if (command.equals("say")) {
            OptionMapping messageOption = event.getOption("message");
            String message = messageOption.getAsString();

            MessageChannel channel;
            OptionMapping channelOption = event.getOption("channel");
            if (channelOption != null) {
                channel = channelOption.getAsChannel().asGuildMessageChannel();

            } else {
                channel = event.getChannel();
            }

            channel.sendMessage(message).queue();
            event.reply("Your message was send!").setEphemeral(true).queue();
        }
        else if (command.equals("hug")) {
            OptionMapping userOption = event.getOption("user");
            User user = userOption.getAsUser();
            User sender = event.getUser();

            event.reply("Awww, " + sender.getAsMention() + " hugged " + user.getAsMention() + " , what a cute gesture :people_hugging:").queue();
        }
    }

    // Guild Command
    public static List<CommandData> commands() {
        List<CommandData> commandData = new ArrayList<>();

        OptionData say_message = new OptionData(OptionType.STRING, "message", "The message you want the bot to say", true);
        OptionData say_channel = new OptionData(OptionType.CHANNEL, "channel", "Sends the message into a specific channel", false).setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);
        OptionData hug_person = new OptionData(OptionType.USER, "user", "The person that you want to express youre emotions to", true);

        commandData.add(Commands.slash("welcome", "Get welcomed by the Bot"));
        commandData.add(Commands.slash("roles", "Displays all roles on the server"));
        commandData.add(Commands.slash("say", "Make the bot say a message").addOptions(say_message, say_channel));
        commandData.add(Commands.slash("hug", "hug a person").addOptions(hug_person));


        return commandData;
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(commands()).queue();
    }

    //@Override
    //public void onGuildJoin(@NotNull GuildJoinEvent event) {
    //    event.getGuild().updateCommands().addCommands(commands()).queue();
    //}

    /*
     * // Register server specific commands
     *
     *                                     SERVER_ID
     * if (event.getGuild().getIdLong() == 1234567890L {
     *   event.getGuild().updateCommands().addCommands(specialCommands()).queue();
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