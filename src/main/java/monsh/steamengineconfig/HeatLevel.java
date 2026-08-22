package monsh.steamengineconfig;

public enum HeatLevel {
    DISABLED(-1),
    PASSIVE(0),
    ACTIVE(1);

    public final int heatValue;
    HeatLevel(int v) { this.heatValue = v; }
}
