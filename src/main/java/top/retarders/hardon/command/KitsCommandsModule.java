package top.retarders.hardon.command;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import top.retarders.hardon.command.handler.KitCommandHandler;

public class KitsCommandsModule implements TerminableModule {
    @Override
    public void setup(TerminableConsumer consumer) {
        Commands.create()
                .assertPlayer()
                .assertOp()
                .handler(new KitCommandHandler())
                .registerAndBind(consumer, "kit", "kits");
    }
}
