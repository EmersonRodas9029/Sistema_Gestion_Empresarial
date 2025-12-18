package com.mycompany.GestionEmpresarial.vista;

import com.mycompany.GestionEmpresarial.dao.EmpleadoDAO;
import com.mycompany.GestionEmpresarial.dao.DepartamentoDAO;
import com.mycompany.GestionEmpresarial.modelo.Departamento;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class FrmAgregarEmpleado extends JFrame {
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    
    private JTextField txtEmpNo, txtApellido, txtOficio, txtDr, txtFechaAlt, txtSalario, txtComision;
    private JComboBox<Departamento> cbDepartamento;
    
    public FrmAgregarEmpleado() {
        setTitle("Agregar Nuevo Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
        cargarDepartamentos();
    }
    
    private void initComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Título
        JLabel titleLabel = new JLabel("AGREGAR NUEVO EMPLEADO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 82, 165));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        
        // Campos del formulario
        txtEmpNo = crearCampo();
        txtApellido = crearCampo();
        txtOficio = crearCampo();
        txtDr = crearCampo();
        txtFechaAlt = crearCampo();
        txtSalario = crearCampo();
        txtComision = crearCampo();
        cbDepartamento = new JComboBox<>();
        
        // Placeholders
        txtFechaAlt.setText("YYYY-MM-DD");
        txtDr.setText("(Opcional)");
        txtComision.setText("(Opcional)");
        
        // Agregar campos al formulario
        formPanel.add(new JLabel("Número Empleado:"));
        formPanel.add(txtEmpNo);
        formPanel.add(new JLabel("Apellido:"));
        formPanel.add(txtApellido);
        formPanel.add(new JLabel("Oficio:"));
        formPanel.add(txtOficio);
        formPanel.add(new JLabel("Director (DR):"));
        formPanel.add(txtDr);
        formPanel.add(new JLabel("Fecha Alta:"));
        formPanel.add(txtFechaAlt);
        formPanel.add(new JLabel("Salario:"));
        formPanel.add(txtSalario);
        formPanel.add(new JLabel("Comisión:"));
        formPanel.add(txtComision);
        formPanel.add(new JLabel("Departamento:"));
        formPanel.add(cbDepartamento);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnGuardar = crearBoton("💾 Guardar", new Color(46, 204, 113));
        JButton btnCancelar = crearBoton("❌ Cancelar", new Color(231, 76, 60));
        JButton btnMenu = crearBoton("🏠 Menú Principal", new Color(52, 152, 219));
        
        // Acciones de los botones
        btnGuardar.addActionListener(e -> guardarEmpleado());
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
    
    private void cargarDepartamentos() {
        List<Departamento> departamentos = departamentoDAO.listar();
        cbDepartamento.removeAllItems();
        for (Departamento dep : departamentos) {
            cbDepartamento.addItem(dep);
        }
    }
    
    private void guardarEmpleado() {
        try {
            // Validar campos obligatorios
            if (txtEmpNo.getText().trim().isEmpty() || 
                txtApellido.getText().trim().isEmpty() ||
                txtOficio.getText().trim().isEmpty() ||
                txtFechaAlt.getText().trim().isEmpty() ||
                txtSalario.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, 
                    "Por favor, complete todos los campos obligatorios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener valores
            int empNo = Integer.parseInt(txtEmpNo.getText().trim());
            String apellido = txtApellido.getText().trim();
            String oficio = txtOficio.getText().trim();
            Integer dr = txtDr.getText().trim().isEmpty() || txtDr.getText().equals("(Opcional)") ? 
                        null : Integer.parseInt(txtDr.getText().trim());
            String fechaAlt = txtFechaAlt.getText().trim();
            double salario = Double.parseDouble(txtSalario.getText().trim());
            Double comision = txtComision.getText().trim().isEmpty() || txtComision.getText().equals("(Opcional)") ? 
                            null : Double.parseDouble(txtComision.getText().trim());
            Departamento dep = (Departamento) cbDepartamento.getSelectedItem();
            int deptNo = dep.getDept_no();
            
            // Guardar en la base de datos
            boolean exito = empleadoDAO.guardar(empNo, apellido, oficio, dr, fechaAlt, salario, comision, deptNo);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Empleado agregado exitosamente!\n" +
                    "Número: " + empNo + "\n" +
                    "Apellido: " + apellido + "\n" +
                    "Departamento: " + dep.getDnombre(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al guardar el empleado. Verifique que el número no exista.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese valores numéricos válidos", 
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar empleado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtEmpNo.setText("");
        txtApellido.setText("");
        txtOficio.setText("");
        txtDr.setText("(Opcional)");
        txtFechaAlt.setText("YYYY-MM-DD");
        txtSalario.setText("");
        txtComision.setText("(Opcional)");
        if (cbDepartamento.getItemCount() > 0) {
            cbDepartamento.setSelectedIndex(0);
        }
        txtEmpNo.requestFocus();
    }
}