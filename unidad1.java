//LEAN EL README NO SEAN GILES (CONSEJO USEN ALT+Z EN EL EDITOR )

// ===========================================================================================
// PROGRAMACION 2 - UNIDAD 1 GUIA DEFINITIVA SUPREMA 4K 360Hz (TEORIA Y PRACTICA)
// ===========================================================================================


/*
    PREGUNTAS REALES DE EXAMEN FINAL(UNIDAD 1 - POO):
    1. ¿Que es el encapsulamiento y el ocultamiento?
        R: El ocultamiento lo que busca es crear una interfaz limpia para las clases externas que interactuen con nuestra clase, que puedan utilizarla sin saber como se implementa por dentro.
        
        El encapsulamiento es una herramienta del ocultamiento (usando modificadores de accespo public, protected y private.) es lo que permite lograr ese ocultamiento.

    
    2. ¿Ques es una clase abstracta y para que sirve?
        R: Representa un concepto general base para instanciar objetos variados que comparten cualidades y comportamiento similares. 
        
        Restriccion clave es que no puede ser instanciada directamente y puede tener tanto métodos concretos (con código) como métodos abstractos (sin código). Si posee métodos abstractos, obliga por contrato a sus subclases(clases hijas) a implementarlos.
        
        No es obligatorio que posea métodos abstractos para declararse como tal."

    
    3. ¿Que es y para que sirve una interfaz?
        R: Define un contrato o "linea" sobre que debe hacer un objeto, sin definir el "como". Sirve para garantizar comportamiento comunes en clases distintas y para solucionar el problema de la falta de herenica multiple en java.


    4. ¿Toda clase posee un constructor?
        R: Si, si no le escribrimos uno, el compilador proporciona y usa un constructor vacio por defecto.


    5. ¿Se puede y es recomendable hacer un constructor en clases abstractas?
        R: Si, es muy recomendable si la clase abstracta tiene atributos propios. Asi, las subclases pueden inicializarlos pasandole los datos mediante la funcion super().

  
    6. ¿Que es la sobrecarga de metodos?¿y de constructores?¿que pasa si tengo 2 contructores con parametros exactamente iguales?
        R: Es cuando tenemos 2 o mas metodos(o constructores) con el mismo nombre. El compilador los diferencia por la cantidad y tipo de parametros,si tenemos 2 contructores iguales habra error. Es un ejemplo de polimorfismo estatico


    7. ¿Cuales son las tres formas de trabajar con Excepciones en java?
        R:- try-catch: Captura el error en el momento y ejecuta una solucion o mensaje alternativo.
           - throw: Lanza una excepcion de forma intencional y manual (sirve para abortar un proceso si algo no cumple las reglas).
           - throws (con 's'): Se coloca en la firma de un metodo para indicar que propaga el error; es decir, "patea" el problema para que lo resuelva quien llamo al metodo.
    

    8. ¿Que diferencia hay entre Exception y RuntimeException?
        R:- Exception (Excepciones Comprobadas): El compilador de Java te OBLIGA a capturarlas con un try-catch o a propagarlas. No te deja compilar si las ignorás.
           - RuntimeException (Excepciones No Comprobadas): Ocurren en tiempo de ejecución. El compilador asume que tu lógica debería evitarlas, por lo que NO te obliga a usar try-catch.
        
        
    #Comentarios extra de personas que la rindieron:
    - "De esta unidad tan solo me hicieron una pregunta la 1 ya al final de responder todo sobre la otra unidad."
*/


public class unidad1 {
    //Esta es la clase principal del archivo(podemos verlo como punto de entrada de la aplicacion)... ¿por que? -> Simplemente porque tiene el mismo nombre.
    public static void main(String []args) {
        System.out.println("Hola, esto si anda");

        //instanciacion usando herencia -> Toma los comportamientos y caracteristicas de su superclase.
        Circulo circulo1 = new Circulo(15.7, "amarillo");
        System.out.print("Color del circulo:" + circulo1.getcolor()); 

        circulo1.calcularArea(); //Ejecuta el metodo reescritro
        System.out.println("Area del circulo: "+circulo1.getArea());

        //Uso de metodos estaticos sin instanciar objetos
        System.out.println("Area de un circulo(static): " + CalculoDeAreas.area(5.16));
        System.out.println("Area de un rectangulo(static): " + CalculoDeAreas.area(12.0, 7.5));
    }
    // //Aca voy a ejecutar todo (no voy a ejecutar nada)
}


// ===========================================================================================
// CLASES - MODIFICADORES - ABSTRACCION
// ===========================================================================================

class otraClase { } //clase con acceso 'default', visible solo en su mismo paquete.

final class otraOtraClase { } //'final': hace que esta clase NO sea heredable y si su constructor es private tampoco instanciable. Seria como un modulo lo podemos llenar de metodos estaticos como los metodos estaticos pertenecen a la clase y no al objeto, los podemos usar directamente

abstract class claseAbstracta { } //Esto no se puede instanciar 

