package top.retarders.hardon.event.statistics;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.retarders.hardon.event.statistics.handler.DeathHandler;

public class StatisticsListener implements TerminableModule {
    @Override
    public void setup(TerminableConsumer consumer) {
        Events.subscribe(PlayerDeathEvent.class)
//                .filter(event -> event.getEntity().getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK))
//                .filter(event -> ((EntityDamageByEntityEvent) event.getEntity().getLastDamageCause()).getDamager() instanceof Player)
                .handler(new DeathHandler())
                .bindWith(consumer);
    }
}
