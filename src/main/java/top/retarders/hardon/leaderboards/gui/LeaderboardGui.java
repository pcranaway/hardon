package top.retarders.hardon.leaderboards.gui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import top.retarders.hardon.leaderboards.Leaderboard;

import java.util.HashMap;

public class LeaderboardGui extends Gui {

    private final static MenuScheme BUTTONS = new MenuScheme()
            .mask("000000000")
            .mask("001010100")
            .mask("001010100")
            .mask("000000000");

    private final static HashMap<Integer, ChatColor> POSITION_COLORS = new HashMap<Integer, ChatColor>() {{
        put(1, ChatColor.GOLD);
        put(2, ChatColor.AQUA);
        put(3, ChatColor.GRAY);
    }};

    private final static HashMap<Integer, Material> POSITION_BLOCKS = new HashMap<Integer, Material>() {{
        put(1, Material.GOLD_BLOCK);
        put(2, Material.DIAMOND_BLOCK);
        put(3, Material.IRON_BLOCK);
    }};

    private final Leaderboard leaderboard;

    public LeaderboardGui(Player player, Leaderboard leaderboard) {
        super(player, 4, leaderboard.name + " Leaderboard");

        this.leaderboard = leaderboard;
    }

    @Override
    public void redraw() {
        if(!this.isFirstDraw()) return;

        MenuPopulator populator = BUTTONS.newPopulator(this);

        this.leaderboard.data.forEach(player -> {
            int index = this.leaderboard.data.indexOf(player) + 1;
            ChatColor color = POSITION_COLORS.get(index);

            populator.accept(ItemStackBuilder.of(Material.SKULL_ITEM)
                    .data(3)
                    .transform(itemStack -> ((SkullMeta) itemStack.getItemMeta()).setOwner(player.name))
                    .name(color + player.name)
                    .lore(color + "Kills:" + ChatColor.GRAY + " " + player.kills)
                    .lore(color + "Highest Killstreak:" + ChatColor.GRAY + " " + player.highestKillstreak)
                    .lore(color + "Deaths:" + ChatColor.GRAY + " " + player.deaths)
                    .lore(color + "Balance:" + ChatColor.GRAY + " " + player.balance)
                    .build(() -> {}));
        });

        this.leaderboard.data.forEach(player -> {
            int index = this.leaderboard.data.indexOf(player) + 1;
            Material block = POSITION_BLOCKS.get(index);
            ChatColor color = POSITION_COLORS.get(index);

            populator.accept(ItemStackBuilder.of(block)
                    .name(color + "#" + index)
                    .build(() -> {}));
        });
    }

}