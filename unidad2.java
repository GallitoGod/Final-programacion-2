// LEAN EL README NO SEAN GILES (CONSEJO: USEN ALT+Z EN EL EDITOR)

// ===========================================================================================
// PROGRAMACION 2 - UNIDAD 2 GUIA DEFINITIVA SUPREMA 4K 360Hz (COLECCIONES Y JCF)
// ===========================================================================================

/*
    PREGUNTAS REALES DE EXAMEN FINAL (UNIDAD 2 - COLECCIONES Y REPASO):
    
    ACLARACION: Los PDFs de la catedra le dan extrema importancia al JCF (Java Collections Framework), pero en los finales a veces te evaluan fuerte los conceptos teoricos practicos de la Unidad 1. ¡Estudien ambas!

    1. ¿Cuando se utilizan interfaces en vez de clases abstractas?
       R: Cuando necesitamos dar un comportamiento a priori identico a clases que no tienen relacion directa entre si. Mismos metodos (el "que"), distintas implementaciones (el "como"). Ademas, Java NO permite la herencia multiple de clases, pero SI permite implementar multiples interfaces.

    2. ¿Cuantas clases (abstractas o normales) se pueden heredar?
       R: SOLO UNA (solo se puede heredar una superclase por subclase). En Java, la herencia de clases es simple. Una subclase solo puede tener una superclase directa.

    3. Escriba en el pizarron/papel la sintaxis de una clase que hereda de otra e implementa varias interfaces.
       R: public class MiClase extends SuperClase implements Interfaz1, Interfaz2, Interfaz3 { ... }

    #Comentarios extra de personas que la rindieron:
    -"Segun mi experiencia en los finales de programacion las respuestas que buscan los profes es que sean concretas a las preguntas que realizaron, no irse mas de lo que te piden.
    Mis respuestas fueron mas bien explicadas a mis palabras a que una definicion tal como lo da la catedra en los pdfs."
*/

import java.util.*;

public class unidad2 {
    //punto de entrada para la coleccion
    public static void main(String [] args) {
        System.out.println("--- Iniciando pruebas de Colecciones (JCF) ---");
        
        Colecciones tiposDeColecciones = new Colecciones();
        
        System.out.println("\n--- 1. Probando ArrayList ---");
        tiposDeColecciones.usosArrayList("Mate");
        
        System.out.println("\n--- 2. Probando TreeSet (Orden natural) ---");
        tiposDeColecciones.usosTreeSet();
        
        System.out.println("\n--- 3. Probando HashMap (Diccionarios) ---");
        tiposDeColecciones.usosHashMap("Gallo");

        System.out.println("\n--- 4. Probando Iterator y Comparable con Objetos ---");
        MovimientosDePersona.usoDeIterator(new Persona("Jorge"));
        MovimientosDePersona.ordenNatural();
        MovimientosDePersona.mapper();
    }
}

// ===========================================================================================
// ESTRUCTURAS DE DATOS: LIST - SET - MAP
// ===========================================================================================

class Colecciones {
    //al declarar la variable con la INTERFAZ (List, Set, Map) y crear la instancia con la CLASE CONCRETA (ArrayList, TreeSet, HashMap).Esto me asegura un bajo acoplamiento.

    private List<String> lista;
    private Set<Integer> setOrdenado, setRapido;
    private Map<String, Integer> diccionario;

    public Colecciones() {
        this.lista = new ArrayList<>();

        //'TreeSet': ordena los elementos automaticamente(mas lento, pero siempre ordenado).
        this.setOrdenado = new TreeSet<>();
        
        //'HashSet': no garantiza ningun orden, pero es ultrarapida para buscar y agregar.
        this.setRapido = new HashSet<>();

        //'HashMap': estructura de clave-valor(key-value), como un diccionario
        this.diccionario = new HashMap<>();
    }

    public void usosArrayList(String a) {
        // 1. INSERCIÓN: 'List' permite duplicados y mantiene el orden segun la insercion
        this.lista.add(a); //agrega al final
        this.lista.add(0, "bombilla"); //agrega en la posicion
        
        // 2. CONSULTAS
        System.out.println("Tamaño de la lista: " + this.lista.size()); 
        System.out.println("¿Esta vacia?: " + this.lista.isEmpty()); 
        System.out.println("¿Contiene '" + a + "'?: " + this.lista.contains(a)); 

        // 3. ITERACIONES
        System.out.println("\nIterando la lista con For-Each:");
        for(String elemento : this.lista) {
            System.out.println(elemento);
        }

        System.out.println("\nIterando la lista con For tradicional:");
        for(int i=0; i < this.lista.size(); i++) {
            System.out.println(this.lista.get(i));
        }

        System.out.println("\nIterando la lista con Iterator:");
        Iterator<String> it = this.lista.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // 4. ORDENAMIENTO
        Collections.sort(this.lista); //de forma natural
        // Collections.sort(this.lista, Comparator.reverseOrder()); //o de otras formas
        // this.lista.sort(Comparator.naturalOrder());
        // this.lista.sort(Comparator.reverseOrder());

        // 5. MODIFICACIÓN Y ELIMINACIÓN
        this.lista.set(0, "termo"); //reemplaza en la posicion
        this.lista.remove(0); //elimina en la posicion
        this.lista.remove(a); //elimina la primera coincidencia
        
        // 6. LIMPIEZA TOTAL (Siempre al final para no romper las iteraciones de arriba)
        this.lista.clear();  //limpia toda la lista
    }

