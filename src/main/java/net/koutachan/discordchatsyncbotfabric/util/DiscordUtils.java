package net.koutachan.discordchatsyncbotfabric.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.properties.Property;
import net.koutachan.discordchatsyncbotfabric.DiscordChatSyncBotFabric;
import net.minecraft.server.network.ServerPlayerEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DiscordUtils {
    public static String retrieveThumbnail(ServerPlayerEntity player) {
        final boolean localOptions = DiscordChatSyncBotFabric.getConfigManager().getBoolean("useLocalOptions");
        return localOptions ? "https://skin.7mi.site/?url=" + getTextureUrl(player) : "https://cravatar.eu/avatar/" + player.getName() + "/64.png";
    }

    public static String getTextureUrl(ServerPlayerEntity player) {
        Property textures = player.getGameProfile().getProperties().get("textures").iterator().next();
        JsonObject object = new Gson().fromJson(new String(Base64.getDecoder().decode(textures.getValue()), StandardCharsets.UTF_8), JsonObject.class);
        return object.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
    }
}