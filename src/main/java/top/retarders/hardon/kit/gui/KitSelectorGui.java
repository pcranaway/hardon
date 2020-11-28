package top.retarders.hardon.kit.gui;

import me.lucko.helper.Helper;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import top.retarders.hardon.kit.repo.KitRepository;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

public class KitSelectorGui extends Gui {

    private final KitRepository kitRepository = Helper.service(KitRepository.class).get();
    private final UserRepository userRepository = Helper.service(UserRepository.class).get();

    public KitSelectorGui(Player player) {
        super(player, 3, "Kit Selector");
    }

    @Override
    public void redraw() {
        if(!isFirstDraw()) return;

        this.kitRepository.kits.forEach(kit -> this.addItem(ItemStackBuilder.of(kit.displayItem)
                .name(kit.color + kit.name)
                .lore(ChatColor.GRAY + kit.description)
                .lore("")
                .lore("&7Click to equip")
                .build(() -> {
                    User user = this.userRepository.find(getPlayer().getUniqueId()).get();

                    this.close();

                    kit.equip(getPlayer());
                    user.kit = kit;
                    user.state = UserState.WARZONE;
                })));
    }

}
