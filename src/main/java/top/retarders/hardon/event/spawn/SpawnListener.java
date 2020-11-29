package top.retarders.hardon.event.spawn;

import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import top.retarders.hardon.event.spawn.handler.InteractEventHandler;
import top.retarders.hardon.event.spawn.handler.JoinEventHandler;
import top.retarders.hardon.event.spawn.handler.RespawnEventHandler;
import top.retarders.hardon.spawn.SpawnItems;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

public class SpawnListener implements TerminableModule {

    private final UserRepository repository = Helper.service(UserRepository.class).get();

    @Override
    public void setup(TerminableConsumer consumer) {
        Events.subscribe(PlayerJoinEvent.class)
                .filter(event -> repository.find(event.getPlayer().getUniqueId()).isPresent())
                .handler(new JoinEventHandler())
                .bindWith(consumer);

        Events.subscribe(PlayerInteractEvent.class)
                .filter(event -> event.hasItem())
                .filter(event -> event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                .filter(event -> SpawnItems.isSpawnItem(event.getItem()))
                .filter(event -> this.repository.find(event.getPlayer().getUniqueId()).get().state == UserState.SPAWN)
                .handler(new InteractEventHandler())
                .bindWith(consumer);

        Events.subscribe(PlayerRespawnEvent.class)
                .handler(new RespawnEventHandler())
                .bindWith(consumer);

        Events.subscribe(PlayerPickupItemEvent.class)
                .filter(event -> this.repository.find(event.getPlayer().getUniqueId()).get().state == UserState.SPAWN)
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);

        Events.subscribe(PlayerDropItemEvent.class)
                .filter(event -> this.repository.find(event.getPlayer().getUniqueId()).get().state == UserState.SPAWN)
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);

        Events.subscribe(EntityDamageEvent.class)
                .filter(event -> this.repository.find(event.getEntity().getUniqueId()).get().state == UserState.SPAWN)
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);

        Events.subscribe(PlayerItemDamageEvent.class)
                .filter(event -> this.repository.find(event.getPlayer().getUniqueId()).get().state == UserState.SPAWN)
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);

        Events.subscribe(EntityDamageByEntityEvent.class)
                .filter(event ->
                        (this.repository.find(event.getDamager().getUniqueId()).get().state == UserState.SPAWN)
                                ||
                                (this.repository.find(event.getEntity().getUniqueId()).get().state == UserState.SPAWN)
                )
                .handler(event -> event.setCancelled(true))
                .bindWith(consumer);
    }

}
