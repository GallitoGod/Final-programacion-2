// LEAN EL README NO SEAN GILES (CONSEJO: USEN ALT+Z EN EL EDITOR)

// ===========================================================================================
// PROGRAMACION 2 - UNIDAD 3 GUIA DEFINITIVA SUPREMA 4K (INTERFACES GRAFICAS AWT Y EVENTOS)
// ===========================================================================================

/*
    PREGUNTAS REALES DE EXAMEN FINAL (UNIDAD 3 - AWT Y EVENTOS):
    
    1. ¿Cual es la diferencia entre un Component y un Container?
       R: Un Component (Componente) es un objeto grafico individual con el que el usuario interactua (Button, TextField, Label). Un Container (Contenedor) es un tipo especial de Componente que tiene la capacidad de agrupar y contener a otros componentes en su interior (Frame, Panel, Dialog).

    2. ¿Que es un LayoutManager y para que sirve?
       R: Es un administrador de diseño. Sirve para definir como se van a organizar y posicionar los componentes dentro de un contenedor, evitando tener que calcular coordenadas exactas (pixel por píxel). Ejemplos: FlowLayout, BorderLayout, GridLayout.

    3. ¿Que diferencia hay entre implementar un Listener y extender un Adapter? (PREGUNTA TRAMPA)
       R: Las interfaces 'Listener' (ej. WindowListener) te obligan a reescribir TODOS sus metodos por contrato, incluso los que no vas a usar (como windowOpened, windowIconified, etc.). 
       Las clases 'Adapter' (ej. WindowAdapter) son clases abstractas que ya implementan el Listener con metodos vacios. Al heredarlas, te permiten reescribir SOLAMENTE el evento que te interesa (ej. windowClosing), ahorrando muchisimas lineas de codigo basura.

    4. ¿Cuales son las formas de implementar el manejo de un evento en Java?
       R: Hay 4 formas principales: 
       - Clase normal (externa)
       - Clase local (dentro de un metodo)
       - Clase anonima (instanciada en el momento)
       - Expresiones Lambda (a partir de Java 8)
*/


import java.awt.*;
import java.awt.event.*;


public class unidad3 {
    public static void main(String[] args) {

        //si queres probar el ejemplo basico descomento esta linea
        //Aplicacacion1 appBasica = new Aplicacion1()
        // appBasica.setVisible(true);

        //aca ejecutamos la aplicacion  principal (To - Do List)
        app aplicacionPrincipal = new app();

        //BUENA PRACTICA: El main es quien decide cuando mostrar la ventana, no el constructor.
        aplicacionPrincipal.pack();
        aplicacionPrincipal.setVisible(true);
    }
}

// ===========================================================================================
// TEORIA DE OBJETOS GRAFICOS MINIMOS EN AWT
// ===========================================================================================
/*
    1. CONTENEDORES BASICOS (Heredan de Container):
        - Frame: Es la ventana principal del sistema (tiene barra con el titulo, botones de minimizar/cerrar). Con este se suele usar WindowListener o WindowAdapter para gestionar su cierre.
        - Panel: Es una especie de "div" invisible. Sirve agrupar componentes y aplicarles un Layout especifico.
        - Dialog: Es una ventana secundaria (modal). Toma el foco y bloquea la ventana principal hasta que se resuelva (ideal para alertas o confirmaciones).

    2. CONTROLES BASICOS (Heredan de Component):
        - Button: Boton clickeable (Se escucha con ActionListener).
        - TextField: Campo de texto de una sola linea (inputs).
        - TextArea: Campo de texto de multiples lineas (ideal para descripciones).
        - Label: Texto estatico en pantalla (etiquetas).
    
    3. ESTRUCTURAS DE ORDENAMIENTO (Layouts): 
        Son clases instanciables que dictan las reglas geometricas de los componentes.
        - FlowLayout(): Es el mas simple, pone todo en fila de izquierda a derecha.
        - BorderLayout(): Divide la pantalla en 5 zonas cardinales (NORTH, SOUTH, WEST, EAST, CENTER).
        - GridLayout(): Crea una matriz o cuadricula de tablas nxm (ej. calculadora).
    
    4. EL EFECTO ZOMBIE Y EL CIERRE DE VENTANAS:
        A diferencia de librerias más modernas, en AWT hacer clic en la "X" de la ventana NO cierra el programa por defecto. Si solo usas 'dispose()', la interfaz desaparece pero la Maquina Virtual de Java (JVM) sigue corriendo en segundo plano consumiendo RAM. Por eso ES OBLIGATORIO usar 'System.exit(0)' dentro del evento 'windowClosing' para matar el proceso.
*/


// ===========================================================================================
// EJEMPLO 1: ESTRUCTURA MENTAL MINIMA DE UNA VENTANA
// ===========================================================================================
/*
    Pasos para crear cualquier UI en Java:
    1. Se crea el contenedor base (Frame).
    2. Se le asigna un LayoutManager.
    3. Se instancian los componentes y se agregan (add).
    4. Se registran los Listeners (para darle vida a los botones).
    5. Se hace visible la ventana (setVisible).
*/

