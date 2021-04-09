package fr.xzefir.cordcraft.sponge;

import com.google.inject.Inject;
import fr.xzefir.cordcraft.sponge.request.Receiver;
import fr.xzefir.cordcraft.sponge.request.Sender;
import fr.xzefir.cordcraft.sponge.utils.ConfigFile;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.nio.file.Path;

@Plugin(
        id = "cordcraft",
        name = "CordCraft",
        version = CordCraft.VERSION,
        description = "Official CordCraft Discord bot plugin, by xzefir.",
        authors = {
                "xzefir"
        }
)
public class CordCraft {

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path privateConfigDir;

    static CordCraft instance = null;

    public static final String API_URL = "http://vps.craftmoney.fr:27980/";
    public static final String VERSION = "1.0.0";
    public static final int versionDataFile = 3;

    public static String token = "";
    public static String guildID = "";
    public static String port = "";
    public static String enable = "";

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        String fileEmplacement = privateConfigDir.toString() + ".json";
        File dataFile = new File(fileEmplacement);

        if (!ConfigFile.ConfigCreate(fileEmplacement, dataFile))
            logger.error("ConfigFile : Error creating config file.");
        if (!ConfigFile.ConfigUpdate(fileEmplacement))
            logger.error("ConfigFile : Error update config file.");
        if (!ConfigFile.ConfigSetValueFromFile(fileEmplacement))
            logger.error("ConfigFile : Error loading config file.");

        if (enable.equals("true")) {
            if(port.equals("")) {
                logger.warn("APIWeb : Please configure the port in the config file ().");
            } else {
                try {
                    new Receiver(CordCraft.port);
                    logger.info("APIWeb : Started");
                } catch (Exception e) {
                    logger.error("APIWeb : Error (port not available)");
                }
            }
            Sponge.getEventManager().registerListeners(this, new ServerListener());
            logger.info("CordCraft enable !");
            Sender.sendServerStartToDiscord();
        } else
            logger.info("CordCraft is disable.");
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        if (enable.equals("true"))
            Sender.sendServerStopToDiscord();
    }

    public static Logger getLogger() {
        return instance.logger;
    }

}
