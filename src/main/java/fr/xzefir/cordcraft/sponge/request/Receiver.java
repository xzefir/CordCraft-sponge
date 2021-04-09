package fr.xzefir.cordcraft.sponge.request;

import fi.iki.elonen.NanoHTTPD;
import fr.xzefir.cordcraft.sponge.CordCraft;
import fr.xzefir.cordcraft.sponge.DiscordMessage;
import org.spongepowered.api.Sponge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Receiver extends NanoHTTPD {

    public Receiver(String port) throws IOException {
        super(Integer.parseInt(port));
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> parms = session.getParms();

        String msg = GetStringURL(String.format(CordCraft.API_URL + "?action=testConnect&token=%s&guildid=%s", CordCraft.token, CordCraft.guildID));
        String token = parms.get("token");
        String guildID = parms.get("guildID");
        String action = parms.get("action");

        if (parms.get("version") != null)
            msg = CordCraft.VERSION;
        else if (token != null && guildID != null) {
            if (token.equals(CordCraft.token) && guildID.equals(CordCraft.guildID)) {
                if (action.equals("sendMessage")) {
                    String message = parms.get("msg");
                    DiscordMessage.sendMessage(message);
                }
                if (action.equals("infoServ")) {
                    String tps = String.valueOf(Math.round(Sponge.getServer().getTicksPerSecond()));
                    String playerOnline = String.valueOf(Sponge.getServer().getOnlinePlayers().stream().count());
                    String maxplayer = String.valueOf(Sponge.getServer().getMaxPlayers());
                    msg = String.format("%s-%s-%s", tps, playerOnline, maxplayer);
                }
                else if (action.equals("testConnection"))
                    msg = "true";
            }
        }

        return newFixedLengthResponse(msg);
    }

    public static void SimplePingURL(String url) {
        try{
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            in.close();
            con.disconnect();
        }
        catch(IOException ignored){

        }
    }

    public static String GetStringURL(String url) {
        String result = "";
        try{
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            result = String.valueOf(content);
        }
        catch(IOException ignored){

        }
        return result;
    }
}
