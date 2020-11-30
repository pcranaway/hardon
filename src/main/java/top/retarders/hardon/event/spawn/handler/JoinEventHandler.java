package top.retarders.hardon.event.spawn.handler;

import me.lucko.helper.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

import java.util.function.Consumer;

public class JoinEventHandler implements Consumer<PlayerJoinEvent> {

    private final UserRepository repository = Helper.service(UserRepository.class).get();

    @Override
    public void accept(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = this.repository.find(player.getUniqueId()).get();
    }

}
