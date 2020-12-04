package top.retarders.hardon.command.kit.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.Services;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.kit.repo.KitRepository;

public class NewKitCommandHandler implements FunctionalCommandHandler<Player> {

    private final KitRepository repository = Services.get(KitRepository.class).get();

    @Override
    public void handle(CommandContext<Player> context) {
        String name = context.arg(0).parse(String.class).get();

        if (this.repository.find(name).isPresent()) {
            context.reply("&cKit &f\"" + name + "\" &calready exists");
            return;
        }

        Kit kit = new Kit();

        kit.name = name;
        kit.description = "No description";
        kit.armor = "";
        kit.inventory = "";
        kit.price = 0;
        kit.color = ChatColor.WHITE;
        kit.displayItem = Material.DEAD_BUSH;

        this.repository.put(kit);
        this.repository.saveKit(kit);

        context.reply(
                "&aKit &f\"" + name + "\" &acreated",
                "&aSet the kit's inventory using &f/setkit <name>"
        );
    }

}