package com.leonyk.jda_bot2;

import com.leonyk.jda_bot2.commands.CommandManager;
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
    private final Dotenv config;

    public JDA_Bot() throws LoginException {
        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("SaikedeLeon on Twitch (twitch.tv/saikedeleon)"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_BANS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);

        shardManager = builder.build();
        shardManager.addEventListener(new EventListener());
        shardManager.addEventListener(new CommandManager());
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
