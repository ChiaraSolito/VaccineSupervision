package Model;

public class RiskFactor {

    private final String name;
    private final String description;
    private final int risk_level;

    public RiskFactor(String name, String description, int risk_level) {
        this.name = name;
        this.description = description;
        this.risk_level = risk_level;
    }

    public String getName(){
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRisk_level() {
        return risk_level;
    }


}
