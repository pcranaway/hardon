package top.retarders.hardon.kit.gui;

import me.lucko.helper.Helper;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import top.retarders.hardon.kit.repo.KitRepository;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class KitListGui extends Gui {

    private static final MenuScheme BUTTONS = new MenuScheme()
            .mask("100000000")
            .mask("000000000")
            .mask("000000000")
            .mask("000000011");

    private KitRepository repository = Helper.service(KitRepository.class).get();

    private final int page;

    public KitListGui(Player player, int page) {
        super(player, 4, "Kits - Page " + page);

        this.page = page;
    }

    private boolean hasPreviousPage() {
        return this.page > 1;
    }

    @Override
    public void redraw() {
        if(!this.isFirstDraw()) return;

        MenuPopulator populator = BUTTONS.newPopulator(this);

        populator.accept(ItemStackBuilder.of(Material.REDSTONE)
                .name("&6&lNew Kit")
                .lore("&7Click to create a new kit")
                .build(() -> {
                    this.close();

                    this.getPlayer().sendMessage(ChatColor.GRAY + "Create a new kit using " + ChatColor.WHITE + "/newkit");
                }));

        populator.accept(ItemStackBuilder.of(Material.WOOL)
                .name("&a&lNext Page")
                .data(5)
                .lore("&7Click to go to the next page")
                .build(() -> {
                    this.close();
                    new KitListGui(this.getPlayer(), this.page + 1).open();
                }));

        populator.accept(ItemStackBuilder.of(Material.WOOL)
                .name("&4&lPrevious Page" + (this.hasPreviousPage() ? "" : " &7(Unavailable)"))
                .data(14)
                .lore("&7Click to go to the previous page")
                .build(() -> {
                    if(!this.hasPreviousPage()) return;

                    this.close();
                    new KitListGui(this.getPlayer(), this.page - 1).open();
                }));

        AtomicInteger slot = new AtomicInteger(9);

        this.repository.kits.forEach(kit -> {
            this.setItem(slot.getAndIncrement(), ItemStackBuilder.of(kit.displayItem)
                    .name(kit.color + ChatColor.BOLD.toString() + kit.name)
                    .lore(ChatColor.GRAY + kit.description)
                    .lore("&7Price &f- " + kit.color + "$" + kit.price)
                    .lore("")
                    .lore("&fClick &7to &fview &7(Unavailable)")
                    .lore("&fRight click &7to &fequip")
                    .lore("&fShift + Click &7to &fdelete")
                    .buildFromMap(new HashMap<ClickType, Runnable>(){{
                        put(ClickType.LEFT, () -> {
//                            close();
//                            new KitViewGui(getPlayer(), kit).open();
                            getPlayer().sendMessage(ChatColor.GRAY + "This feature is unavailable");
                        });

                        put(ClickType.RIGHT, () -> {
                            close();
                            kit.equip(getPlayer());
                            getPlayer().sendMessage(ChatColor.GRAY + "Equipping " + kit.color + kit.name);
                        });

                        put(ClickType.SHIFT_LEFT, () -> {
                            close();
                            new KitDeleteGui(getPlayer(), kit).open();
                        });
                    }})
            );
        });
    }
}
