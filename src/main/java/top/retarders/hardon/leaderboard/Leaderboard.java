package top.retarders.hardon.leaderboard;

import top.retarders.hardon.leaderboard.api.LeaderboardPlayer;
import top.retarders.hardon.leaderboard.api.LeaderboardQuery;

import java.util.List;

public class Leaderboard {

    public final String name;
    private final LeaderboardQuery query;
    public List<LeaderboardPlayer> data;

    public Leaderboard(String name, LeaderboardQuery query) {
        this.name = name;
        this.query = query;
    }

    public void update() {
        this.data = this.query.execute().body();
    }

}
