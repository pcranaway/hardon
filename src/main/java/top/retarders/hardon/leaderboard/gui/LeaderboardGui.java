package top.retarders.hardon.leaderboard.gui;

import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.entity.Player;

public class LeaderboardGui extends Gui {

    private final static MenuScheme BUTTONS = new MenuScheme()
            .mask("000000000")
            .mask("001010100")
            .mask("000000000");

    public LeaderboardGui(Player player) {
        super(player, 6, "KitPvP Leaderboard");
    }

    @Override
    public void redraw() {

    }

}
