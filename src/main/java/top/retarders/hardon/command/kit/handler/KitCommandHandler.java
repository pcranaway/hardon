package top.retarders.hardon.command.kit.handler;

import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.gui.KitListGui;

public class KitCommandHandler implements FunctionalCommandHandler<Player> {

    @Override
    public void handle(CommandContext<Player> context) {
        new KitListGui(context.sender(), 1).open();
    }

}
