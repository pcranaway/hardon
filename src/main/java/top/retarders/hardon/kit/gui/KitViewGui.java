package top.retarders.hardon.kit.gui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.retarders.hardon.kit.Kit;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class KitViewGui extends Gui {

    private static final MenuScheme BUTTONS = new MenuScheme()
            .mask("100000001")
            .mask("000000001");

    private final Kit kit;

    public KitViewGui(Player player, Kit kit) {
        super(player, 2, "Viewing " + kit.name);

        this.kit = kit;
    }

    @Override
    public void redraw() {
        MenuPopulator populator = BUTTONS.newPopulator(this);

        Optional<ItemStack> primaryWeapon = this.kit.getPrimaryWeapon();

        if(primaryWeapon.isPresent()) {
            populator.accept(ItemStackBuilder.of(primaryWeapon.get()).build(() -> {}));
        }

        populator.accept(ItemStackBuilder.of(Material.PAPER)
                .name(this.kit.color + this.kit.name)
                .lore(ChatColor.GRAY + this.kit.description)
                .lore("")
                .lore("&7Price &f- " + this.kit.color + "$" + this.kit.price)
                .build(() -> {}));

        AtomicInteger slot = new AtomicInteger(9);

        Stream.of(this.kit.getInventory())
                .filter(item -> item != null && item.getType() != null)
                .filter(item -> {
                    if(!primaryWeapon.isPresent()) return true;
                    if(primaryWeapon.get() == item) return false;

                    return true;
                })
                .forEach(item -> this.setItem(slot.getAndIncrement(), ItemStackBuilder.of(item.getType()).build(() -> {})));

        populator.accept(ItemStackBuilder.of(Material.SADDLE)
                .name("&6&lEquip")
                .lore("&7Click to equip " + kit.color + this.kit.name)
                .build(() -> {
                    this.close();
                    kit.equip(getPlayer());
                    this.getPlayer().sendMessage(ChatColor.GRAY + "Equipping " + kit.color + kit.name);
                }));
    }

}
