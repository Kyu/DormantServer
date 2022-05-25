package me.preciouso.dormantservers.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

public class ProxyServerHelper {
    public Map<String, ServerInfo> getServers() {
        return ProxyServer.getInstance().getServers();
    }

    public boolean serverExists(String name) {
        return getServers().containsKey(name);
    }

    public void addServer(String name, String host, int port, String motd, boolean restricted, boolean force) {

        if (!force && serverExists(name)) {
            return;
        }
        SocketAddress addr = new InetSocketAddress(host, port);
        ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name,
                addr, motd, restricted);

       getServers().put(name, serverInfo);
    }
}
