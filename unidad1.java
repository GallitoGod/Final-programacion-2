//Estos documentos no siguen el orden de explicacion de los documentos teoricos.


public class unidad1 {
    // Esta es la clase principal del archivo. Simplemente porque tiene el mismo nombre.
    // Etntonces puedo hacer esto --->
    public static void main(String []args) {
        System.out.println("Hola, esto si anda");

        Circulo circulo1 = new Circulo(15.7, "amarillo");
        System.out.print(circulo1.getcolor()); // Toma los comportamientos y caracteristicas de su superclase.

        System.out.println(CalculoDeAreas.area(5.16));
        System.out.println(CalculoDeAreas.area(12.0, 7.5));
    }
    // Aca voy a ejecutar todo (no voy a ejecutar nada)
}


class otraClase { } // Solo en esta linea pasa algo interesante, esta clase es 'default', basicamente publica
// Tambien puedo crear clases abstractas y finales
final class otraOtraClase { } // Esta clase no es heredable y si su constructor es private tampoco instanciable. Seria como un modulo
//de metodos.
abstract class claseAbstracta { } // Esta no se puede instanciar no se puede instanciar

abstract class Figura {
    //      Voy a explicar el concepto de abstraccion con esta clase, ojo que no necesariamente abstraccion en OO significa clases abstractas.
    //      Mas bien hace referencia a la capacidad de crear una clase que pueda representar un concepto general para poder intanciar objectos 
    //variados pero que comportan cualidades y comportamientos similares. Podria haber hehco la clase figura normal, pero de paso explico 
    //clases abstractas:
    private String color; // que sea private significa que solo el, a traves de sus metodos, puede acceder a su valor.
    protected Double area; // En protected, todas las subclases ven el atributo.
    public Figura(String color){
        // Puedo crear su constructor, el no lo va a usar pero sus subclases si
        this.color = color;
    }
    public String getcolor() { // Como tal puede tener metodos normales, pero es necesario que tenga como minimo un metodo abstracto
        return this.color;
    }
    public abstract void calcularArea(); // Este metodo no tiene definicion, las subclases dependientes de su manera de calcular el area deben definirlo.
    public Double getArea() {
        return this.area;
    }
} //    Esto simplifica la idea de jerarquia en los programas de OO, donde podemos ver que toda clase que herede de esta, podra comportarse como ella y
  //tendra caracteristicas similares 

class Circulo extends Figura {
    private Double radio;
    public Circulo(Double radio, String color) {
        super(color);
        this.radio = radio;
    }
    @Override // <--- Aca se esta reescribiendo la funcion abstracta heredada por la clase abstracta Figura.
    public void calcularArea() {//      La reescritura es un tipo de polimorfismo en tiempo de ejecucion.
        this.area = Math.PI * (this.radio * this.radio);
    }   
} //    En esto tambien se ve la encapsulacion y ocultamiento, principalmente a traves de los modificadores de acceso de clases, metodos y atributos.

//      El concepto de modularidad hace foco en al capacidad de modularizar las partes necesarias de un sistema para llevar a cabo la resolucion de un problema
//no es tan visible en programas como estos, donde su fin es practicar conceptos y marcar de donde vienen de la teoria, pero:
//      Hagamos de cuenta que se tiene un sistema de cajero de comida rapida, la capacidad de modularidad viene dada de el hecho de poder separar dependencias claves.
//      Si tenemos (idealmente claro) un paquete que adminstra el pago, otro que manda el pedido a cocina y otro que ordena las salidas de los pedidos, podriamos decir
//que, a priori que el sistema esta bien modularizado. Tambien se puede ver el nivel de acoplamiento entre ellos que se explica como la dependencia entre uno y otro para
//completar su fuoncion.


// -----------------------------------------------------------------------------------

//      Creo que en el simple hecho de mostrar los elementos que componen el paradigma OO queda implicitamente explicado lo que significa
//un metodo y un atributo, que es una clase y porque su instancia es un objeto unico y como estos documentos son mas practicos que teoricos
//voy a saltar conceptos teoricos que no tiene sentido explicar nuevamente en la practica.

