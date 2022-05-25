package me.preciouso.dormantservers;

import com.amazonaws.services.ec2.model.Instance;
import me.preciouso.dormantservers.listeners.PlayerAttemptJoinListener;
import me.preciouso.dormantservers.utils.AwsHelper;
import me.preciouso.dormantservers.utils.LobbyChecker;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class DormantServers extends Plugin {
    private Configuration config;
    public Configuration makeConfig() throws IOException {
        // Create plugin config folder if it doesn't exist
        if (!getDataFolder().exists()) {
           getLogger().info("Created config folder: " + getDataFolder().mkdir());
        }

        File configFile = new File(getDataFolder(), "config.yml");

        // Copy default config if it doesn't exist
        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile);
            InputStream in = getResourceAsStream("config.yml");
            in.transferTo(outputStream);
        }
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    }

    @Override
    public void onEnable() {
        try {
            config = makeConfig();
        } catch (IOException e) {
            getLogger().log(Level.WARNING,"Could not load config. Plugin will not continue to load.");
            e.printStackTrace();
            return;
        }

        try { // Validate AWS creds and check if AWS server is already up (in case of some forced shutdown)
            AwsHelper.build(this).refreshEc2Instance();
        } catch (RuntimeException exception) {
            getLogger().log(Level.WARNING, "AWS credentials could not be validated. Plugin will not continue to load.");
        } finally {
            String serverName = this.getConfig().getString("server.name");
            if (! new LobbyChecker(this).isLobbyAlreadyUp(serverName)) { // Server is down

                Instance myInst = AwsHelper.build().getMyEc2Instance();
                if (myInst != null) { // Running: 16, Stopped: 80 (?)
                    String hostname = myInst.getPublicIpAddress();
                    new LobbyChecker(this).updateServer(serverName, hostname);
                }
            }
        }

        getProxy().getPluginManager().registerListener(this, new PlayerAttemptJoinListener(this));
        getLogger().info("Plugin loaded!");
    }

    public Configuration getConfig() {
        return config;
    }
}
