import java.awt.*;
import java.awt.event.*;


public class unidad3 {
    public static void main(String[] args) {

    }
}

/*
    Objetos graficos minimos:
        
        1_Contenedores basicos (objetos Containers):
            1_Frame = ventana principal (Con este solo usar WindowListener o WindowAdapter).
            2_Panel = una especie de div.

        2_Controles basicos (objetos Components):
            1_Button (ActionListener y MouseAdapter (mejor ActionListener que es generico y solo usa 
            la reescritura de actionPerformed)).
            2_TetxtField (una linea).
            3_TextArea (varias lineas).
            4-Label (texto "estatico").
    
    Todo objeto contenedores pueden llevar estructuras (layouts): 
        Estos tres son clases, o sea, se tienen que iniciar dando un espacio de memoria 'new' (siempre es mas facil que sea anonima)
        1_FlowLayout(): es el mas simple, pone todo en fila.
        2_BorderLayout(): NORTH,SOUTH,WEST,EAST.
        3_GridLayout(): tablas nxm.
*/

//Estructura minima mental:
//  1_ Se crea una ventana Frame
//  2_ Se le da un layout
//  3_ Se le pone componentes
//  4_ Se registran los Listeners
//  5_ Se hace visible la ventana
class Aplicacion1 {
    public Aplicacion1() {
        //  Ventana que tenga un boton que al tocar se imprima 'hola mundo':
        Frame ventana = new Frame("Idea de prueba");
        TextArea output = new TextArea();
        Button boton = new Button("Saludar ");

        ventana.setLayout(new FlowLayout());
        ventana.add(boton);
        ventana.add(output);

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText("Hola mundo");
            }
        });

        ventana.addWindowListener(new WindowAdapter() {
            @Override 
            public void windowClosing(WindowEvent e) {
                ventana.dispose();
                System.exit(0);
            }
        });

        ventana.pack();
        ventana.setVisible(true);
    }
}


/*
    Esta aplicacion tiene que tener 3 botones, existen 3 formas explicadas por la catedra para crear 
eventos en objetos y una mas que no explican pero es la mas sencilla:
    1_clase normal      <--- La voy a usar para el boton borrar
    2_clase local (dentro del metodo)   <--- La voy a usar para el boton 'taskCreator'
    3_clase anonima     <--- La voy a usar para el boton editar
    4_funcion lambda    <--- No la voy a usar para ninguno de los botones porque solo admite una sentencia
*/


class app extends Frame {
    private final Panel divCreator = new Panel();
    private final Panel list = new Panel();
    private final Button taskCreator = new Button("Agregar");
    private final TextField fieldTask = new TextField();

    public app() {
        super("titulo");
        setLayout(new BorderLayout());
        divCreator.setLayout(new BorderLayout());
        divCreator.add(fieldTask, BorderLayout.WEST);
        divCreator.add(taskCreator, BorderLayout.EAST);
        add(divCreator, BorderLayout.SOUTH);
        add(list, BorderLayout.CENTER);
        pack();
        setVisible(true);

        class CreateRow implements ActionListener {
            //  Esta es una clase local.
            @Override 
            public void actionPerformed(ActionEvent e) {
                String value = fieldTask.getText();
                Panel row = new Panel();
                Button deleter = new Button();
                Button updater = new Button();
                Label task = new Label();

                task.setText(value);
                row.setLayout(new FlowLayout());
                row.add(task);
                row.add(updater);
                row.add(deleter);
                list.add(row);

                deleter.addActionListener(new DeleteTask());
                updater.addActionListener(new ActionListener() {
                    //  Esta es una clase anonima.
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
            }
        }
        taskCreator.addActionListener(new CreateRow());
    }
}

class DeleteTask implements ActionListener { 
    //  Esta es una clase externa. 
    //  A traves de las jerarquias de objetos en java puedo hacer esto.
    //  Es bastante similar a la jerarquia del DOM en html y js. Pero js es muchisimo mejor.
    @Override 
    public void actionPerformed(ActionEvent e) {
        Component deleter = (Button) e.getSource();
        Container row = (Panel) deleter.getParent();
        Container list = (Panel) row.getParent();
        list.remove(row);
        list.revalidate();
        list.repaint();
    }
}