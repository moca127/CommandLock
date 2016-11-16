package kr.mocha.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import kr.mocha.CommandLock;

/**
 * Created by mocha on 16. 11. 16.
 */
public class CommandLockCommand extends Command{
    public Config config = CommandLock.getInstance().getConfig();

    public CommandLockCommand(){
        super("commandlock", "Lock commands", "/commandlock <add|del|list>", new String[]{"cmdlock"});
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof ConsoleCommandSender))
            sender.sendMessage(TextFormat.RED+"명령어의 권한이 없습니다.");
        else{
            try{
                switch (args[0]){
                    case "add":
                    case "a":
                        config.set(args[1], true);
                        config.save();
                        sender.sendMessage(TextFormat.AQUA+"[ 알림 ] "+TextFormat.GRAY+args[1]+" 명령어가 잠겼습니다.");
                        return true;
                    case "del":
                    case "d":
                        config.remove(args[1]);
                        sender.sendMessage(TextFormat.AQUA+"[ 알림 ] "+TextFormat.GRAY+args[1]+" 명령어의 잠금이 해제되었습니다.");
                        return true;
                    case "list":
                    case "l":
                        sender.sendMessage("=== Locked Commands ===");
                        config.getKeys().forEach(s -> sender.sendMessage(TextFormat.GREEN+s));
                        return true;
                    default:
                        sender.sendMessage(TextFormat.RED+getUsage());
                        break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                sender.sendMessage(TextFormat.RED+getUsage());
            }
        }
        return false;
    }
}
