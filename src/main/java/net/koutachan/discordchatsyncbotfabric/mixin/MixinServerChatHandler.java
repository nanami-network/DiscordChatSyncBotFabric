package net.koutachan.discordchatsyncbotfabric.mixin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.koutachan.discordchatsyncbotfabric.DiscordChatSyncBotFabric;
import net.koutachan.discordchatsyncbotfabric.util.CommonUtils;
import net.koutachan.discordchatsyncbotfabric.util.DiscordUtils;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.time.Instant;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerChatHandler {
    @Shadow
    public ServerPlayerEntity player;
    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "onChatMessage", at = @At(value = "HEAD"))
    public void onGameMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        if (packet.getChatMessage().startsWith("/")) return;
        new Thread(() -> {
            TextChannel textChannel = DiscordChatSyncBotFabric.getJDA().getTextChannelById(DiscordChatSyncBotFabric.getConfigManager().getString("textChannelId"));
            if (textChannel != null) {
                textChannel.sendMessageEmbeds(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setDescription("```" + CommonUtils.translateGoogle(packet.getChatMessage()) + "```")
                        .setThumbnail(DiscordUtils.retrieveThumbnail(player))
                        .setAuthor(player.getName().getString(), "https://mine.ly/" + player.getName().getString())
                        .setTimestamp(Instant.now())
                        .build()).queue();
            }
        }).start();
    }

    @Inject(method = "onDisconnected", at = @At(value = "HEAD"))
    public void onDisconnected(Text reason, CallbackInfo ci) {
        final int size = server.getPlayerManager().getCurrentPlayerCount() - 1;
        new Thread(() -> {
            TextChannel textChannel = DiscordChatSyncBotFabric.getJDA().getTextChannelById(DiscordChatSyncBotFabric.getConfigManager().getString("textChannelId"));
            if (textChannel != null) {
                textChannel.sendMessageEmbeds(new EmbedBuilder()
                        .setColor(Color.RED)
                        .setDescription(player.getName().getString() + "さんが退出しました。 (" + size + "人います。)")
                        .setTimestamp(Instant.now())
                        .build()).queue();
                if (DiscordChatSyncBotFabric.getConfigManager().getBoolean("setTopic")) {
                    textChannel.getManager().setTopic(size + " / " + server.getMaxPlayerCount()).queue();
                }
            }
        }).start();
    }
}