abstract class Figura {
    //abstraccion: Representa un concepto general, si tiene un metodo abstracto obligatoriamente la clase es abstracta, pero no necesesariamente la clase abstracta tiene que tener metodos abstractos
    
    //encapsulamiento:
    private String color; //'private': el atributo es visible dentro de la misma clase y solo a traves de sus metodos, puede acceder a su valor.
    protected Double area; //'protected': el atributo es visible para esta clase y todas las subclases hijas.
    
    //constructor de clase abstracta(la clase en si no lo va usar pero sus subclases si)
    public Figura(String color){
        this.color = color;
    }

    public String getcolor() {
        return this.color;
    }

    //metodo abstacto: este metodo no tiene definicion,pero obliga a toda subclase dependientes a definir a su manera el como se calcula el area.
    public abstract void calcularArea();
    
    public Double getArea() {
        return this.area;
    }
} 


// ===========================================================================================
// JERARQUÍA - OCULTAMIENTO
// ===========================================================================================

class Circulo extends Figura {
    //'jerarquia': al usar el extends Figura, la clase Circulo se inserta en una jerarquia, donde toda clase hija adopta los comportamientos de la clase padre(superclase), permitiendo la reutilizacion de codigo y se establece una relacion de "es un/una"(Ej. el Circulo ES UNA Figura).
    
    //aca se usa el el concepto de 'encapsulamiento'
    private Double radio;
    public Circulo(Double radio, String color) {
        super(color);
        this.radio = radio;
    }

    //'ocultamiento': si se llama a calcularArea() desde otra clase, no le importa ni necesita saber la formula matematica interna, solo le interesa usar la interfaz publica y obtener el resultado final.
    @Override //el decorador indica que se esta reescribiendo la funcion abstracta heredada por la clase abstracta Figura.La reescritura es un tipo de polimorfismo en tiempo de ejecucion.
    public void calcularArea() {
        this.area = Math.PI * (this.radio * this.radio);
    }   
}


// ===========================================================================================
// MODULARIDAD - COHESION - ACOPLAMIENTO
// ===========================================================================================

/*
    Modularidad: Es la capacidad de dividir un sistema complejo en partes mas pequeñas (modulos) con responsabilidades claras e independientes. Se rige por dos metricas clave:
        1. Alta Cohesion: Un modulo debe estar enfocado en hacer UNA sola cosa, y hacerla bien.
        2. Bajo Acoplamiento: Los modulos deben ser lo mas independientes posibles entre si.

    Ejemplo Practico:
    Imaginemos el sistema de una app de delivery de comida por chatbot. Si tenemos un módulo (o paquete) encargado exclusivamente de leer los mensajes del bot, otro encargado de gestionar el carrito de compras, y otro encargado de procesar el pago:
        - Hay ALTA COHESION porque el modulo del carrito solo se ocupa del carrito, no de cobrar.
        - Hay BAJO ACOPLAMIENTO si, el dia de mañana, cambiamos la pasarela de pagos y el módulo del chatbot sigue funcionando perfectamente sin necesidad de modificar su codigo.
*/


// ===========================================================================================
// CLASE - OBJETO - ATRIBUTO - METODO
// ===========================================================================================

/*
    Aunque suelen darse por sentados, el paradigma se sostiene sobre estos 4 pilares:
    - Clase: Es la plantilla, el "molde" abstracto (ej. clase Circulo).
    - Objeto (Instancia): Es la materialización de esa clase en la memoria RAM,  con un estado unico (ej. un circulo especifico de radio 15.7 y color amarillo).
    - Atributo: Son las caracteristicas o el estado del objeto (ej. radio, color).
    - Metodo: Es el comportamiento, las acciones que el objeto puede realizar (ej. calcularArea).
*/


// ===========================================================================================
// SOBRECARGA - METODOS ESTATICOS
// ===========================================================================================

final class CalculoDeAreas {
    private CalculoDeAreas() {} //el constructor esta privado para que no lo puedan instanciar.

    //'sobrecarga': Mismo nombre de metodo(), pero con distintos parametros, que varia segun la cantidad y el tipo de parametros.
    static Double area(Double radio) {
        return Math.PI * (radio*radio);
    }

    //'static': significa que el metodo pertenece a la clase en si, no al objeto instanciado por lo que se pueden ejecutar sin existir un objeto de esa clase. Pasa lo mismo con los atributos, tienen un unico espacio de memoria.

    static Double area(Double base, Double altura) {// Esto es polimorfismo en tiempo de compilador.
        return base*altura;
    }
}


// ===========================================================================================
// POLIMORFISMO(COMPILACION Y EJECUCION)
// ===========================================================================================

class A {
    public A() {}//No es necesario hacer un constructor vacio porque como tal eso siempre existe hasta definirse uno con parametros, como ya se explico al inicio del archivo

    public void print(String x) {
        System.out.println("A: String");
    }
    public void print(Object x) {
        System.out.println("A: Object");
    }
}

