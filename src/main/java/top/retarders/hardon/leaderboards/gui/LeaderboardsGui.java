package top.retarders.hardon.leaderboards.gui;

import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuScheme;
import org.bukkit.entity.Player;

public class LeaderboardsGui extends Gui {

    private final static MenuScheme BUTTONS = new MenuScheme()
            .mask("000000000")
            .mask("000010000")
            .mask("000101000")
            .mask("000010000")
            .mask("000000000");

    public LeaderboardsGui(Player player) {
        super(player, 6, "Leaderboard");
    }

    @Override
    public void redraw() {
//        if(!this.isFirstDraw()) return;

//        MenuPopulator populator = BUTTONS.newPopulator(this);

        // todo
    }

}
