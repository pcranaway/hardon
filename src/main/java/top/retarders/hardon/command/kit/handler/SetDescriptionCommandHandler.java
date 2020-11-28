package top.retarders.hardon.command.kit.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.kit.repo.KitRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public class SetDescriptionCommandHandler implements FunctionalCommandHandler<Player> {

    private KitRepository repository = Helper.service(KitRepository.class).get();

    @Override
    public void handle(CommandContext<Player> context) {
        String name = context.arg(0).parse(String.class).get();
        Optional<Kit> hasKit = this.repository.find(name);

        if(!hasKit.isPresent()) {
            context.reply("&cKit &f\"" + name + "\" &cdoesn't exist");
            return;
        }

        String description = context.args().subList(1, context.args().size()).stream().collect(Collectors.joining(" "));

        Kit kit = hasKit.get();
        kit.description = description;
        repository.saveKit(kit);

        context.reply("&aSet description of kit &f\"" + name + "\" &ato &f\"" + description + "\"");
    }

}
