package top.retarders.hardon.command.kit.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.Services;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.kit.repo.KitRepository;

import java.util.Optional;

public class SetPriceCommandHandler implements FunctionalCommandHandler<Player> {

    private final KitRepository repository = Services.get(KitRepository.class).get();

    @Override
    public void handle(CommandContext<Player> context) {
        String name = context.arg(0).parse(String.class).get();
        Optional<Kit> hasKit = this.repository.find(name);

        if (!hasKit.isPresent()) {
            context.reply("&cKit &f\"" + name + "\" &cdoesn't exist");
            return;
        }

        int price = context.arg(1).parse(Integer.class).get();

        Kit kit = hasKit.get();
        kit.price = price;
        repository.saveKit(kit);

        context.reply("&aSet price of kit &f\"" + name + "\" &ato &f$" + price);
    }

}
