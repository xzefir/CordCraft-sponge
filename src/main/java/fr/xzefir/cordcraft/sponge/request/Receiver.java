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
        Map<String, String> params = session.getParms();

        String msg = getStringURL(String.format(CordCraft.API_URL + "?action=testConnect&token=%s&guildid=%s", CordCraft.token, CordCraft.guildID));
        String token = params.get("token");
        String guildID = params.get("guildID");
        String action = params.get("action");

        if (params.get("version") != null)
            msg = CordCraft.VERSION;
        else if (token != null && guildID != null) {
            if (token.equals(CordCraft.token) && guildID.equals(CordCraft.guildID)) {
                if (action.equals("sendMessage")) {
                    String message = params.get("msg");
                    DiscordMessage.sendMessage(message);
                }
                if (action.equals("infoServ")) {
                    String tps = String.valueOf(Math.round(Sponge.getServer().getTicksPerSecond()));
                    String playerOnline = String.valueOf((long) Sponge.getServer().getOnlinePlayers().size());
                    String maxPlayer = String.valueOf(Sponge.getServer().getMaxPlayers());

                    msg = String.format("%s-%s-%s", tps, playerOnline, maxPlayer);
                }
                else if (action.equals("testConnection"))
                    msg = "true";
            }
        }

        return newFixedLengthResponse(msg);
    }

    public static void simplePingURL(String url) {
        try{
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(2500);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            in.close();
            con.disconnect();
        }
        catch(IOException ignored){

        }
    }

    public static String getStringURL(String url) {
        String result = "";
        try{
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(2500);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
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
