package top.retarders.hardon.event.connection.handler;

import me.lucko.helper.Helper;
import me.lucko.helper.mongo.Mongo;
import org.bukkit.event.player.PlayerQuitEvent;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

import java.util.function.Consumer;

public class QuitEventHandler implements Consumer<PlayerQuitEvent> {

    private final UserRepository repository = Helper.service(UserRepository.class).get();
    private final Mongo mongo = Helper.service(Mongo.class).get();

    @Override
    public void accept(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        User user = this.repository.find(event.getPlayer().getUniqueId()).get();

        mongo.getMorphiaDatastore().save(user.account);
        this.repository.remove(user);
    }

}
