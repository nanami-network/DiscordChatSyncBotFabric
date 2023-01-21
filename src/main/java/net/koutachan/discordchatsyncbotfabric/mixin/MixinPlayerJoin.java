package net.koutachan.discordchatsyncbotfabric.mixin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.koutachan.discordchatsyncbotfabric.DiscordChatSyncBotFabric;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.time.Instant;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerJoin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow public abstract int getCurrentPlayerCount();

    @Inject(method = "onPlayerConnect", at = @At(value = "HEAD"))
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        final int size = getCurrentPlayerCount() + 1;
        new Thread(() -> {
            TextChannel textChannel = DiscordChatSyncBotFabric.getJDA().getTextChannelById(DiscordChatSyncBotFabric.getConfigManager().getString("textChannelId"));
            if (textChannel != null) {
                textChannel.sendMessageEmbeds(new EmbedBuilder()
                        .setColor(Color.GREEN)
                        .setDescription(player.getName().getString() + "さんが入室しました。 (" + size + "人います。)")
                        .setTimestamp(Instant.now())
                        .build()).queue();
                if (DiscordChatSyncBotFabric.getConfigManager().getBoolean("setTopic")) {
                    textChannel.getManager().setTopic(size + " / " + server.getMaxPlayerCount()).queue();
                }
            }
        }).start();
    }
}