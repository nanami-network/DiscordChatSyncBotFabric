package net.koutachan.discordchatsyncbotfabric.discord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fabricmc.loader.api.FabricLoader;
import net.koutachan.discordchatsyncbotfabric.DiscordChatSyncBotFabric;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class DiscordEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild() && event.isFromType(ChannelType.TEXT) && !event.getAuthor().isBot() && !event.isWebhookMessage()) {
            TextChannel textChannel = DiscordChatSyncBotFabric.getJDA().getTextChannelById(DiscordChatSyncBotFabric.getConfigManager().getString("textChannelId"));
            if (textChannel.getIdLong() == event.getGuildChannel().asTextChannel().getIdLong()) {
                StringBuilder message = new StringBuilder(event.getMessage().getContentRaw());
                if (message.length() > 100) {
                    message = new StringBuilder(message.substring(0, 100));
                }

                if (event.getMessage().getAttachments().size() > 0){
                    for (Message.Attachment attachment : event.getMessage().getAttachments()){
                        message.append("\n");
                        message.append(attachment.getProxyUrl());
                    }
                }

                for (ServerPlayerEntity player : ((MinecraftServer) FabricLoader.getInstance().getGameInstance()).getPlayerManager().getPlayerList()) {
                    player.sendMessage(new LiteralText("§b[Discord] §r" + message + " (by " + event.getMember().getEffectiveName() + ")"), false);
                }
            }
        }
    }
}