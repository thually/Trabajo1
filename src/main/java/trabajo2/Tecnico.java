package trabajo2;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeMap;

public class Tecnico {
    public static Hashtable<Integer, Tecnico> tecnicosPorID = new Hashtable<>();
    public static TreeMap<Double, LinkedList<Tecnico>> tecnicosPorSueldo = new TreeMap<>();
    public static TreeMap<String, LinkedList<Tecnico>> tecnicosPorArea = new TreeMap<>();

    int cedula;
    double sueldo;
    String area; //Tipo de trabajo, Guarda, Aseo, etc
    String horaInicio;
    String horaSalida;

    public Tecnico(int cedula, double sueldo, String area, String horaInicio, String horaSalida) {
        this.cedula = cedula;
        this.area = area;
        this.horaInicio = horaInicio;
        this.horaSalida = horaSalida;
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Tecnico { " +
                "cedula: " + cedula +
                ", área: '" + area + '\'' +
                ", Sueldo: " + sueldo + '\'' +
                ", Hora de inicio: '" + horaInicio + '\'' +
                ", Hora de Salida: '" + horaSalida + '\'' +
                '}';
    }
}
