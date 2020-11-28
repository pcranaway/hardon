package top.retarders.hardon.command.kit.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.kit.gui.KitDeleteGui;
import top.retarders.hardon.kit.repo.KitRepository;

import java.util.Optional;

public class DeleteKitCommandHandler implements FunctionalCommandHandler<Player> {

    private KitRepository repository = Helper.service(KitRepository.class).get();

    @Override
    public void handle(CommandContext<Player> context) {
        String name = context.arg(0).parse(String.class).get();
        Optional<Kit> hasKit = this.repository.find(name);

        if(!hasKit.isPresent()) {
            context.reply("&cKit &f\"" + name + "\" &cdoesn't exist");
            return;
        }

        new KitDeleteGui(context.sender(), hasKit.get()).open();
    }

}
