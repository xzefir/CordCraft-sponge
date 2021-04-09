package fr.xzefir.cordcraft.sponge.request;

import fr.xzefir.cordcraft.sponge.CordCraft;

import java.net.URLEncoder;

public class Sender {

    public static void sendMessageToDiscord(String msg, String pseudo) {
        if (!CordCraft.token.equals("") && !CordCraft.guildID.equals("")) {
            Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=sendChat&token=%s&guildid=%s&pseudo=%s&msg=%s", CordCraft.token, CordCraft.guildID, pseudo, URLEncoder.encode(msg))));
            t.start();
        }
    }

    public static void sendDeathToDiscord(String pseudo) {
        if (!CordCraft.token.equals("") && !CordCraft.guildID.equals("")) {
            Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=playerAction&token=%s&guildid=%s&pseudo=%s&playerAction=death", CordCraft.token, CordCraft.guildID, pseudo)));
            t.start();
        }
    }

    public static void sendLoginToDiscord(String pseudo) {
        if (!CordCraft.token.equals("") && !CordCraft.guildID.equals("")) {
            Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=playerAction&token=%s&guildid=%s&pseudo=%s&playerAction=login", CordCraft.token, CordCraft.guildID, pseudo)));
            t.start();
        }
    }

    public static void sendQuitToDiscord(String pseudo) {
        if (!CordCraft.token.equals("") && !CordCraft.guildID.equals("")) {
            Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=playerAction&token=%s&guildid=%s&pseudo=%s&playerAction=quit", CordCraft.token, CordCraft.guildID, pseudo)));
            t.start();
        }
    }

    public static void sendServerStopToDiscord() {
        if (!CordCraft.token.equals("") && !CordCraft.guildID.equals("")) {
            Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=serverAction&token=%s&guildid=%s&serverAction=stop", CordCraft.token, CordCraft.guildID)));
            t.start();
        }
    }

    public static void sendServerStartToDiscord() {
        if (!CordCraft.token.equals("") && !CordCraft.guildID.equals("")) {
            Thread t = new Thread(() -> Receiver.SimplePingURL(String.format(CordCraft.API_URL + "?action=serverAction&token=%s&guildid=%s&serverAction=start", CordCraft.token, CordCraft.guildID)));
            t.start();
        }
    }
}
