package top.retarders.hardon.command.kit.handler;

import me.lucko.helper.Services;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.kit.repo.KitRepository;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

import java.util.Optional;

public class EquipKitCommandHandler implements FunctionalCommandHandler<Player> {

    private final KitRepository kitRepository = Services.get(KitRepository.class).get();
    private final UserRepository userRepository = Services.get(UserRepository.class).get();

    @Override
    public void handle(CommandContext<Player> context) {
        User user = this.userRepository.find(context.sender().getUniqueId()).get();

        if (user.kit != null) {
            context.reply("&cYou already have a kit");
            return;
        }

        String name = context.arg(0).parse(String.class).get();
        Optional<Kit> hasKit = this.kitRepository.find(name);

        if (!hasKit.isPresent()) {
            context.reply("&cKit &f\"" + name + "\" &cdoesn't exist");
            return;
        }

        Kit kit = hasKit.get();

        user.state(UserState.WARZONE);
        user.kit = kit;
        kit.equip(context.sender());
    }
}
