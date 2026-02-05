import java.awt.*;
import java.awt.event.*;

public class idea {
    
}


class SimpleAwtApp extends Frame implements ActionListener {

    private final TextField inputNombre;
    private final TextField inputEdad;
    private final TextArea output;

    private final Button btnSaludar;
    private final Button btnLimpiar;

    public SimpleAwtApp() {
        super("Simple AWT App");

        // Layout principal de la ventana
        setLayout(new BorderLayout(10, 10));

        // Panel superior: inputs
        Panel panelInputs = new Panel(new GridLayout(2, 2, 8, 8));
        panelInputs.add(new Label("Nombre:"));
        inputNombre = new TextField(20);
        panelInputs.add(inputNombre);

        panelInputs.add(new Label("Edad:"));
        inputEdad = new TextField(5);
        panelInputs.add(inputEdad);

        add(panelInputs, BorderLayout.NORTH);

        // Panel inferior: botones
        Panel panelBotones = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSaludar = new Button("Saludar");
        btnLimpiar = new Button("Limpiar");

        btnSaludar.addActionListener(this);
        btnLimpiar.addActionListener(this);

        panelBotones.add(btnSaludar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        // Centro: salida
        output = new TextArea("", 8, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        output.setEditable(false);
        add(output, BorderLayout.CENTER);

        // Cierre de ventana (WindowListener)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();      // libera recursos de la ventana
                System.exit(0); // termina el programa
            }
        });

        // Tamaño y visibilidad
        setSize(420, 260);
        setLocationRelativeTo(null); // centra la ventana (si tu Java lo soporta)
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnSaludar) {
            String nombre = inputNombre.getText().trim();
            String edadTxt = inputEdad.getText().trim();

            if (nombre.isEmpty()) {
                output.append("⚠ Ingresá un nombre.\n");
                return;
            }

            // Validación simple de edad
            int edad;
            try {
                edad = Integer.parseInt(edadTxt);
                if (edad < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                output.append("⚠ Edad inválida. Usá un entero >= 0.\n");
                return;
            }

            output.append("Hola, " + nombre + ". Tenés " + edad + " años.\n");
            inputNombre.requestFocus();

        } else if (src == btnLimpiar) {
            inputNombre.setText("");
            inputEdad.setText("");
            output.setText("");
            inputNombre.requestFocus();
        }
    }
}