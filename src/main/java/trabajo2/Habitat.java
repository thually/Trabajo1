package trabajo2;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeMap;

public class Habitat {
    public static Hashtable<Integer, Habitat> habitatsPorID = new Hashtable<>();
    public static TreeMap<Double, LinkedList<Habitat>> habitatsPorTemperatura = new TreeMap<>();
    public static TreeMap<String, LinkedList<Habitat>> habitatsPorSuelo = new TreeMap<>();

    int id;
    double temperatura;
    String tipoSuelo; // Tierra, nevado, desertico, etc.
    String vegetacion; // Abundante, sin vegetacion, etc
    String tipoJaula; // Aviario, barrotes, malla, etc

    public Habitat(int id, double temperatura, String tipoSuelo, String vegetacion, String tipoJaula) {
        this.id = id;
        this.tipoSuelo = tipoSuelo;
        this.vegetacion = vegetacion;
        this.tipoJaula = tipoJaula;
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "Habitat:" + "\n" +
                "ID: " + id +
                ", Temperatura: '" + temperatura + '\'' +
                ", Tipo de suelo: '" + tipoSuelo + '\'' +
                ", vegetación: '" + vegetacion + '\'' +
                ", Tipo de jaula: '" + tipoJaula + '\'' +
                "";
    }
}
