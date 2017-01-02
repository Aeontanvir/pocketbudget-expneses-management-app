package bitmgroup.aeontanvir.com.pocketbudget.main;

/**
 * Created by aeon on 29 Oct, 2016.
 */

public class Expense {
    private int id;
    private int budgetId;
    private String name;
    private String details;
    private float amount;

    public Expense() {}

    public Expense(String name, String details, float amount) {
        setName(name);
        setDetails(details);
        setAmount(amount);
    }

    public Expense(int budgetId, String name, String details, float amount) {
        setBudgetId(budgetId);
        setName(name);
        setDetails(details);
        setAmount(amount);
    }

    public Expense(int id, int budgetId, String name, String details, float amount) {
        setId(id);
        setBudgetId(budgetId);
        setName(name);
        setDetails(details);
        setAmount(amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
