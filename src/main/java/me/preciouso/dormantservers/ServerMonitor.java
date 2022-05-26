package me.preciouso.dormantservers;

import me.preciouso.dormantservers.utils.AwsHelper;
import me.preciouso.dormantservers.utils.LobbyChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Map;

public class ServerMonitor implements Runnable {

    private long lastActive;
    private final String serverName;
    private final int minsTillShutDown;
    private final LobbyChecker lobbyChecker;

    public ServerMonitor(DormantServers plugin) {
        lobbyChecker = new LobbyChecker(plugin);
        minsTillShutDown = plugin.getConfig().getInt("server.max_idle", 30);
        serverName = plugin.getConfig().getString("server.name");
        lastActive = System.currentTimeMillis();
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();

        // If server is up, check if players have been active in last N mins, then shutdown instance if so
        if (this.lobbyChecker.isServerUp(serverName)) {
            Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();

            if (servers.get(serverName).getPlayers().isEmpty()) {
                if ((now - lastActive) / 1000 / 60 > minsTillShutDown) {
                    AwsHelper.build().stopEc2Instance();
                }
            } else {
                lastActive = System.currentTimeMillis();
            }
        }
    }
}
