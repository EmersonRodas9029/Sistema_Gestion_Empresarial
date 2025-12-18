package com.mycompany.GestionEmpresarial;

import com.mycompany.GestionEmpresarial.vista.FrmMenuPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmMenuPrincipal().setVisible(true);
        });
    }
}