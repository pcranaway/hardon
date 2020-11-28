package top.retarders.hardon.command.admin;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import top.retarders.hardon.command.admin.handler.BuildModeCommandHandler;

public class AdminCommandsModule implements TerminableModule {

    @Override
    public void setup(TerminableConsumer consumer) {
        Commands.create()
                .assertPlayer()
                .assertOp()
                .description("Toggles build mode")
                .handler(new BuildModeCommandHandler())
                .registerAndBind(consumer, "build", "buildmode");
    }

}
