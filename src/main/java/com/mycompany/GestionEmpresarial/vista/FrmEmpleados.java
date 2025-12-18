package com.mycompany.GestionEmpresarial.vista;

import com.mycompany.GestionEmpresarial.dao.EmpleadoDAO;
import com.mycompany.GestionEmpresarial.modelo.Empleado;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmEmpleados extends JFrame {
    EmpleadoDAO dao = new EmpleadoDAO();
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> cbDepartamentos;
    private JTextField txtPromedio, txtMax, txtMin;
    private JTextArea txtResumen;
    private JButton btnEliminar;

    public FrmEmpleados() {
        setTitle("Gestión de Empleados - Sistema Empresa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        initComponents();
        cargarDepartamentos();
        listar();
        mostrarDatos();
    }

    private void initComponents() {
        // --- Tabla de empleados ---
        modelo = new DefaultTableModel(new Object[]{
            "Emp No", "Apellido", "Oficio", "Director", "Fecha Alta", 
            "Salario", "Comisión", "Dept No", "Departamento"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(8).setPreferredWidth(120);
        
        // Agregar listener para selección de filas
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                habilitarBotonEliminar();
            }
        });
        
        JScrollPane spTabla = new JScrollPane(tabla);

        // --- Panel superior con filtro y botones ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel de filtro
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);
        
        cbDepartamentos = new JComboBox<>();
        cbDepartamentos.addItem("-- TODOS LOS DEPARTAMENTOS --");
        cbDepartamentos.addActionListener(e -> filtrar());

        filterPanel.add(new JLabel("Filtrar por departamento:"));
        filterPanel.add(cbDepartamentos);
        
        // Panel de botones de acción
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        
        btnEliminar = crearBoton("🗑️ Eliminar Empleado", new Color(231, 76, 60));
        JButton btnAgregar = crearBoton("➕ Agregar Empleado", new Color(46, 204, 113));
        JButton btnMenu = crearBoton("🏠 Menú Principal", new Color(52, 152, 219));
        
        // Acciones de los botones
        btnEliminar.addActionListener(e -> eliminarEmpleado());
        btnEliminar.setEnabled(false); // Inicialmente deshabilitado
        
        btnAgregar.addActionListener(e -> {
            new FrmAgregarEmpleado().setVisible(true);
            this.dispose();
        });
        
        btnMenu.addActionListener(e -> {
            new FrmMenuPrincipal().setVisible(true);
            this.dispose();
        });
        
        actionPanel.add(btnEliminar);
        actionPanel.add(btnAgregar);
        actionPanel.add(btnMenu);
        
        topPanel.add(filterPanel, BorderLayout.WEST);
        topPanel.add(actionPanel, BorderLayout.EAST);

        // --- Panel de métricas ---
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        statsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        txtPromedio = crearCampo();
        txtMax = crearCampo();
        txtMin = crearCampo();

        statsPanel.add(crearCard("💰 Salario Promedio", txtPromedio, new Color(52, 152, 219)));
        statsPanel.add(crearCard("⬆️ Salario Máximo", txtMax, new Color(46, 204, 113)));
        statsPanel.add(crearCard("⬇️ Salario Mínimo", txtMin, new Color(231, 76, 60)));

        // --- Resumen de empleados por departamento ---
        txtResumen = new JTextArea(6, 40);
        txtResumen.setEditable(false);
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResumen.setBorder(BorderFactory.createTitledBorder("📊 Resumen por Departamento"));
        JScrollPane spResumen = new JScrollPane(txtResumen);

        // --- Layout general ---
        Container c = getContentPane();
        c.setLayout(new BorderLayout(10, 10));
        c.add(topPanel, BorderLayout.NORTH);
        c.add(spTabla, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(statsPanel, BorderLayout.NORTH);
        bottom.add(spResumen, BorderLayout.CENTER);

        c.add(bottom, BorderLayout.SOUTH);
    }

    private JTextField crearCampo() {
        JTextField txt = new JTextField();
        txt.setEditable(false);
        txt.setHorizontalAlignment(SwingConstants.CENTER);
        txt.setFont(new Font("SansSerif", Font.BOLD, 14));
        txt.setBackground(Color.WHITE);
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
                if (boton.isEnabled()) {
                    boton.setBackground(color.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (boton.isEnabled()) {
                    boton.setBackground(color);
                }
            }
        });
        
        return boton;
    }

    private JPanel crearCard(String titulo, JTextField campo, Color colorFondo) {
        JPanel card = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(Color.WHITE);
        card.add(lbl, BorderLayout.NORTH);

        campo.setBorder(null);
        card.add(campo, BorderLayout.CENTER);
        card.setBackground(colorFondo);
        card.setBorder(new EmptyBorder(10, 10, 10, 10));
        return card;
    }

    private void cargarDepartamentos() {
        Map<String, String> res = dao.resumenPorDepartamento();
        cbDepartamentos.removeAllItems();
        cbDepartamentos.addItem("-- TODOS LOS DEPARTAMENTOS --");

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : res.entrySet()) {
            String dep = entry.getKey();
            String info = entry.getValue();
            cbDepartamentos.addItem(dep);
            sb.append(String.format("%-20s ➜ %s%n", dep, info));
        }
        txtResumen.setText(sb.toString());
    }

    private void listar() {
        List<Empleado> lista = dao.listar();
        modelo.setRowCount(0);
        for (Empleado e : lista) {
            modelo.addRow(new Object[]{
                e.getEmp_no(),
                e.getApellido(),
                e.getOficio(),
                e.getDr() != null ? e.getDr() : "",
                e.getFecha_alt(),
                String.format("%.2f", e.getSalario()),
                e.getComision() != null ? String.format("%.2f", e.getComision()) : "",
                e.getDept_no(),
                e.getDepartamento()
            });
        }
        btnEliminar.setEnabled(false); // Deshabilitar después de cargar
    }

    private void filtrar() {
        String sel = (String) cbDepartamentos.getSelectedItem();
        modelo.setRowCount(0);
        if (sel == null || sel.equals("-- TODOS LOS DEPARTAMENTOS --")) {
            listar();
        } else {
            List<Empleado> lista = dao.listarPorDepartamento(sel);
            for (Empleado e : lista) {
                modelo.addRow(new Object[]{
                    e.getEmp_no(),
                    e.getApellido(),
                    e.getOficio(),
                    e.getDr() != null ? e.getDr() : "",
                    e.getFecha_alt(),
                    String.format("%.2f", e.getSalario()),
                    e.getComision() != null ? String.format("%.2f", e.getComision()) : "",
                    e.getDept_no(),
                    e.getDepartamento()
                });
            }
        }
        btnEliminar.setEnabled(false); // Deshabilitar después de filtrar
    }

    private void mostrarDatos() {
        txtPromedio.setText(String.format("$%.2f", dao.salarioPromedio()));
        txtMax.setText(String.format("$%.2f", dao.salarioMaximo()));
        txtMin.setText(String.format("$%.2f", dao.salarioMinimo()));
    }
    
    private void habilitarBotonEliminar() {
        int filaSeleccionada = tabla.getSelectedRow();
        btnEliminar.setEnabled(filaSeleccionada != -1);
    }
    
    private void eliminarEmpleado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un empleado para eliminar", 
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener datos del empleado seleccionado
        int empNo = (int) modelo.getValueAt(filaSeleccionada, 0);
        String apellido = (String) modelo.getValueAt(filaSeleccionada, 1);
        String departamento = (String) modelo.getValueAt(filaSeleccionada, 8);
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar al empleado?\n\n" +
            "Número: " + empNo + "\n" +
            "Apellido: " + apellido + "\n" +
            "Departamento: " + departamento,
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = dao.eliminar(empNo);
            if (exito) {
                JOptionPane.showMessageDialog(this,
                    "Empleado eliminado exitosamente!\n" +
                    "Número: " + empNo + "\n" +
                    "Apellido: " + apellido,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Actualizar la vista
                listar();
                mostrarDatos();
                cargarDepartamentos(); // Actualizar resumen
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar el empleado.\n" +
                    "Puede que el empleado tenga registros relacionados.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}