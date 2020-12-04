package top.retarders.hardon.leaderboards.gui;

import me.lucko.helper.Helper;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import top.retarders.hardon.leaderboards.LeaderboardsModule;

public class LeaderboardsGui extends Gui {

    private final static MenuScheme BUTTONS = new MenuScheme()
            .mask("000000000")
            .mask("000010000")
            .mask("001010100")
            .mask("000010000")
            .mask("000000000");

    private static LeaderboardsModule leaderboardsModule = Helper.service(LeaderboardsModule.class).get();

    public LeaderboardsGui(Player player) {
        super(player, 6, "Leaderboard");
    }

    @Override
    public void redraw() {
        if(!this.isFirstDraw()) return;

        MenuPopulator populator = BUTTONS.newPopulator(this);

        populator.accept(ItemStackBuilder.of(Material.DIAMOND_SWORD)
                .name("&b&lMost Kills")
                .lore("&7Click to view the players with the most kills")
                .build(() -> new LeaderboardGui(this.getPlayer(), this.leaderboardsModule.find("most_kills")).open()));

        populator.accept(ItemStackBuilder.of(Material.GOLD_AXE)
                .name("&6&lHighest Killstreak")
                .lore("&7Click to view the players with the highest killstreak")
                .build(() -> new LeaderboardGui(this.getPlayer(), this.leaderboardsModule.find("highest_killstreak")).open()));

        populator.accept(ItemStackBuilder.of(Material.BOW)
                .name("&7&lPractice Leaderboards")
                .lore("&7Click to view the Practice Leaderboards")
                .build(() -> this.getPlayer().sendMessage(ChatColor.RED + "This feature is unavailable")));

        populator.accept(ItemStackBuilder.of(Material.DIAMOND)
                .name("&6&lRichest")
                .lore("&7Click to view the richest players on the server")
                .build(() -> new LeaderboardGui(this.getPlayer(), this.leaderboardsModule.find("richest")).open()));

        populator.accept(ItemStackBuilder.of(Material.REDSTONE)
                .name("&4&lMost Deaths")
                .lore("&7Click to view the players with the most deaths")
                .build(() -> new LeaderboardGui(this.getPlayer(), this.leaderboardsModule.find("most_deaths")).open()));
    }

}
