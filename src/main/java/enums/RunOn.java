package enums;

import java.util.Arrays;

public enum RunOn {
    LOCAL("local", null),
    GRID("grid", "http://localhost:4444"),
    PERFECTO("perfecto", ""),
    AWS("aws", ""),
    INVALID("Invalid run mode", null);

    private final String name;
    private final String urlHub;

    RunOn(String name, String urlHub) {
        this.name = name;
        this.urlHub = urlHub;
    }

    public static RunOn fromName(String name) {
        return Arrays.stream(RunOn.values())
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(INVALID);
    }

    public boolean hasUrlHub() {
        return this.urlHub != null;
    }

    public String getName() {
        return name;
    }

    public String getUrlHub() {
        return urlHub;
    }
}
