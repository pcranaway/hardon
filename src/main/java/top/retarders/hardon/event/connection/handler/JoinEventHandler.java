package top.retarders.hardon.event.connection.handler;

import me.lucko.helper.Helper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import top.retarders.hardon.sidebar.SidebarModule;
import top.retarders.hardon.user.User;
import top.retarders.hardon.user.repo.UserRepository;

import java.util.function.Consumer;

public class JoinEventHandler implements Consumer<PlayerJoinEvent> {

    private UserRepository repository = Helper.service(UserRepository.class).get();
    private SidebarModule sidebarModule = Helper.service(SidebarModule.class).get();

    @Override
    public void accept(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        player.teleport(Helper.world("world").get().getSpawnLocation());

        User user = new User(player.getUniqueId());
        this.repository.put(user);

        user.loadAccount();

        this.sidebarModule.apply(user);
    }

}
