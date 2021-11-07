package io.github.jefflegendpower.cbpcore.sql;

import io.github.jefflegendpower.cbpcore.CBPCore;

public abstract class SQLControl extends Thread {

    protected final CBPCore plugin = CBPCore.getPlugin(CBPCore.class);

    public SQLControl() {
        this.createTable();
    }

    public abstract void createTable();
}