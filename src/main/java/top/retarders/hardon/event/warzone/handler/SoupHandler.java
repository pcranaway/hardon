package top.retarders.hardon.event.warzone.handler;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Consumer;

public class SoupHandler implements Consumer<PlayerInteractEvent> {
    @Override
    public void accept(PlayerInteractEvent event) {
        double newHealth = event.getPlayer().getHealth() + 4.5;
        if (newHealth > 20.0) newHealth = 20.0;

        event.getPlayer().setHealth(newHealth);

        event.getItem().setType(Material.BOWL);
    }
}
