package top.retarders.hardon.event.connection;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.retarders.hardon.event.connection.handler.JoinEventHandler;
import top.retarders.hardon.event.connection.handler.QuitEventHandler;

public class ConnectionListener implements TerminableModule {
    @Override
    public void setup(TerminableConsumer consumer) {
        Events.subscribe(PlayerJoinEvent.class)
                .handler(new JoinEventHandler())
                .bindWith(consumer);

        Events.subscribe(PlayerQuitEvent.class)
                .handler(new QuitEventHandler())
                .bindWith(consumer);
    }
}