class Aplicacion1 {
    public Aplicacion1() {
        //ventana que tenga un boton que al tocar se imprima 'hola mundo':
        Frame ventana = new Frame("Idea de prueba basica");
        TextArea output = new TextArea();
        Button boton = new Button("Saludar");

        ventana.setLayout(new FlowLayout());
        ventana.add(boton);
        ventana.add(output);

        //se usa de CLASE ANONIMA para el evento del boton
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText("Hola mundo");
            }
        });
        
        //se usa de WindowAdapter para no tener que implementar los 7 metodos de WindowListener
        ventana.addWindowListener(new WindowAdapter() {
            @Override 
            public void windowClosing(WindowEvent e) {
                ventana.dispose(); //libera los recursos graficos
                System.exit(0);    //mata el proceso de la JVM
            }
        });

        ventana.pack(); //ajusta la ventana al tamaño de sus componentes
        ventana.setVisible(true);
    }
}


// ===========================================================================================
// EJEMPLO 2: APLICACION COMPLETA (TO-DO LIST) Y TIPOS DE LISTENERS
// ===========================================================================================

/*
    Esta aplicacion demuestra las 4 formas de atrapar eventos:
    1. Clase externa (Se usara para el boton 'Borrar')
    2. Clase local dentro del metodo (Se usara para el boton 'Agregar')
    3. Clase anonima (Se usara para el boton 'Actualizar')
    4. Funcion Lambda (Se usara en el Modal). Si se usan llaves { }, admite infinitas sentencias de codigo.
*/

class app extends Frame {
    //definimos los componentes a nivel de clase para que no mueran al terminar el constructor
    private final Panel divCreator = new Panel();
    private final Panel list = new Panel();
    private final Button taskCreator = new Button("Agregar");
    private final TextField fieldTask = new TextField(20);

    public app() {
        super("Gestor de Tareas"); //llama al constructor de Frame pasandole el titulo
        
        //configuracion de Layouts y armado del DOM visual
        setLayout(new BorderLayout());
        
        divCreator.setLayout(new BorderLayout());
        divCreator.add(fieldTask, BorderLayout.CENTER);
        divCreator.add(taskCreator, BorderLayout.EAST);
        
        add(divCreator, BorderLayout.SOUTH);
        add(list, BorderLayout.CENTER);//el panel 'list' ocupara el centro de la pantalla
        
        
        //'FORMA 1': CLASE LOCAL (Adentro del constructor)
        class CreateRow implements ActionListener {
            @Override 
            public void actionPerformed(ActionEvent e) {
                String value = fieldTask.getText();
                if(value.trim().isEmpty()) return; //evita agregar tareas vacias
                
                Panel row = new Panel();
                Button deleter = new Button("borrar");
                Button updater = new Button("actualizar");
                Label task = new Label();

                task.setText(value);
                row.setLayout(new FlowLayout());
                row.add(task);
                row.add(updater);
                row.add(deleter);
                
                list.add(row);
                //revalidate() y repaint() obligan a la interfaz a recalcular el Layout y dibujarse de nuevo
                list.revalidate();
                list.repaint();

                //'FORMA 2': CLASE EXTERNA
                deleter.addActionListener(new DeleteTask());


                //'FORMA 3': CLASE ANONIMA
                updater.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String taskToUpdate = task.getText();
                        DialogUpdater d = new DialogUpdater(app.this, "modal", taskToUpdate);
                        d.setVisible(true);//bloquea la ejecucion aca hasta que el modal se cierre

                        String newTask = d.getResult();
                        if (newTask != null) {
                            task.setText(newTask);
                        }
                    }
                });
            }
        }

        //le asignamos la clase local que acabamos de crear al boton
        taskCreator.addActionListener(new CreateRow());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Frame window = (Frame) e.getSource();
                window.dispose();
                System.exit(0);
            }
        });
    }
}


//'FORMA 2': CLASE EXTERNA (Reutilizable para cualquier boton que deba borrar su fila)
class DeleteTask implements ActionListener { 
    /*
        Al estar fuera de la clase 'app', no tiene acceso a las variables del sistema.
        Solucion: Utiliza la jerarquia grafica de AWT (Parecido a navegar por el DOM en JS).
        - e.getSource() obtiene quien disparo el evento (El boton)
        - getParent() obtiene el div (Panel) que contiene al boton
    */            
    @Override 
    public void actionPerformed(ActionEvent e) {
        Component deleter = (Button) e.getSource();
        Container row = (Panel) deleter.getParent();
        Container list = (Panel) row.getParent();
        
        list.remove(row);  //destruye la fila completa
        list.revalidate(); //recalcula graficos
        list.repaint();
    }
}

// CLASE DIALOGO (Modal para actualizar tareas)
class DialogUpdater extends Dialog {
    private final Button ok = new Button("OK");
    private final Button cancel = new Button("cancelar");
    private final Panel buttoner = new Panel();
    private final TextField newTask = new TextField(20);
    private String result = null;

    public DialogUpdater(Frame window, String title, String task) {
        super(window, title, true);//el 'true' lo hace MODAL (Bloquea la ventana principal)

        buttoner.setLayout(new FlowLayout());
        buttoner.add(ok);
        buttoner.add(cancel);
        
        newTask.setText(task);
        
        this.setLayout(new BorderLayout());
        this.add(newTask, BorderLayout.CENTER);
        this.add(buttoner, BorderLayout.SOUTH);

        //'FORMA 4': FUNCION LAMBDA (La forma moderna y mas limpia introducida en Java 8)usando { } podemos meter multiples sentencias
        ok.addActionListener(e -> {
            result = newTask.getText();
            dispose(); //cierra el modal
        });

        cancel.addActionListener(e -> {
            result = null;
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                result = null;
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(window); //centra el modal respecto a la ventana principal
    }

    //metodo publico para extraer la informacion una vez que el modal se cerro
    public String getResult() { 
        return result; 
    }
}