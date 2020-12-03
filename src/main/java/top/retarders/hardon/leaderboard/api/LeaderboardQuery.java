package top.retarders.hardon.leaderboard.api;

import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class LeaderboardQuery {

    public final String by;
    public final int limit;
    public final boolean reverse;

    public LeaderboardQuery(String by, int limit, boolean reverse) {
        this.by = by;
        this.limit = limit;
        this.reverse = reverse;
    }

    public Response<List<LeaderboardPlayer>> execute() {
        try {
            return LeaderboardAPI.instance.getTopPlayers(this.by, this.limit, this.reverse).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
