package net.koutachan.discordchatsyncbotfabric;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.fabricmc.api.ModInitializer;
import net.koutachan.discordchatsyncbotfabric.discord.DiscordEvent;
import net.koutachan.discordchatsyncbotfabric.util.CommonUtils;
import net.koutachan.discordchatsyncbotfabric.util.ConfigManager;
import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.koutachan.discordchatsyncbotfabric.util.DiscordUtils;

import java.awt.*;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public class DiscordChatSyncBotFabric implements ModInitializer {
    private static final ConfigManager configManager = new ConfigManager("config.properties", Paths.get("", "config.properties").toAbsolutePath());
    private static final JDA jda = JDABuilder.createDefault(configManager.getString("token"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS).addEventListeners(new DiscordEvent()).build();

    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(jda::shutdownNow));

        // TODO: なぜかtellコマンド等もこれで捕獲されて中身だけ渡されるのでどうやって検知して止めるか
        ServerMessageDecoratorEvent.EVENT.register(
                // Used to provide better compatibility across mods
                ServerMessageDecoratorEvent.STYLING_PHASE,
                (sender, message) -> {
                    TextChannel textChannel = DiscordChatSyncBotFabric.getJDA().getTextChannelById(DiscordChatSyncBotFabric.getConfigManager().getString("textChannelId"));

                    if (sender == null){
                        return CompletableFuture.completedFuture(message);
                    }

                    if (sender.getCommandSource() == null){
                        return CompletableFuture.completedFuture(message);
                    }



                    textChannel.sendMessageEmbeds(new EmbedBuilder().setColor(Color.ORANGE)
                            .setDescription("```" + CommonUtils.translateGoogle(message.getString()) + "```")
                            .setThumbnail(DiscordUtils.retrieveThumbnail(sender.getCommandSource().getPlayer()))
                            .setAuthor(sender.getName().getString(), "https://mine.ly/" + sender.getCommandSource().getPlayer().getName().getString())
                            .setTimestamp(Instant.now())
                            .build()).queue();

                    return CompletableFuture.completedFuture(message);
                }
        );

    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static JDA getJDA() {
        return jda;
    }
}