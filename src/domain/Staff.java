package domain;

import java.io.Serializable;

public class Staff implements Serializable {
    
    private final String employeeId;
    private final String name;
    private final String role;

    public Staff(final String employeeId, final String name, final String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
    }

    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getRole() { return role; }
}
