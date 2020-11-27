package top.retarders.hardon.event.spawn.handler;

import org.bukkit.event.player.PlayerInteractEvent;
import top.retarders.hardon.spawn.SpawnItems;

import java.util.function.Consumer;

public class InteractEventHandler implements Consumer<PlayerInteractEvent> {

    @Override
    public void accept(PlayerInteractEvent event) {
        SpawnItems.ITEMS.forEach(triplet -> {
            if(!triplet.first.isSimilar(event.getItem())) return;

            triplet.third.accept(event.getPlayer());
        });
    }

}
