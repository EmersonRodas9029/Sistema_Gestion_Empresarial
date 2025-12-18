package com.mycompany.GestionEmpresarial.modelo;

public class Empleado {
    private int emp_no;
    private String apellido;
    private String oficio;
    private Integer dr;
    private String fecha_alt;
    private double salario;
    private Double comision;
    private int dept_no;
    private String departamento; // Para mostrar en la vista

    // Getters y Setters
    public int getEmp_no() { return emp_no; }
    public void setEmp_no(int emp_no) { this.emp_no = emp_no; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getOficio() { return oficio; }
    public void setOficio(String oficio) { this.oficio = oficio; }

    public Integer getDr() { return dr; }
    public void setDr(Integer dr) { this.dr = dr; }

    public String getFecha_alt() { return fecha_alt; }
    public void setFecha_alt(String fecha_alt) { this.fecha_alt = fecha_alt; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public Double getComision() { return comision; }
    public void setComision(Double comision) { this.comision = comision; }

    public int getDept_no() { return dept_no; }
    public void setDept_no(int dept_no) { this.dept_no = dept_no; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}