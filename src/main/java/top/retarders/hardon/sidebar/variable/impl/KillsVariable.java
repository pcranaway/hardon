package top.retarders.hardon.sidebar.variable.impl;

import top.retarders.hardon.sidebar.variable.SidebarVariable;
import top.retarders.hardon.user.User;

public class KillsVariable implements SidebarVariable {

    @Override
    public String getName() {
        return "kills";
    }

    @Override
    public String getValue(User user) {
        return String.valueOf(user.account.kills);
    }

}
