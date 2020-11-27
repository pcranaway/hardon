package top.retarders.hardon.event.warzone.handler;

import me.lucko.helper.text3.Text;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.function.Consumer;

public class DeathHandler implements Consumer<PlayerDeathEvent> {

    @Override
    public void accept(PlayerDeathEvent event) {
        event.setDeathMessage(Text.colorize("%s &7was killed by &f%s"));
    }

}
