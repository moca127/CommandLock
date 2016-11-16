package kr.mocha;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import kr.mocha.command.CommandLockCommand;

/**
 * Created by mocha on 16. 11. 16.
 */
public class CommandLock extends PluginBase implements Listener{
    public static CommandLock instance;

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdirs();
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getCommandMap().register("commandlock", new CommandLockCommand());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.save();
        super.onDisable();
    }

    /*event*/
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){
        String command = event.getMessage();

        getConfig().getKeys().forEach(s -> {
            if(command.contains(s)){
                event.setCancelled();
                event.getPlayer().sendMessage(TextFormat.RED+"this command is locked!");
            }
        });
    }

    /*utils*/
    public void save(){
        getConfig().save();
    }

    public static CommandLock getInstance(){
        return instance;
    }
}
