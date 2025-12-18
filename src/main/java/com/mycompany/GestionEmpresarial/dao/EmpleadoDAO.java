package com.mycompany.GestionEmpresarial.dao;

import com.mycompany.GestionEmpresarial.modelo.*;
import java.sql.*;
import java.util.*;

public class EmpleadoDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // 1️⃣ Mostrar todos los empleados (CON TODOS LOS CAMPOS)
    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT e.emp_no, e.apellido, e.oficio, e.dr, e.fecha_alt, "
                   + "e.salario, e.comision, e.dept_no, d.dnombre AS departamento "
                   + "FROM empleado e INNER JOIN departamentos d "
                   + "ON e.dept_no = d.dept_no";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setEmp_no(rs.getInt("emp_no"));
                e.setApellido(rs.getString("apellido"));
                e.setOficio(rs.getString("oficio"));
                e.setDr(rs.getInt("dr"));
                if (rs.wasNull()) e.setDr(null);
                e.setFecha_alt(rs.getString("fecha_alt"));
                e.setSalario(rs.getDouble("salario"));
                e.setComision(rs.getDouble("comision"));
                if (rs.wasNull()) e.setComision(null);
                e.setDept_no(rs.getInt("dept_no"));
                e.setDepartamento(rs.getString("departamento"));
                lista.add(e);
            }
        } catch (Exception e) {
            System.out.println("Error listar: " + e.getMessage());
        }
        return lista;
    }

    // 2️⃣ Filtrar empleados por departamento
    public List<Empleado> listarPorDepartamento(String departamento) {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT e.emp_no, e.apellido, e.oficio, e.dr, e.fecha_alt, "
                   + "e.salario, e.comision, e.dept_no, d.dnombre AS departamento "
                   + "FROM empleado e INNER JOIN departamentos d "
                   + "ON e.dept_no = d.dept_no "
                   + "WHERE d.dnombre = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, departamento);
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setEmp_no(rs.getInt("emp_no"));
                e.setApellido(rs.getString("apellido"));
                e.setOficio(rs.getString("oficio"));
                e.setDr(rs.getInt("dr"));
                if (rs.wasNull()) e.setDr(null);
                e.setFecha_alt(rs.getString("fecha_alt"));
                e.setSalario(rs.getDouble("salario"));
                e.setComision(rs.getDouble("comision"));
                if (rs.wasNull()) e.setComision(null);
                e.setDept_no(rs.getInt("dept_no"));
                e.setDepartamento(rs.getString("departamento"));
                lista.add(e);
            }
        } catch (Exception e) {
            System.out.println("Error filtrar: " + e.getMessage());
        }
        return lista;
    }

    // 3️⃣ Salario medio de todos los empleados
    public double salarioPromedio() {
        String sql = "SELECT AVG(salario) AS promedio FROM empleado";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("promedio");
        } catch (Exception e) {
            System.out.println("Error promedio: " + e.getMessage());
        }
        return 0;
    }

    // 4️⃣ Salario mayor
    public double salarioMaximo() {
        String sql = "SELECT MAX(salario) AS maximo FROM empleado";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("maximo");
        } catch (Exception e) {
            System.out.println("Error maximo: " + e.getMessage());
        }
        return 0;
    }

    // 5️⃣ Salario menor
    public double salarioMinimo() {
        String sql = "SELECT MIN(salario) AS minimo FROM empleado";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("minimo");
        } catch (Exception e) {
            System.out.println("Error minimo: " + e.getMessage());
        }
        return 0;
    }

    // 6️⃣ Cantidad de empleados y salario promedio por departamento
    public Map<String, String> resumenPorDepartamento() {
        Map<String, String> datos = new LinkedHashMap<>();
        String sql = "SELECT d.dnombre AS departamento, COUNT(e.emp_no) AS cantidad, AVG(e.salario) AS promedio "
                   + "FROM empleado e INNER JOIN departamentos d "
                   + "ON e.dept_no = d.dept_no "
                   + "GROUP BY d.dept_no, d.dnombre";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String dep = rs.getString("departamento");
                String info = "Empleados: " + rs.getInt("cantidad")
                            + " | Salario Promedio: " + String.format("%.2f", rs.getDouble("promedio"));
                datos.put(dep, info);
            }
        } catch (Exception e) {
            System.out.println("Error resumen: " + e.getMessage());
        }
        return datos;
    }

    // Método para obtener los nombres de departamentos para el ComboBox
    public List<String> obtenerNombresDepartamentos() {
        List<String> departamentos = new ArrayList<>();
        String sql = "SELECT dnombre FROM departamentos ORDER BY dnombre";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                departamentos.add(rs.getString("dnombre"));
            }
        } catch (Exception e) {
            System.out.println("Error obtener departamentos: " + e.getMessage());
        }
        return departamentos;
    }

    // MÉTODO PARA GUARDAR NUEVO EMPLEADO
    public boolean guardar(int empNo, String apellido, String oficio, Integer dr, 
                          String fechaAlt, double salario, Double comision, int deptNo) {
        String sql = "INSERT INTO empleado (emp_no, apellido, oficio, dr, fecha_alt, salario, comision, dept_no) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empNo);
            ps.setString(2, apellido);
            ps.setString(3, oficio);
            if (dr != null) {
                ps.setInt(4, dr);
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setString(5, fechaAlt);
            ps.setDouble(6, salario);
            if (comision != null) {
                ps.setDouble(7, comision);
            } else {
                ps.setNull(7, Types.DOUBLE);
            }
            ps.setInt(8, deptNo);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar empleado: " + e.getMessage());
            if (e.getErrorCode() == 1062) {
                System.out.println("El número de empleado ya existe");
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
            return false;
        }
    }
    // MÉTODO PARA ELIMINAR EMPLEADO
    public boolean eliminar(int empNo) {
        String sql = "DELETE FROM empleado WHERE emp_no = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empNo);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error general al eliminar: " + e.getMessage());
            return false;
        }
    }

    // MÉTODO PARA BUSCAR EMPLEADO POR NÚMERO (útil para verificar antes de eliminar)
    public Empleado buscarPorNumero(int empNo) {
        String sql = "SELECT e.emp_no, e.apellido, e.oficio, e.dr, e.fecha_alt, "
                   + "e.salario, e.comision, e.dept_no, d.dnombre AS departamento "
                   + "FROM empleado e INNER JOIN departamentos d "
                   + "ON e.dept_no = d.dept_no "
                   + "WHERE e.emp_no = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                Empleado e = new Empleado();
                e.setEmp_no(rs.getInt("emp_no"));
                e.setApellido(rs.getString("apellido"));
                e.setOficio(rs.getString("oficio"));
                e.setDr(rs.getInt("dr"));
                if (rs.wasNull()) e.setDr(null);
                e.setFecha_alt(rs.getString("fecha_alt"));
                e.setSalario(rs.getDouble("salario"));
                e.setComision(rs.getDouble("comision"));
                if (rs.wasNull()) e.setComision(null);
                e.setDept_no(rs.getInt("dept_no"));
                e.setDepartamento(rs.getString("departamento"));
                return e;
            }
        } catch (Exception e) {
            System.out.println("Error buscar empleado: " + e.getMessage());
        }
        return null;
    }
}

