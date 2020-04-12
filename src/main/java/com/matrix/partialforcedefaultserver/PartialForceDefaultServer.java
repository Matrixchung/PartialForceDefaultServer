package com.matrix.partialforcedefaultserver;

import com.matrix.partialforcedefaultserver.events.PlayerLoginEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public final class PartialForceDefaultServer extends Plugin {
    private static PartialForceDefaultServer plugin;
    public static List<String> serversNeed = null;
    public static String serversLobby = null;
    public static ServerInfo lobby = null;
    public static HashSet<ServerInfo> servers = new HashSet<>();;
    @Override
    public void onLoad(){
        try{
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdirs();
            }
            File config = new File(this.getDataFolder().getPath()+"/config.yml");
            if(!config.exists()){
                config.createNewFile();
                InputStream iStream = getResourceAsStream("config.yml");
                FileOutputStream foStream = new FileOutputStream(config.getPath());
                int ch = 0;
                try {
                    while((ch=iStream.read()) != -1){
                        foStream.write(ch);
                    }
                } catch (IOException e1) {
                    getLogger().warning("Exception in creating config:"+e1.getMessage());
                } finally{
                    foStream.close();
                    iStream.close();
                }
            }
            Yaml yaml = new Yaml();
            Map fileMap ;
            fileMap = (Map) yaml.load(new FileInputStream(config));
            serversLobby = (String) fileMap.get("lobbyServer");
            serversNeed = (List<String>) fileMap.get("serversNeedToTpToLobby");
        }
        catch(Exception e){
            getLogger().warning("Exception in loading config:"+e.getMessage());
        }
    }

    @Override
    public void onEnable() {
        plugin=this;
        // Plugin startup logic
        getProxy().getPluginManager().registerListener(this,new PlayerLoginEvent());
        try{
            lobby = ProxyServer.getInstance().getServerInfo(serversLobby);
            getLogger().info("Lobby: "+lobby.getName());
            for(String s : serversNeed){
                servers.add(ProxyServer.getInstance().getServerInfo(s));
                getLogger().info("ServersNeedTp: "+s);
            }
            getLogger().info("PartialForceDefaultServer Enabled");
        }
        catch (Exception e){
            getLogger().warning("Error in getting server info:"+e.getMessage());
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static PartialForceDefaultServer getPlugin(){
        return plugin;
    }

}
