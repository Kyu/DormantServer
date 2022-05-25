package me.preciouso.dormantservers.utils;

import me.preciouso.dormantservers.DormantServers;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LobbyChecker {
    DormantServers plugin;
    int port;

    public LobbyChecker(DormantServers plugin) {
        this.plugin = plugin;
        this.port = this.plugin.getConfig().getInt("server.port");
    }

    // TODO Checks for multiple pings via annotation
    private boolean pingServer(InetSocketAddress addr) {

        try {
            Socket s = new Socket();
            s.connect(addr, 300); // TODO make timeout configurable?
            s.close();
        } catch (IOException e) {
            return false; // Not online
        }

        return true;
    }

    public boolean isLobbyAlreadyUp(String hostname) {
        return pingServer(new InetSocketAddress(hostname, this.port));
    }

    public boolean isServerUp(String name) {
        plugin.getLogger().info("Checking for server: " + name);
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(name);
        if (serverInfo == null) {
            plugin.getLogger().info("Could not find server named: " + name);
            return false;
        }
        return pingServer((InetSocketAddress) serverInfo.getSocketAddress());
    }

    public void updateServer(String name, String hostname) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(name);
        new ProxyServerHelper().addServer(name, hostname, this.port, serverInfo.getMotd(), serverInfo.isRestricted(), true);
    }
}


