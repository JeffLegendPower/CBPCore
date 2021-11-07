package io.github.jefflegendpower.cbpcore.modes;

public enum Mode {
    FIREBALL_JUMP("fireball_jump"),
    TNT_JUMP("tnt_jump"),
    SPEED_CLUTCH("speed_clutch"),
    KNOCKBACK_CLUTCH("knockback_clutch"),
    BLOCK_IN("block_in"),
    ;

    private final String alias;

    Mode(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
