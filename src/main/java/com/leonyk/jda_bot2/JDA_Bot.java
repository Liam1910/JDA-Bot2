package com.leonyk.jda_bot2;

import com.leonyk.jda_bot2.commands.CommandManager;
import com.leonyk.jda_bot2.commands.CommandManager_AC;
import com.leonyk.jda_bot2.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;


public class JDA_Bot {
    private final ShardManager shardManager;
    private final ShardManager shardManager_admin_commands;
    private final Dotenv config;

    public JDA_Bot() throws LoginException {
        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");
        String token_admin_commands = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        DefaultShardManagerBuilder builder_admin_commands = DefaultShardManagerBuilder.createDefault(token_admin_commands);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("SaikedeLeon on Twitch (twitch.tv/saikedeleon)"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_BANS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);

        builder_admin_commands.setStatus(OnlineStatus.ONLINE);
        builder_admin_commands.setActivity(Activity.watching("SaikedeLeon on Twitch (twitch.tv/saikedeleon)"));
        builder_admin_commands.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_BANS);
        builder_admin_commands.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder_admin_commands.setChunkingFilter(ChunkingFilter.ALL);
        builder_admin_commands.enableCache(CacheFlag.ONLINE_STATUS);

        shardManager = builder.build();
        shardManager.addEventListener(new EventListener());
        shardManager.addEventListener(new CommandManager());

        shardManager_admin_commands = builder_admin_commands.build();
        shardManager_admin_commands.addEventListener(new CommandManager_AC());

    }

    public ShardManager getShardManager() {
        return shardManager;
    }
    public Dotenv getConfig() {
        return config;
    }

    public static void main(String[] args) {
        try {
            JDA_Bot bot = new JDA_Bot();
        } catch (LoginException e) {
            System.err.println("ERROR: Provided bot token is invalid!");
            System.err.println("Exited with error code 1");
            System.exit(1);
        }
    }
}
