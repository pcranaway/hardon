package top.retarders.hardon.leaderboards.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import me.lucko.helper.Services;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LeaderboardQuery {

    private static OkHttpClient client = new OkHttpClient();
    private static JsonParser jsonParser = Services.get(JsonParser.class).get();

    public final String by;
    public final int limit;
    public final boolean reverse;

    public LeaderboardQuery(String by, int limit, boolean reverse) {
        this.by = by;
        this.limit = limit;
        this.reverse = reverse;
    }

    public JsonArray execute() {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("0.0.0.0")
                .port(8000)
                .addPathSegment("get_top_players")
                .addQueryParameter("by", this.by)
                .addQueryParameter("limit", String.valueOf(this.limit))
                .addQueryParameter("reverse", String.valueOf(this.reverse))
                .build();

        Request request = new Request.Builder().url(httpUrl).build();

        try(Response response = this.client.newCall(request).execute()) {

            return jsonParser.parse(response.body().string()).getAsJsonArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
