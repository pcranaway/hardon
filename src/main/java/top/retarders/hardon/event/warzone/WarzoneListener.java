package top.retarders.hardon.event.warzone;

import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.entity.PlayerDeathEvent;
import top.retarders.hardon.event.warzone.handler.DeathHandler;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.user.state.UserState;

public class WarzoneListener implements TerminableModule {

    private final UserRepository repository = Helper.service(UserRepository.class).get();

    @Override
    public void setup(TerminableConsumer consumer) {
        Events.subscribe(PlayerDeathEvent.class)
                .filter(event -> this.repository.find(event.getEntity().getUniqueId()).get().state == UserState.WARZONE)
                .handler(new DeathHandler())
                .bindWith(consumer);
    }
}
