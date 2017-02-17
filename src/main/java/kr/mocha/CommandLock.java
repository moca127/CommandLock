package kr.mocha;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.io.File;

/**
 * Created by mocha on 16. 11. 16.
 */
public class CommandLock extends PluginBase implements Listener{
    public Config config;

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        config = new Config(getDataFolder()+ File.separator+"config.yml", Config.YAML);
        this.getServer().getPluginManager().registerEvents(this, this);
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(TextFormat.RED + "명령어의 권한이 없습니다.");
            return false;
        }
        else{
            try{
                switch (args[0]){
                    case "add":
                    case "a":
                        config.set(arrToStr(args, 1), true);
                        config.save();
                        sender.sendMessage(TextFormat.AQUA+"[ 알림 ] "+TextFormat.GRAY+arrToStr(args, 1)+" 명령어가 잠겼습니다.");
                        return true;
                    case "del":
                    case "d":
                        config.remove(arrToStr(args, 1));
                        sender.sendMessage(TextFormat.AQUA+"[ 알림 ] "+TextFormat.GRAY+arrToStr(args, 1)+" 명령어의 잠금이 해제되었습니다.");
                        return true;
                    case "list":
                    case "l":
                        sender.sendMessage("=== Locked Commands ===");
                        config.getKeys().forEach(s -> sender.sendMessage(TextFormat.GREEN+s));
                        return true;
                    default:
                        return false;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                return false;
            }
        }
    }

    public String arrToStr(String[] args, int start) {
        String r = "";
        for(int i = start; i < args.length; i++) {
            if(i == args.length-1)
                r += args[i];
            else
                r += args[i] + " ";
        }
        return r;
    }

}
