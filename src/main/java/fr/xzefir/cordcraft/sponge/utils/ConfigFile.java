package fr.xzefir.cordcraft.sponge.utils;

import fr.xzefir.cordcraft.sponge.CordCraft;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigFile {

    public static boolean ConfigCreate(String fileEmplacement, File dataFile) {
        if(!dataFile.isFile()) {
            try {
                dataFile.createNewFile();

                FileWriter dataFileWrite = new FileWriter(fileEmplacement);
                dataFileWrite.write("{\"fileVersion\":1}");
                dataFileWrite.flush();

            } catch (IOException ignored) {
                return false;
            }
        }
        return true;
    }

    public static boolean ConfigUpdate(String fileEmplacement) {
        String dataJSON = "";

        try {
            dataJSON = new String(Files.readAllBytes(Paths.get(fileEmplacement)));
        }
        catch (IOException e) {
            return false;
        }

        JSONObject dataParse = new JSONObject(dataJSON);

        if(dataParse.getInt("fileVersion") != CordCraft.versionDataFile) {
            dataParse.put("fileVersion", CordCraft.versionDataFile);

            String[] valueName = {"token", "guildID", "plugin_port", "enable"};
            String[] valueDefault = {GenerateToken.createToken(), "", "", "true"};
            for( int i = 0; i <= valueName.length - 1; i++)
            {
                try {
                    dataParse.getString(valueName[i]);
                } catch (Exception e) {
                    dataParse.put(valueName[i], valueDefault[i]);
                }
            }
        }

        try (FileWriter file = new FileWriter(fileEmplacement)) {

            file.write(dataParse.toString());
            file.flush();

        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static boolean ConfigSetValueFromFile(String fileEmplacement) {

        String dataJSON = "";

        try {
            dataJSON = new String(Files.readAllBytes(Paths.get(fileEmplacement)));
        }
        catch (IOException e) {
            return false;
        }

        JSONObject dataParse = new JSONObject(dataJSON);

        CordCraft.port = dataParse.getString("plugin_port");
        CordCraft.token = dataParse.getString("token");
        CordCraft.guildID = dataParse.getString("guildID");
        CordCraft.enable = dataParse.getString("enable");

        return true;
    }
}
