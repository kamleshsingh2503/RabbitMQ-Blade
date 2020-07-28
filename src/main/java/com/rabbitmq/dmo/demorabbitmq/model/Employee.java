package com.rabbitmq.dmo.demorabbitmq.model;

import java.io.Serializable;

public class Employee implements Serializable{
   
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private String empId;

    public Employee(String name, String empId) {
        this.name = name;
        this.empId = empId;
    }

    public Employee() {
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the empId
     */
    public String getEmpId() {
        return empId;
    }

    @Override
	public String toString() {
		return "Employee [empName=" + name + ", empId=" + empId + "]";
	}

    /**
     * @param empId the empId to set
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

}