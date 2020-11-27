package trabajo2;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeMap;

public class Animal {
    public static Hashtable<Integer, Animal> animalesPorID = new Hashtable<>();
    public static TreeMap<Integer, LinkedList<Animal>> animalesPorAgresividad = new TreeMap<>();
    public static TreeMap<String, LinkedList<Animal>> animalesPorEspecie = new TreeMap<>();

    int id;
    String especie; // Sea un Leon, foca, etc
    int nivelAgresividad; // Se mide del 0 al 5, siendo por ejemplo 0 un gorrion y 5 un leon
    String alimentacion; // Carnivoro, Hervivoro, Omnivoro.


    public Animal(int id, String especie, int nivelAgresividad, String alimentacion) {
        this.id = id;
        this.especie = especie;
        this.nivelAgresividad = nivelAgresividad;
        this.alimentacion = alimentacion;
    }


    @Override
    public String toString() {
        return "Animal:  " + "\n" +
                "ID: " + id +
                ", especie: '" + especie + '\'' +
                ", Nivel de Agresividad: " + nivelAgresividad +
                ", alimentación: '" + alimentacion + '\'' +
                " ";
    }

}

