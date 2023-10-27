package com.leonyk.jda_bot2.commands;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
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

import static com.leonyk.functions.Util.addRole;
import static com.leonyk.functions.Util.removeRole;

public class CommandManager_AC extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        switch (command) {
            case "add_role": {
                OptionMapping roleOption = event.getOption("role");
                OptionMapping userOption = event.getOption("role_user");

                Role role = roleOption.getAsRole();
                User user = userOption.getAsUser();
                Member runner = event.getMember();

                Role admin = event.getGuild().getRoleById(1167031036618756116L);
                Role owner = event.getGuild().getRoleById(1167031551935139850L);

                if (role.equals(owner)) {
                    if (runner.getRoles().contains(owner)) {
                        addRole(event, user, role);
                    } else {
                        event.reply("The Role " + owner.getAsMention() + " cant be given to User " + user.getAsMention() + " because you (" + runner.getAsMention() + ") do not have the Owner Role/Permissions!").setEphemeral(true).queue();
                    }
                } else if (runner.getRoles().contains(admin)) {
                    addRole(event, user, role);
                } else if (runner.getRoles().contains(owner)) {
                    addRole(event, user, role);
                } else {
                    event.reply("You have to be at least an Administrator ( " + admin.getAsMention() + ") to run this command").setEphemeral(true).queue();
                }
                break;
            }
            case "remove_role": {
                OptionMapping roleOption = event.getOption("remove_role");
                OptionMapping userOption = event.getOption("remove_role_user");

                Role role = roleOption.getAsRole();
                User user = userOption.getAsUser();
                Member runner = event.getMember();

                Role admin = event.getGuild().getRoleById(1167031036618756116L);
                Role owner = event.getGuild().getRoleById(1167031551935139850L);

                if (role.equals(owner)) {
                    if (runner.getRoles().contains(owner)) {
                        removeRole(event, user, role);
                    } else {
                        event.reply("The Role " + owner.getAsMention() + " cant be removed from User " + user.getAsMention() + " because you (" + runner.getAsMention() + ") do not have the Owner Role/Permissions!").setEphemeral(true).queue();
                    }
                } else if (runner.getRoles().contains(admin)) {
                    removeRole(event, user, role);
                } else if (runner.getRoles().contains(owner)) {
                    removeRole(event, user, role);
                } else {
                    event.reply("You have to be at least an Administrator ( " + admin.getAsMention() + ") to run this command").setEphemeral(true).queue();
                }
                break;
            }
        }
    }

    public static List<CommandData> commands() {
        List<CommandData> commandData = new ArrayList<>();

        OptionData add_role_user = new OptionData(OptionType.USER, "role_user", "The User that gets the Role", true);
        OptionData add_role_role = new OptionData(OptionType.ROLE, "role", "The Role that the User gets", true);
        OptionData remove_role_user = new OptionData(OptionType.USER, "remove_role_user", "The User from where he Role should be taken", true);
        OptionData remove_role_role = new OptionData(OptionType.ROLE, "remove_role", "The Role that should be taken from the User", true);

        commandData.add(Commands.slash("add_role", "Ads a role to a user").addOptions(add_role_role, add_role_user));
        commandData.add(Commands.slash("remove_role", "Removes a role from a user").addOptions(remove_role_role, remove_role_user));

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
