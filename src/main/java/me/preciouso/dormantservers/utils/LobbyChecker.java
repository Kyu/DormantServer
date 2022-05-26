package me.preciouso.dormantservers.utils;

import me.preciouso.dormantservers.DormantServers;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;

public class LobbyChecker {
    DormantServers plugin;
    int port;
    int max_timeout;

    public LobbyChecker(DormantServers plugin) {
        this.plugin = plugin;
        this.port = this.plugin.getConfig().getInt("server.port", 25565);
        this.max_timeout = this.plugin.getConfig().getInt("server.max_ping_timeout", 300);
    }

    // TODO Checks for multiple pings via annotation
    private boolean pingServer(InetSocketAddress addr) {

        try {
            Socket s = new Socket();
            s.connect(addr, max_timeout);
            s.close();
        } catch (IOException e) {
            return false; // Not online
        }

        return true;
    }

    public boolean isHostNameUp(String hostname) {
        return pingServer(new InetSocketAddress(hostname, this.port));
    }

    public boolean isServerUp(String name) {
        plugin.getLogger().log(Level.FINE, "Checking for server: " + name);
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(name);
        if (serverInfo == null) {
            plugin.getLogger().log(Level.FINE,"Could not find server named: " + name);
            return false;
        }
        plugin.getLogger().log(Level.FINE,"Pinging server " + name + "@" + serverInfo.getSocketAddress().toString());
        return pingServer((InetSocketAddress) serverInfo.getSocketAddress());
    }

    public void updateServer(String name, String hostname) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(name);
        new ProxyServerHelper().addServer(name, hostname, this.port, serverInfo.getMotd(), serverInfo.isRestricted(), true);
    }
}


