package entities;

import java.math.BigDecimal;

public class Request {

    private int id;
    private String description;

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    private String decision;
    private Boolean isApproved;
    private BigDecimal amount;
    private Person employee;
    private Person manager;
    public Request() {
        super();
    }

    public Request(int id, String description, Boolean isApproved, BigDecimal amount, Person employee,
                   Person manager) {
        super();
        this.id = id;
        this.description = description;
        this.isApproved = isApproved;
        this.amount = amount;
        this.employee = employee;
        this.manager = manager;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String type) {
        this.description = type;
    }
    public Boolean getIsApproved() {
        return isApproved;
    }
    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Person getEmployee() {
        return employee;
    }
    public void setEmployee(Person employee) {
        this.employee = employee;
    }
    public Person getManager() {
        return manager;
    }
    public void setManager(Person manager) {
        this.manager = manager;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
//		result = prime * result + ((servlets.manager == null) ? 0 : servlets.manager.hashCode());
        result = prime * result + id;
        result = prime * result + ((isApproved == null) ? 0 : isApproved.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Request other = (Request) obj;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (employee == null) {
            if (other.employee != null)
                return false;
        } else if (!employee.equals(other.employee))
            return false;
        if (id != other.id)
            return false;
        if (manager == null) {
            if (other.manager != null)
                return false;
        } else if (!manager.equals(other.manager))
            return false;
        if (isApproved == null) {
            if (other.isApproved != null)
                return false;
        } else if (!isApproved.equals(other.isApproved))
            return false;
        if (description == null) {
            return other.description == null;
        } else return description.equals(other.description);
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ", description=" + description + ", isApproved=" + isApproved + ", amount="
                + amount + ", employee=" + employee + ", servlets.manager=" + manager + "]";
    }

    public void setManager(int id) {
    }
}