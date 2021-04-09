package fr.xzefir.cordcraft.sponge;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class DiscordMessage {

    public static void sendMessage(String msg) {
        Text messagetext = TextSerializers.FORMATTING_CODE.deserialize(msg);

        CordCraft.getLogger().info(msg);
        for (Player OnlinePlayer : Sponge.getServer().getOnlinePlayers()){
            OnlinePlayer.sendMessage(messagetext);
        }
    }
}
