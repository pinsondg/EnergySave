package jmu.booze.energysave.model;

public class Department {

    private String departmentName;
    private int departmentPlace;

    public Department( String departmentName, int departmentPlace ) {
        this.departmentName = departmentName;
        this.departmentPlace = departmentPlace;
    }

    public int getDepartmentPlace() {
        return departmentPlace;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
