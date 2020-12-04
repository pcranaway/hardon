package top.retarders.hardon.leaderboards;

import me.lucko.helper.Services;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import top.retarders.hardon.leaderboards.api.LeaderboardQuery;

import java.util.Arrays;
import java.util.List;

public class LeaderboardsModule implements TerminableModule {

    private Mongo mongo = Services.get(Mongo.class).get();

    public List<Leaderboard> leaderboards = Arrays.asList(
            new Leaderboard("Most Kills", new LeaderboardQuery("kills", 3, false)),
            new Leaderboard("Most Deaths", new LeaderboardQuery("deaths", 3, false)),
            new Leaderboard("Richest", new LeaderboardQuery("balance", 3, false)),
            new Leaderboard("Highest Killstreak", new LeaderboardQuery("highestKillstreak", 3, false))
    );

    @Override
    public void setup(TerminableConsumer consumer) {
//        Schedulers.async().runRepeating(this::refreshLeaderboards, 5 * 60 * 20L, 5 * 60 * 20L).bindWith(consumer);
        this.refreshLeaderboards();
    }

    public void refreshLeaderboards() {
        this.leaderboards.forEach(leaderboard -> {
            leaderboard.update();
            System.out.println("updated " + leaderboard.name);
        });
    }

}