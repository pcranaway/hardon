package top.retarders.hardon.sidebar.variable.impl;

import top.retarders.hardon.sidebar.variable.SidebarVariable;
import top.retarders.hardon.user.User;

public class StateVariable implements SidebarVariable {

    @Override
    public String getName() {
        return "state";
    }

    @Override
    public String getValue(User user) {
        return user.state.name();
    }

}