//      La sobrecarga si es un concepto que me interesa practicar fuertemente. Hagamos de cuenta que en algun sistema se necesita caluclar
//areas de distintoas figuras geometricas utilizando una clase de la forma:

final class CalculoDeAreas {
    private CalculoDeAreas() {}

    static Double area(Double radio) {//    Donde la sobrecarga de metodos se da a traves de la cantidad y tipo de parametros que toma el metodo.
        return Math.PI * (radio*radio);
    }

    static Double area(Double base, Double altura) {//      Esto es polimorfismo en tiempo de compilador.
        return base*altura;
    }
}//     Aparte, en esta clase se puede ver metodos "static", los cuales pertenecen a la clase y no a un objeto,
 //por lo que se pueden ejecutar sin existir un objeto de esa clase. Pasa lo mismo con los atributos, tienen un unico espacio de memoria.





//      Ya hice referencia al polimorfismo en tiempo de compilacion y ejecucion, asi que voy a dar un ejemplo util para el final de estos dos:

class A {
    public A() {}//     No es necesario hacer un constructor vacio porque como tal eso siempre existe hasta definirse uno con parametros.

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
    static Object x = "Hola en Object";
    static String y = "hola en String";
    private Ejecutor() {}

    static void ejecutar() {
        A idea = new B();
        idea.print(x);
        idea.print(y);
    }
}
//      Aca se pueden ver muchisimas cosas interesantes, entre ellas la difrencia de dos tipos de polimrofismo.
//      Que se ejecutaria de aca? los metodos de A, siendo la referencia, o los metodos reescritos de B siendo el espacio de memoria almacenado ?
//      La respuesta es simple: se ejecutarian las reescrituras de B y sus sobrecargas, pero, tambien se ve que la referencia de un objeto y
//las sobrecargas son polimorfismo de compilacion, mientras que las reescrituras polimorfismo de compilacion, porque en compilacion 
//se utilizarian los metodos sobrecargados de la clase A, mientras que en ejecucion las reescrituras de la clase B.


//      Una vez intente esto:
//      class N extends X, Y {}
//Y FUNCIONO, estaba en C++. En Java no se puede extender de mas de una clase. Para corregir ello, existe una estructura llamada Interfaz:

interface NombreDeInterfaz {
    // Su fin ultimo es el de dar una linea editorial de que debe hacer todo objeto que la implemente sin definir el como.
    // Donde, a diferencia de las clases abstractas, no se puede definir ningun metodo. Solo se los declara de la forma:
    public abstract void metodoSinDefinir();
    public abstract void otroMasSinDefinir();
}

//      Pensemos en aviones. Existen distintos tipos con distintos motores. En general, todos siguen regla fundamentalmente igual:
//queman combustible para mover un eje. Claro que entre una turbina de avion militar y un motor a combustion interna de una avioneta
//hay un mundo de diferencia entre ellos, pero comparten un comportamiento: los dos deben encenderse, entonces, podriamos definir una interfaz:

interface encendidoDeAvion {//  Si ncesitara le podria agregar mas interfaces.
    public abstract Boolean startEngine();
}//     El cual puede ser usado por distintas clases de la forma:

class AvionAReaccion implements encendidoDeAvion {
    @Override
    public Boolean startEngine() {
        //Definicion del metodo que termina en:
        return true;
        //O flase de haber algun problema: trow new RuntimeException("Algo salio mal")
    }
}
// Y tambien:
class AvionACombustionInterna implements encendidoDeAvion {
    @Override
    public Boolean startEngine() {
        //Definicion del metodo que termina en:
        return true;
        //O flase de haber algun problema: trow new RuntimeException("Algo salio mal")
    }
}
//      En donde la defincion del metodo entre ellos puede varaiar escandalozamente pero en general ambos tienen "que" 
//hacer lo mismo y ellos deciden el "como".

