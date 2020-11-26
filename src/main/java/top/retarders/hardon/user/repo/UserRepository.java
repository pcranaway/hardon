package top.retarders.hardon.user.repo;

import top.retarders.hardon.repository.Repository;
import top.retarders.hardon.user.User;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements Repository<User, UUID> {

    public final ArrayList<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public boolean put(User user) {
        return this.users.add(user);
    }

    @Override
    public boolean remove(User user) {
        return this.users.remove(user);
    }

    @Override
    public Optional<User> find(UUID identifier) {
        return this.users.stream().filter(user -> user.uuid == identifier).findFirst();
    }
}
