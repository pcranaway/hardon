package top.retarders.hardon.leaderboard.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Query;
import retrofit2.http.GET;

import java.util.List;

public interface LeaderboardAPI {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8000").build();
    LeaderboardAPI instance = retrofit.create(LeaderboardAPI.class);

    @GET("/get_top_players")
    Call<List<LeaderboardPlayer>> getTopPlayers(@Query("by") String by, @Query("limit") int limit, @Query("reverse") boolean reverse);

}
