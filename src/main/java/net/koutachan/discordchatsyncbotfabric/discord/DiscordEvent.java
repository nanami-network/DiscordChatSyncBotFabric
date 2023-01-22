package net.koutachan.discordchatsyncbotfabric.discord;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.game.minecraft.launchwrapper.FabricClassTransformer;
import net.fabricmc.loader.impl.game.minecraft.launchwrapper.FabricServerTweaker;
import net.fabricmc.loader.impl.launch.server.FabricServerLauncher;
import net.koutachan.discordchatsyncbotfabric.DiscordChatSyncBotFabric;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.FabricUtil;

public class DiscordEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild() && event.isFromType(ChannelType.TEXT) && !event.getAuthor().isBot() && !event.isWebhookMessage()) {
            TextChannel textChannel = DiscordChatSyncBotFabric.getJDA().getTextChannelById(DiscordChatSyncBotFabric.getConfigManager().getString("textChannelId"));
            if (textChannel.getIdLong() == event.getGuildChannel().asTextChannel().getIdLong()) {
                String message = event.getMessage().getContentRaw();
                if (message.length() > 100) {
                    message = message.substring(0, 100);
                }
                for (ServerPlayerEntity player : ((MinecraftServer) FabricLoader.getInstance().getGameInstance()).getPlayerManager().getPlayerList()) {
                    player.sendMessage(new LiteralText("§b[Discord] §r" + message + " (by " + event.getMember().getEffectiveName() + ")"), false);
                }
            }
        }
    }
}