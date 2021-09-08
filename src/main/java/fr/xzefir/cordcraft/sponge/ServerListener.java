package fr.xzefir.cordcraft.sponge;

import fr.xzefir.cordcraft.sponge.request.Sender;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class ServerListener {

    @Listener
    public void onMessage(MessageChannelEvent.Chat event, @Root Player p) {

        String channel = event.getChannel().toString();

        if (channel.contains("org.spongepowered.api.text.channel.MessageChannel")) {
            String msg = event.getFormatter().getBody().toText().toPlain();
            String pseudo = p.getName();
            Sender.sendMessageToDiscord(msg, pseudo);
        }
    }

    @Listener
    public void onDeath(DestructEntityEvent.Death event) {
        if (event.getTargetEntity() instanceof Player) {
            if (event.getMessage().toPlain().isEmpty()) return;

            String pseudo = ((Player) event.getTargetEntity()).getName();
            Sender.sendDeathToDiscord(pseudo);
        }
    }

    @Listener
    public void onLogin(ClientConnectionEvent.Join event) {
        String pseudo = event.getTargetEntity().getName();
        Sender.sendLoginToDiscord(pseudo);
    }

    @Listener
    public void onQuit(ClientConnectionEvent.Disconnect event) {
        String pseudo = event.getTargetEntity().getName();
        Sender.sendQuitToDiscord(pseudo);
    }
}
