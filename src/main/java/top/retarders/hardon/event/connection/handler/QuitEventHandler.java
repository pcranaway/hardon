package top.retarders.hardon.event.connection.handler;

import me.lucko.helper.Services;
import me.lucko.helper.mongo.Mongo;
import org.bukkit.event.player.PlayerQuitEvent;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;
import top.retarders.hardon.utilities.PlayerUtilities;

import java.util.function.Consumer;

public class QuitEventHandler implements Consumer<PlayerQuitEvent> {

    private final UserRepository repository = Services.get(UserRepository.class).get();
    private final Mongo mongo = Services.get(Mongo.class).get();

    @Override
    public void accept(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        PlayerUtilities.clear(event.getPlayer());
        PlayerUtilities.resetState(event.getPlayer());

        User user = this.repository.find(event.getPlayer().getUniqueId()).get();

        mongo.getMorphiaDatastore().save(user.account);
        this.repository.remove(user);
    }

}
