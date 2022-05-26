package me.preciouso.dormantservers.listeners;

import me.preciouso.dormantservers.DormantServers;
import me.preciouso.dormantservers.utils.AwsHelper;
import me.preciouso.dormantservers.utils.LobbyChecker;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PlayerAttemptJoinListener implements Listener {
    private final String kickMessage;
    private final String waitMessage;
    private final String serverName;
    private final int serverBootDelay;

    DormantServers plugin;


    public PlayerAttemptJoinListener(DormantServers plugin) {
        this.plugin = plugin;
        Configuration cfg = plugin.getConfig();
        this.serverName = cfg.getString("server.name");
        this.serverBootDelay = cfg.getInt("server.boot_delay_seconds", 30);

        this.waitMessage = cfg.getString("wait_message");
        this.kickMessage = cfg.getString("kick_message");
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        if (! new LobbyChecker(plugin).isServerUp(serverName)) {
            ProxiedPlayer pl = event.getPlayer();
            if (canBringUpServer(pl)) {
                pl.disconnect(new TextComponent(waitMessage));

                AwsHelper helper = AwsHelper.build();

                helper.startEc2Instance();

                // TODO this runs in the future // blocking
                this.plugin.getProxy().getScheduler().schedule(this.plugin, () -> {

                    helper.refreshEc2Instance();
                    new LobbyChecker(this.plugin).updateServer(serverName, helper.getMyEc2Instance().getPublicIpAddress());

                }, serverBootDelay, TimeUnit.SECONDS);

                /* TODO Checks for multiple starts/stops
                    If server is known to have shut down, dont check serverUp
                */
            } else {
                pl.disconnect(new TextComponent(kickMessage));
            }
        }
    }

    public boolean canBringUpServer(ProxiedPlayer player) {
        return player.hasPermission("dormantserver.start");
    }

}
