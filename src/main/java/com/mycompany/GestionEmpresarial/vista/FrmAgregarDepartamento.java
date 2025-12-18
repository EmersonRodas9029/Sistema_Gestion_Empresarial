package com.mycompany.GestionEmpresarial.vista;

import com.mycompany.GestionEmpresarial.dao.DepartamentoDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmAgregarDepartamento extends JFrame {
    private DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    
    private JTextField txtDeptNo, txtDnombre, txtLoc;
    
    public FrmAgregarDepartamento() {
        setTitle("Agregar Nuevo Departamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }
    
    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Título
        JLabel titleLabel = new JLabel("AGREGAR NUEVO DEPARTAMENTO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(155, 89, 182));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        
        // Campos del formulario
        txtDeptNo = crearCampo();
        txtDnombre = crearCampo();
        txtLoc = crearCampo();
        
        // Agregar campos al formulario
        formPanel.add(new JLabel("Número Departamento:"));
        formPanel.add(txtDeptNo);
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtDnombre);
        formPanel.add(new JLabel("Localidad:"));
        formPanel.add(txtLoc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnGuardar = crearBoton("💾 Guardar", new Color(46, 204, 113));
        JButton btnCancelar = crearBoton("❌ Cancelar", new Color(231, 76, 60));
        JButton btnMenu = crearBoton("🏠 Menú Principal", new Color(52, 152, 219));
        
        // Acciones de los botones
        btnGuardar.addActionListener(e -> guardarDepartamento());
        btnCancelar.addActionListener(e -> limpiarFormulario());
        btnMenu.addActionListener(e -> {
            new FrmMenuPrincipal().setVisible(true);
            this.dispose();
        });
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnMenu);
        
        // Agregar componentes al panel principal
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JTextField crearCampo() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Arial", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return txt;
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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
    
    private void guardarDepartamento() {
        try {
            // Validar campos obligatorios
            if (txtDeptNo.getText().trim().isEmpty() || 
                txtDnombre.getText().trim().isEmpty() ||
                txtLoc.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, 
                    "Por favor, complete todos los campos", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener valores
            int deptNo = Integer.parseInt(txtDeptNo.getText().trim());
            String dnombre = txtDnombre.getText().trim();
            String loc = txtLoc.getText().trim();
            
            // Guardar en la base de datos
            boolean exito = departamentoDAO.guardar(deptNo, dnombre, loc);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Departamento agregado exitosamente!\n" +
                    "Número: " + deptNo + "\n" +
                    "Nombre: " + dnombre + "\n" +
                    "Localidad: " + loc,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al guardar el departamento. Verifique que el número no exista.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese un número válido para el departamento", 
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar departamento: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtDeptNo.setText("");
        txtDnombre.setText("");
        txtLoc.setText("");
        txtDeptNo.requestFocus();
    }
}