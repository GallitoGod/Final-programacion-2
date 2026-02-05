import java.util.*;



public class unidad2 {
    public static void main(String [] args) {
        //      Cabe aclarar que de haber queriado usar las colecciones, tendria que haberlas creado aca,
        //no en un constructor al cual simplemente no se puede acceder.
    }
}

final class tipos {
    
    private List<String> lista;
    private Set<Integer> set, set1;
    private Map<String, Integer> map;

    private tipos() {
        //  No se referenian tipos de colecciones desde objetos como ArrayList, ect.
        List<String> lista = new ArrayList<>();
        Set<Integer> tSet = new TreeSet<>();
        Set<Integer> hSet = new HashSet<>();
        Map<String, Integer> diccionario = new HashMap<>();
        // Siempre usar las interfaces para referenicar
        // Y para tomar espacio de memoria si se usa la clase.

        //      Deque<Boolean> cola = new ArrayDeque<>();

        this.lista = lista;
        this.set = tSet;
        this.set1 = hSet;
        this.map = diccionario;
    }

    public void usosArrayList(String a) {
        //  Se usa asi:
        this.lista.add(a); //agrega al final
        this.lista.add(3, a); //agrega en la posicion
        this.lista.remove(0); // elimina en la posicion
        this.lista.remove(a);       // elimina la primera coincidencia
        this.lista.set(0, a);// reemplaza en la posicion
        this.lista.size();  // Da el tamaño
        this.lista.isEmpty(); // dice si esta vacia o no
        this.lista.contains(a); // busca el objeto
        this.lista.clear();  // limpia toda la lista

        //  Se iteran como siempre:
        for(String elemento : this.lista) {
            System.out.println(elemento);
        } 
        for(int i=0; i < this.lista.size(); i++) {
            System.out.println(this.lista.get(i));
        }
        // O sino con la interfaz Iterator
        Iterator<String> it = this.lista.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        //Y se pueden ordenar:
        Collections.sort(this.lista); //    De forma natural
        Collections.sort(this.lista, Comparator.reverseOrder()); // O de otras formas
        // O sino:
        this.lista.sort(Comparator.naturalOrder());
        this.lista.sort(Comparator.reverseOrder());
    }

    public void usosTreeSet() {
        // Donde:
        this.set.add(1);
        this.set.add(3);
        this.set.add(2);
        // Los pone en orden 1,2,3 solo

        // Se iteran igual que una lista
    }

    public void usosHashSet() {
        this.set1.add(1);
        this.set1.add(1); // No lo va a permitir
        this.set1.contains(1); // existe pero solo una vez
        this.set1.remove(1);
        this.set1.size(); // Daria 0

        // Se iteran igual que una lista
    }

    public void usosHashMap(String a) {
        this.map.put(a, 1);
        this.map.put(a, 2); // pisa el dato
        this.map.get(a); 
        this.map.containsKey(a);
        this.map.containsValue(1);
        this.map.size();

        // Se puede iterar de la forma:
        for (String key : this.map.keySet()) {
            System.out.println(key);
        }
        // O sino:
        for (Integer value : this.map.values()) {
            System.out.println(value);
        }
    }
}