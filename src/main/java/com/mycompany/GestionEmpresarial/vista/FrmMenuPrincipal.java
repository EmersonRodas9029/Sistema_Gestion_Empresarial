package com.mycompany.GestionEmpresarial.vista;

import javax.swing.*;
import java.awt.*;

public class FrmMenuPrincipal extends JFrame {
    
    public FrmMenuPrincipal() {
        setTitle("Sistema de Gestión Empresarial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }
    
    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Título
        JLabel titleLabel = new JLabel("SISTEMA DE GESTIÓN EMPRESARIAL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 82, 165));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        // Botones
        JButton btnEmpleados = crearBoton("👥 Gestión de Empleados", new Color(52, 152, 219));
        JButton btnAgregarEmpleado = crearBoton("➕ Agregar Empleado", new Color(46, 204, 113));
        JButton btnAgregarDepartamento = crearBoton("🏢 Agregar Departamento", new Color(155, 89, 182));
        JButton btnSalir = crearBoton("🚪 Salir", new Color(149, 165, 166));
        
        // Acciones de los botones
        btnEmpleados.addActionListener(e -> {
            new FrmEmpleados().setVisible(true);
            this.dispose();
        });
        
        btnAgregarEmpleado.addActionListener(e -> {
            new FrmAgregarEmpleado().setVisible(true);
            this.dispose();
        });
        
        btnAgregarDepartamento.addActionListener(e -> {
            new FrmAgregarDepartamento().setVisible(true);
            this.dispose();
        });
        
        btnSalir.addActionListener(e -> {
            System.exit(0);
        });
        
        // Agregar componentes
        buttonPanel.add(btnEmpleados);
        buttonPanel.add(btnAgregarEmpleado);
        buttonPanel.add(btnAgregarDepartamento);
        buttonPanel.add(btnSalir);
        
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
}