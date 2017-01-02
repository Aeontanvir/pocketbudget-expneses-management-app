package bitmgroup.aeontanvir.com.pocketbudget.main;

/**
 * Created by aeon on 29 Oct, 2016.
 */

public class Budget {
    private int id;
    private String name;
    private String sources;
    private float amount;

    public Budget() {}

    public Budget(String name, String sources, float amount) {
        setName(name);
        setSources(sources);
        setAmount(amount);
    }
    public Budget(int id, String name, String sources, float amount) {
        setId(id);
        setName(name);
        setSources(sources);
        setAmount(amount);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
