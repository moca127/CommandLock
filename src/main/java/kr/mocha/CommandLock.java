package kr.mocha;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import kr.mocha.command.CommandLockCommand;

import java.io.File;

/**
 * Created by mocha on 16. 11. 16.
 */
public class CommandLock extends PluginBase implements Listener{
    public static CommandLock instance;
    public Config config;

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdirs();
        config = new Config(getDataFolder()+ File.separator+"config.yml", Config.YAML);
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getCommandMap().register("commandlock", new CommandLockCommand());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        config.save();
        super.onDisable();
    }

    /*event*/
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){
        String command = event.getMessage();

        getConfig().getKeys().forEach(s -> {
            if(command.startsWith(s)){
                event.setCancelled();
                event.getPlayer().sendMessage(TextFormat.RED+"this command is locked!");
            }
        });
    }

    public static CommandLock getInstance(){
        return instance;
    }

}
