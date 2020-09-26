import java.util.ArrayList;

public class ZooAmigo {
    int cedula;
    String nombre;
    String telefono;
    ArrayList<Animal> animales = new ArrayList<>();
    Zoologico zoologico;

    public ZooAmigo(int cedula, String nombre, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getAnimalID(){
        ArrayList<Integer> idAnimals = new ArrayList<>();
        for (Animal animal : animales) {
            idAnimals.add(animal.id);
        }
        return idAnimals.toString();
    }

    @Override
    public String toString() {
        return "ZooAmigo{" +
                "cedula=" + cedula +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", ID de Animales asociados= " + getAnimalID() +
                ", NIT del Zoologico asociado= " + ((zoologico == null) ? "N/A" : zoologico.nit) +
                '}';
    }
}
