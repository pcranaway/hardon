package top.retarders.hardon;

import me.lucko.helper.Schedulers;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.mongo.MongoDatabaseCredentials;
import me.lucko.helper.mongo.MongoProvider;
import me.lucko.helper.mongo.plugin.HelperMongo;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import top.retarders.hardon.command.KitsCommandsModule;
import top.retarders.hardon.event.connection.ConnectionListener;
import top.retarders.hardon.event.statistics.StatisticsListener;
import top.retarders.hardon.kit.repo.KitRepository;
import top.retarders.hardon.user.repo.UserRepository;

@Plugin(
        name = "hardon",
        version = "1.0",
        description = "kitpvp at its finest",
        authors = "pcranaway",
        website = "https://retarders.top",
        hardDepends = {
                "helper",
                "helper-mongo"
        }
)
public class HardonPlugin extends ExtendedJavaPlugin implements MongoProvider {

    private MongoDatabaseCredentials globalCredentials;
    private Mongo globalDataSource;

    @Override
    protected void enable() {
        // load config
        this.saveDefaultConfig();

        // connect to database
        this.globalCredentials = MongoDatabaseCredentials.fromConfig(this.getConfig());
        this.globalDataSource = this.getMongo(this.globalCredentials);
        this.globalDataSource.bindWith(this);

        // kinda di
        this.provideService(MongoProvider.class, this);
        this.provideService(MongoDatabaseCredentials.class, this.globalCredentials);
        this.provideService(Mongo.class, this.globalDataSource);

        this.provideService(UserRepository.class, new UserRepository());
        this.provideService(KitRepository.class, new KitRepository());

        // load kits
        this.getService(KitRepository.class).loadKits();

        // register listeners
        this.bindModule(new ConnectionListener());
        this.bindModule(new StatisticsListener());

        // register commands
        this.bindModule(new KitsCommandsModule());

        // schedule accounts save task every 15 seconds (not sure if that's a bad idea)
        Schedulers.async().runRepeating(() -> {
            this.getService(UserRepository.class).users.forEach(user -> this.globalDataSource.getMorphiaDatastore().save(user.account));
        }, 15 * 20L, 15 * 20L);
    }

    @Override
    public Mongo getMongo() {
        return this.globalDataSource;
    }

    @Override
    public Mongo getMongo(MongoDatabaseCredentials mongoDatabaseCredentials) {
        return new HelperMongo(mongoDatabaseCredentials);
    }

    @Override
    public MongoDatabaseCredentials getGlobalCredentials() {
        return this.globalCredentials;
    }
}