    public void usosTreeSet() {
        //'set':LA REGLA PRINCIPAL ES QUE NO PERMITE DUPLICADOS.        
        this.setOrdenado.add(10);
        this.setOrdenado.add(3);
        this.setOrdenado.add(2);
        this.setOrdenado.add(3); //esto NO hace nada ni tira error, simplemente lo ignora.  

        //al imprimir, va a salir "2, 3, 10" porque es un TreeSet y se ordena solo. Se iteran igual que una lista
        System.out.println("Contenido del TreeSet: " + this.setOrdenado);
    }

    public void usosHashSet() {
        this.setRapido.add(1);
        this.setRapido.add(1); //lo ignora(no permite duplicados)
        this.setRapido.contains(1); //existe pero solo una vez
        this.setRapido.remove(1);
        System.out.println("Tamaño del HashSet: "+this.setRapido.size()); //daria 0
        //se iteran igual que una lista
    }

    public void usosHashMap(String a) {
        //'map':Se guarda en parte de (clave,valor),las claves no se pueden repetir
        this.diccionario.put(a, 1);
        this.diccionario.put(a, 2); //al repetir la clave 'a', PISA el valor anterior (1 pasa a ser 2).
        System.out.println("Valor asociado a la clave: "+this.diccionario.get(a)); 
        System.out.println("¿Existe la clave?: "+this.diccionario.containsKey(a));
        
        //se puede iterar de la forma map:
        System.out.println("Iterando solo las claves (KeySet):");
        for (String key : this.diccionario.keySet()) {
            System.out.println(key);
        }
        // O sino:
        System.out.println("Iterando solo los valores:");
        for (Integer value : this.diccionario.values()) {
            System.out.println(value);
        }
    }
}

// ===========================================================================================
// INTERFACES: COMPARABLE - ITERATOR
// ===========================================================================================

//implementar Comparable obliga a la clase a definir cómo se ordenan sus objetos por defecto.
class Persona implements Comparable<Persona> {
    private String nombre;

    public Persona (String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int compareTo(Persona otraPersona) {//aca delegamos el ordenamiento al metodo compareTo de la clase String
        return this.nombre.compareTo(otraPersona.nombre);
    }

    @Override
    public String toString() {//para que al hacer System.out.println(persona) salga el nombre y no la memoria.
        return this.nombre;
    }

    //es buena práctica reescribir equals cuando trabajamos con colecciones
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Persona persona = (Persona) obj;
        return Objects.equals(nombre, persona.nombre);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}

final class MovimientosDePersona {
    private MovimientosDePersona() {};

    //orden natural - usando TreeSet con objetos propios
    public static void ordenNatural() {
        //al usar un TreeSet con nuestra clase Persona, Java llama automáticamente al método compareTo() que sobreescribimos arriba para saber cómo ordenarlos.
        SortedSet<Persona> p = new TreeSet<>();
        p.add(new Persona("Rodrigo"));
        p.add(new Persona("Ana"));
        
        System.out.println("\n--- Orden natural del TreeSet de Personas ---");
        //va a imprimir "Ana" y después "Rodrigo", ordenados alfabéticamente.
        for (Persona persona : p) {
            System.out.println(persona);
        }
    }

    //iterator - recorriendo y eliminando de forma segura)
    public static void usoDeIterator(Persona nadie) {
        List<Persona> p = new ArrayList<>();
        p.add(new Persona("Rodrigo"));
        p.add(new Persona("Ana"));
        p.add(new Persona("Jorge"));
        p.add(new Persona("Lucia"));

        Iterator<Persona> it = p.iterator();

        while(it.hasNext()) {
            //correccion critica: se debe llamar a it.next() UNA SOLA VEZ por ciclo
            Persona actual = it.next(); 
            System.out.println(actual);
            
            if (actual.equals(nadie)) {
                it.remove(); //borra el elemento actual de forma segura sin romper el ciclo
            }
        }
        //existe ListIterator que es muy similar, tambien implementa hasPrevious() y previous() para poder ir de atras para delante.
    }

    //'mapper':Uso puro de un HashMap
    public static void mapper() {
        Map<String, Integer> map = new HashMap<>();
        map.put("valor1", 1);
        map.put("valor2", 2);
        map.put("valor1", 1); //sobrescribe la clave "valor1" existente
        map.put("valor3", 3);
        
        System.out.println("\n--- Pruebas de HashMap (Mapper) ---");
        System.out.println("Mapa completo: " + map);
        System.out.println("Obtener clave 'valor1': " + map.get("valor1"));
        System.out.println("¿Contiene clave 'valor2'?: " + map.containsKey("valor2"));
        
        System.out.println("Iterando solo los valores (por clave):");
        for (String key : map.keySet()) {
            System.out.println(map.get(key));
        }
        
        map.remove("valor3"); // Elimina la clave y su valor asociado

        System.out.println("Iterando solo los valores sueltos:");
        for (Integer value : map.values()) {
            System.out.println(value);
        }
    }
}