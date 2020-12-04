package top.retarders.hardon.command.leaderboard.handler;

import me.lucko.helper.Services;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import top.retarders.hardon.leaderboards.Leaderboard;
import top.retarders.hardon.leaderboards.LeaderboardsModule;
import top.retarders.hardon.leaderboards.gui.LeaderboardGui;

import java.util.Optional;
import java.util.stream.Collectors;

public class LeaderboardCommandHandler implements FunctionalCommandHandler<Player> {

    @Override
    public void handle(CommandContext<Player> context) {
        String leaderboardName = context.arg(0).parse(String.class).get();

        Optional<Leaderboard> hasLeaderboard = Services.get(LeaderboardsModule.class).get().leaderboards
                .stream()
                .filter(leaderboard -> leaderboard.name.replace(" ", "_").equalsIgnoreCase(leaderboardName))
                .findFirst();

        if (!hasLeaderboard.isPresent()) {
            context.reply(
                    "&cLeaderboard does not exist, try one of the following",

                    ChatColor.RED + String.join(", ", Services.get(LeaderboardsModule.class).get().leaderboards
                            .stream()
                            .map(leaderboard -> leaderboard.name.toLowerCase().replace(" ", "_"))
                            .collect(Collectors.toList()))
            );
            return;
        }

        Leaderboard leaderboard = hasLeaderboard.get();

        new LeaderboardGui(context.sender(), leaderboard).open();
    }

}