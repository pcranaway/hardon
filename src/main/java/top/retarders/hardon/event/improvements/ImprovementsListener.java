package top.retarders.hardon.event.improvements;

import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import top.retarders.hardon.user.repo.UserRepository;

public class ImprovementsListener implements TerminableModule {

    private UserRepository repository = Helper.service(UserRepository.class).get();

    @Override
    public void setup(TerminableConsumer consumer) {
        Events.subscribe(FoodLevelChangeEvent.class)
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);

        Events.subscribe(CreatureSpawnEvent.class)
                .filter(event -> event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM)
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);

        Events.subscribe(WeatherChangeEvent.class)
                .handler(event -> {
                    event.setCancelled(true);
                    event.getWorld().setStorm(true);
                })
                .bindWith(consumer);

        Events.subscribe(BlockBreakEvent.class)
                .filter(event -> !this.repository.find(event.getPlayer().getUniqueId()).get().buildmode)
                .handler(event -> event.setCancelled(true));

        Events.subscribe(BlockPlaceEvent.class)
                .filter(event -> !this.repository.find(event.getPlayer().getUniqueId()).get().buildmode)
                .handler(event -> event.setCancelled(true));

        Events.subscribe(EntityDamageEvent.class)
                .filter(event -> event.getCause() == EntityDamageEvent.DamageCause.FALL)
                .handler(event -> event.setCancelled(true));
    }
}
