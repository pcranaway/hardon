package top.retarders.hardon.sidebar.variable.impl;

import top.retarders.hardon.sidebar.variable.SidebarVariable;
import top.retarders.hardon.user.User;

public class DeathsVariable implements SidebarVariable {

    @Override
    public String getName() {
        return "deaths";
    }

    @Override
    public String getValue(User user) {
        return String.valueOf(user.account.deaths);
    }

}
