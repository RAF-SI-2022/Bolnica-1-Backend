package raf.bolnica1.employees.exceptions.employee;

public class EmployeePasswordException extends RuntimeException{

    public EmployeePasswordException(String message) {
        super(message);
    }

    public EmployeePasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
