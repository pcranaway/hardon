package top.retarders.hardon.event.admin;

import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.ChatColor;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Arrays;

public class AdminListener implements TerminableModule {

    @Override
    public void setup(TerminableConsumer terminableConsumer) {
        Events.subscribe(SignChangeEvent.class)
                .filter(event -> event.getPlayer().isOp())
                .filter(event -> Arrays.stream(event.getLines()).anyMatch(line -> line.toLowerCase().contains("[refill]")))
                .handler(event -> event.setLine(0, ChatColor.RED + "[Refill]"));
    }

}