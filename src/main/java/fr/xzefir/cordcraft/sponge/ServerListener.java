package fr.xzefir.cordcraft.sponge;

import fr.xzefir.cordcraft.sponge.request.Sender;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.channel.MessageChannel;

public class ServerListener {

    @Listener
    public void onMessage(MessageChannelEvent.Chat event, @Root Player p) {
        String msg = event.getFormatter().getBody().toText().toPlain();
        String pseudo = p.getName();
        String channel = event.getChannel().toString();

        if (channel.contains("org.spongepowered.api.text.channel.MessageChannel"))
            Sender.sendMessageToDiscord(msg, pseudo);
    }

    @Listener
    public void onDeath(DestructEntityEvent.Death event) {
        if (event.getTargetEntity() instanceof Player) {
            if (event.getMessage().toPlain().isEmpty()) return;

            Player p = (Player) event.getTargetEntity();

            String pseudo = p.getName();

            Sender.sendDeathToDiscord(pseudo);
        }
    }

    @Listener
    public void onLogin(ClientConnectionEvent.Join event) {
        Player p = event.getTargetEntity();

        String pseudo = p.getName();

        Sender.sendLoginToDiscord(pseudo);
    }

    @Listener
    public void onQuit(ClientConnectionEvent.Disconnect event) {
        Player p = event.getTargetEntity();

        String pseudo = p.getName();

        Sender.sendQuitToDiscord(pseudo);
    }
}
