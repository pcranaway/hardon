package top.retarders.hardon.event.spawn;

import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import top.retarders.hardon.event.spawn.handler.InteractEventHandler;
import top.retarders.hardon.event.spawn.handler.JoinEventHandler;
import top.retarders.hardon.spawn.SpawnItems;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

public class SpawnListener implements TerminableModule {

    private UserRepository repository = Helper.service(UserRepository.class).get();

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
                .filter(event ->  this.repository.find(event.getPlayer().getUniqueId()).get().state == UserState.SPAWN)
                .handler(new InteractEventHandler())
                .bindWith(consumer);
    }

}
