package top.retarders.hardon.command.leaderboard;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import top.retarders.hardon.command.leaderboard.handler.LeaderboardCommandHandler;

public class LeaderboardsCommandsModule implements TerminableModule {

    @Override
    public void setup(TerminableConsumer consumer) {
        Commands.create()
                .assertPlayer()
                .assertUsage("<leaderboard>")
                .description("Shows a leaderboard")
                .handler(new LeaderboardCommandHandler())
                .registerAndBind(consumer, "lb", "leaderboard");
    }

}