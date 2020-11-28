package top.retarders.hardon.kit.gui;

import me.lucko.helper.Helper;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.kit.repo.KitRepository;

public class KitDeleteGui extends Gui {

    private final static MenuScheme BUTTONS = new MenuScheme()
            .mask("000000000")
            .mask("000111000")
            .mask("000000000");

    private final KitRepository repository = Helper.service(KitRepository.class).get();

    private final Kit kit;

    public KitDeleteGui(Player player, Kit kit) {
        super(player, 3, "Deleting \"" + kit.name + "\"");

        this.kit = kit;
    }

    @Override
    public void redraw() {
        MenuPopulator populator = BUTTONS.newPopulator(this);

        populator.accept(ItemStackBuilder.of(Material.WOOL)
                .data(1)
                .name("&6&lCancel")
                .lore("&7Click to cancel the &cdeletion &7of " + kit.color + kit.name)
                .build(() -> {
                    this.close();
                }));

        populator.accept(ItemStackBuilder.of(Material.PAPER)
                .name("&c&lDeleting " + kit.color + kit.name)
                .lore("&7You are about to &cdelete &7" + kit.color + kit.name)
                .build(() -> {}));

        populator.accept(ItemStackBuilder.of(Material.WOOL)
                .data(14)
                .name("&c&lConfirm")
                .lore("&7Click to confirm the &cdeletion &7of " + kit.color + kit.name)
                .build(() -> {
                    this.repository.remove(kit);
                    this.repository.deleteKit(kit);
                    this.close();
                    this.getPlayer().sendMessage(ChatColor.GREEN + "Deleted kit " + kit.color + kit.name);
                }));
    }
}
