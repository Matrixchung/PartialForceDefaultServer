package com.matrix.partialforcedefaultserver.events;

import com.matrix.partialforcedefaultserver.PartialForceDefaultServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerLoginEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerConnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if(PartialForceDefaultServer.serversNeed.isEmpty()||PartialForceDefaultServer.serversLobby.isEmpty()){
            PartialForceDefaultServer.getPlugin().getLogger().warning("Dispatched an event but your server configuration is empty or null.");
        }
        else{
            try{
                if(player.getServer().equals(PartialForceDefaultServer.lobby)){ //Not Null - Switch Server from Lobby
                    }
            }
            catch (NullPointerException e){ //Empty Server - Login from Proxy
                if(!event.getTarget().equals(PartialForceDefaultServer.lobby)&&
                        PartialForceDefaultServer.servers.contains(event.getTarget())){
                    player.setReconnectServer(PartialForceDefaultServer.lobby);
                    event.setTarget(PartialForceDefaultServer.lobby);
                }
            }
        }
        /*if(player.isForgeUser()){
            PartialForceDefaultServer.getPlugin().getLogger().info("Forge Detected.");
            Map<String,String> modList = player.getModList();
            for(Map.Entry<String, String> entry : modList.entrySet()){
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                PartialForceDefaultServer.getPlugin().getLogger().info("Mods: "+mapKey+" "+mapValue);
            }
        }*/
    }
}
