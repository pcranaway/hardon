package top.retarders.hardon.user.preferences;

import me.lucko.helper.Helper;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.text3.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

public class PreferencesGui extends Gui {

    private final static MenuScheme BUTTONS = new MenuScheme()
            .mask("000000000")
            .mask("001010100")
            .mask("000000000");

    private UserRepository userRepository = Helper.service(UserRepository.class).get();

    private final User user;

    public PreferencesGui(Player player) {
        super(player, 3, "Preferences");

        this.user = this.userRepository.find(player.getUniqueId()).get();
    }

    @Override
    public void redraw() {
        MenuPopulator populator = BUTTONS.newPopulator(this);

        boolean globalChat = this.user.account.globalChat;
        boolean sidebar = this.user.account.sidebar;
        boolean deathMessages = this.user.account.deathMessages;

        populator.accept(ItemStackBuilder.of(Material.PAPER)
                .name("&bGlobal Chat")
                .lore("&7Click to " + (globalChat ? "&cdisable" : "&aenable") + " &fGlobal Chat")
                .build(() -> {
                    String newStatus = globalChat ? "&cdisabled" : "&aenabled";

                    this.user.account.globalChat = !globalChat;
                    this.getPlayer().sendMessage(Text.colorize("&7You have " + newStatus + " &fGlobal Chat"));

                    this.redraw();
                })
        );

        populator.accept(ItemStackBuilder.of(Material.ITEM_FRAME)
                .name("&bSidebar")
                .lore("&7Click to " + (sidebar ? "&cdisable" : "&aenable") + " &fSidebar")
                .build(() -> {
                    String newStatus = sidebar ? "&cdisabled" : "&aenabled";

                    this.user.account.sidebar = !sidebar;
                    this.getPlayer().sendMessage(Text.colorize("&7You have " + newStatus + " &fSidebar"));

                    this.redraw();
                })
        );

        populator.accept(ItemStackBuilder.of(Material.NETHER_STAR)
                .name("&bDeath Messages")
                .lore("&7Click to " + (deathMessages ? "&cdisable" : "&aenable") + " &fDeath Messages")
                .build(() -> {
                    String newStatus = deathMessages ? "&cdisabled" : "&aenabled";

                    this.user.account.deathMessages = !deathMessages;
                    this.getPlayer().sendMessage(Text.colorize("&7You have " + newStatus + " &fDeath Messages"));

                    this.redraw();
                })
        );

    }

}
