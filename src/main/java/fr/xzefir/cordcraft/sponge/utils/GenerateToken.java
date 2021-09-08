package fr.xzefir.cordcraft.sponge.utils;

import fr.xzefir.cordcraft.sponge.request.Receiver;
import org.json.JSONObject;


public class GenerateToken {

    public static String createToken() {

        String apiUrl = "https://api.motdepasse.xyz/create/?include_digits&include_lowercase&include_uppercase&password_length=42&quantity=1";
        String json = Receiver.getStringURL(apiUrl);

        JSONObject dataParse = new JSONObject(json);

        return dataParse.getJSONArray("passwords").getString(0);
    }
}