class B extends A{
    public B() {}

    @Override
    public void print(String x) {
        System.out.println("B: String");
    }
    @Override
    public void print(Object x) {
        System.out.println("B: Object");
    }
}

final class Ejecutor {
    static void ejecutar() {
        Object x = "Hola en Object";
        String y = "hola en String";

        A idea = new B(); //Aca tenemos una referencia de tipo 'A', y una instancia de tipo B
        idea.print(x);
        idea.print(y);
    }

    /*
        Aclaracion: Aca se pueden ver muchisimas cosas interesantes, entre ellas la difrencia de dos tipos de polimorfismo.
            1. Sobrecarga = Tiempo de Compilación. El compilador decide que metodo usar  basado en el tipo de parametro (String vs Object).
            2. Reescritura (@Override) = Tiempo de Ejecucion. En este caso, aunque la referencia es de tipo 'A', Java sabe en tiempo de ejecucion que el objeto real en memoria es 'B'. Por lo tanto, SIEMPRE ejecutara las reescrituras de 'B'.

    */
}


// ===========================================================================================
// INTERFACES (EL "SIMULADO DE HERENCIA MULTIPLE DE JAVA")
// ===========================================================================================

//en java NO existe la herencia multiple(class hijo extends Padre,Madre {}), pero para resolver esto, java tiene las estructuras llamadas interfaces. Las interfaces definen "que" hacer, pero no "como".


interface NombreDeInterfaz {
    // Su fin ultimo es el de dar una linea editorial de que debe hacer todo objeto que la implemente sin definir el como.
    // Donde, a diferencia de las clases abstractas, no se puede definir ningun metodo. Solo se los declara de la forma:
    public abstract void metodoSinDefinir();
    public abstract void otroMasSinDefinir();
}

//Pensemos en aviones. Existen distintos tipos con distintos motores. En general, todos siguen regla fundamentalmente igual: queman combustible para mover un eje. Claro que entre una turbina de avion militar y un motor a combustion interna de una avioneta hay un mundo de diferencia entre ellos, pero comparten un comportamiento: los dos deben encenderse, entonces, podriamos definir una interfaz,en donde la defincion del metodo entre ellos puede varaiar escandalozamente pero en general ambos tienen "que" hacer lo mismo y ellos deciden el "como".

interface EncendidoDeAvion { //si se necesitara se le podria agregar mas interfaces a las clases, y una interface puede ser usado por distintas clases.
    Boolean startEngine(); //los metodos en las interfaces son implicitamente public y abstrac
}

class AvionAReaccion implements EncendidoDeAvion {
    @Override
    public Boolean startEngine() {
        //implementacion especifica de un motor a reaccion
        return true;
        //si hay un error: throw new RuntimeException("Algo salio mal")
    }
}

class AvionACombustionInterna implements EncendidoDeAvion {
    @Override
    public Boolean startEngine() {
        //implementacion especifica de un motor a helice
        return true;
        //si hay un error: throw new RuntimeException("Algo salio mal")
    }
}

// ===========================================================================================
// MANEJO DE EXCEPCIONES(Errores)
// ===========================================================================================


//  Existen 3 formas:
final class N {
    private N(Boolean condicion) { // No se puede usar.
        if (condicion) {
            //'forma 1': try-catch (manejo interno)
            try {
                // Secuencia que en caso de fallar, salta inmediatamente al bloque catch()
            } catch(Exception e) { // El bloque catch atrapa el error 'e'. 
                // Usamos 'throw' para lanzar un nuevo error mas descriptivo.
                throw new RuntimeException("Se rompio algo en: ", e);
            }   
        }
        else {
            //'forma 2': throw (Lanzamiento manual)
            // Esto simplemente crea y ejecuta una excepción intencional si no se cumple la condición.
            throw new RuntimeException("Algo salió mal, condición no cumplida");
        } 
    }

    //'forma 3': throws(propagacion del error)
    //cuando un metodo tiene 'throws' (con S al final), significa que no maneja el error internamente, si no que lo delega
    public void metodoX(String x) throws RuntimeException { //  en metodos es throws no thorw.
        System.out.println(x);
    }
    //quien llame a metodoX() tendra que hacerse cargo(del muerto), o usa un bloque try-catch, o también le pone throws a su propio método. 
}

//  Tambien se puede crear errores propios y personalizados:
class ExcepcionEpica extends Exception {
    // Entonces, en los bloques try-catch o usando 'throw', podés utilizar tu ExcepcionEpica.
    public ExcepcionEpica(String mensaje) {
        super(mensaje);
    }
}   
/*
    REGLA DE ORO DE LAS EXCEPCIONES:
    - RuntimeException (y sus hijas, como NullPointerException) NO necesitan 
      de bloques try-catch obligatorios para que el código compile.
    - Exception (y clases como ExcepcionEpica que hereden de ella) NECESARIAMENTE 
      deben estar dentro de un bloque try-catch o propagarse con 'throws', 
      sino Java te tirará error de compilación.

*/