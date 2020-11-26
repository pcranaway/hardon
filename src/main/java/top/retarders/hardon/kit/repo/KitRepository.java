package top.retarders.hardon.kit.repo;

import me.lucko.helper.Helper;
import me.lucko.helper.mongo.Mongo;
import me.lucko.helper.mongo.external.morphia.Datastore;
import top.retarders.hardon.kit.Kit;
import top.retarders.hardon.repository.Repository;

import java.util.ArrayList;
import java.util.Optional;

public class KitRepository implements Repository<Kit, String> {

    public final ArrayList<Kit> kits;
    private final Datastore datastore;

    public KitRepository() {
        this.kits = new ArrayList<>();
        this.datastore = Helper.service(Mongo.class).get().getMorphiaDatastore();
    }

    @Override
    public boolean put(Kit kit) {
        return this.kits.add(kit);
    }

    @Override
    public boolean remove(Kit kit) {
        return this.kits.remove(kit);
    }

    @Override
    public Optional<Kit> find(String identifier) {
        return this.kits.stream().filter(kit -> kit.name.equalsIgnoreCase(identifier)).findFirst();
    }

    public void loadKits() {
        this.kits.addAll(this.datastore.find(Kit.class).asList());
    }

    public void saveKit(Kit kit) {
        this.datastore.save(kit);
    }

    public void deleteKit(Kit kit) {
        this.datastore.delete(kit);
    }
}
