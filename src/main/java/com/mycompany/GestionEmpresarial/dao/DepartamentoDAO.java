package com.mycompany.GestionEmpresarial.dao;

import com.mycompany.GestionEmpresarial.modelo.Departamento;
import com.mycompany.GestionEmpresarial.modelo.Conexion;
import java.sql.*;
import java.util.*;

public class DepartamentoDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Departamento> listar() {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT dept_no, dnombre, loc FROM departamentos ORDER BY dnombre";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Departamento d = new Departamento();
                d.setDept_no(rs.getInt("dept_no"));
                d.setDnombre(rs.getString("dnombre"));
                d.setLoc(rs.getString("loc"));
                lista.add(d);
            }
        } catch (Exception e) {
            System.out.println("Error listar departamentos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // Método para guardar un nuevo departamento
    public boolean guardar(int deptNo, String dnombre, String loc) {
        String sql = "INSERT INTO departamentos (dept_no, dnombre, loc) VALUES (?, ?, ?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, deptNo);
            ps.setString(2, dnombre);
            ps.setString(3, loc);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar departamento: " + e.getMessage());
            if (e.getErrorCode() == 1062) {
                System.out.println("El número de departamento ya existe");
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
            return false;
        }
    }
}