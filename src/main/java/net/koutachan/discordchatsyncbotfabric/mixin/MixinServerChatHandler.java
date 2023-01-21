package net.koutachan.discordchatsyncbotfabric.mixin;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerChatHandler {
    @Shadow public ServerPlayerEntity player;
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "onGameMessage", at = @At(value = "HEAD"))
    public void onGameMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        LOGGER.info(player.getName().asString() + ": " + packet.getChatMessage());
    }
}