package io.github.jefflegendpower.cbpcore.sql;

import io.github.jefflegendpower.cbpcore.CBPCore;

public abstract class AbstractSQLControl {

    protected final CBPCore plugin = CBPCore.getPlugin(CBPCore.class);

    public AbstractSQLControl() {
        this.createTable();
    }

    public abstract void createTable();
}