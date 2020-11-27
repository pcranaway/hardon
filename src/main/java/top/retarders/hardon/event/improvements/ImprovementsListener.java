package top.retarders.hardon.event.improvements;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ImprovementsListener implements TerminableModule {
    @Override
    public void setup(TerminableConsumer consumer) {
        Events.subscribe(FoodLevelChangeEvent.class)
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);
    }
}
