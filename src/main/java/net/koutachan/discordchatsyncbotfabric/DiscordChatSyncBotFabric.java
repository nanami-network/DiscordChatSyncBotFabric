package net.koutachan.discordchatsyncbotfabric;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.fabricmc.api.ModInitializer;
import net.koutachan.discordchatsyncbotfabric.util.ConfigManager;

import java.nio.file.Paths;

public class DiscordChatSyncBotFabric implements ModInitializer {
    private static final ConfigManager configManager = new ConfigManager("config.properties", Paths.get("", "config.properties").toAbsolutePath());
    private static final JDA jda = JDABuilder.createDefault(configManager.getString("token"), GatewayIntent.GUILD_MESSAGES).build();;

    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(jda::shutdown));
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static JDA getJDA() {
        return jda;
    }
}
