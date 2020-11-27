package top.retarders.hardon.command.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.kit.repo.KitRepository;
import top.retarders.hardon.serialization.ItemSerializer;

import java.util.Optional;

public class SetKitCommandHandler implements FunctionalCommandHandler<Player> {

    private KitRepository repository = Helper.service(KitRepository.class).get();

    @Override
    public void handle(CommandContext<Player> context) {
        String name = context.arg(0).parse(String.class).get();
        Optional<Kit> hasKit = this.repository.find(name);

        if(!hasKit.isPresent()) {
            context.reply("&cKit &f\"" + name + "\" &cdoesn't exist");
            return;
        }

        PlayerInventory inventory = context.sender().getInventory();

        Kit kit = hasKit.get();
        kit.inventory = ItemSerializer.itemStackArrayToBase64(inventory.getContents());
        kit.armor = ItemSerializer.itemStackArrayToBase64(inventory.getArmorContents());

        this.repository.saveKit(kit);

        context.reply("&aKit &f\"" + name + "\" &aupdated");
    }

}