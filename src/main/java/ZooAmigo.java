import java.util.ArrayList;
import java.util.Comparator;

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

    public class cedulaComparator implements Comparator<ZooAmigo> {
        public int compare(ZooAmigo cedula1, ZooAmigo cedula2){
            if (cedula1.cedula < cedula2.cedula){
                return -1;
            }else if(cedula1.cedula == cedula2.cedula){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class nombreComparator implements Comparator<ZooAmigo> {
        public int compare(ZooAmigo nombre1, ZooAmigo nombre2){

            if (nombre1.nombre.compareTo(nombre2.nombre) < 0){
                return -1;
            }else if(nombre1.nombre.equals(nombre2.nombre)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class telefonoComparator implements Comparator<ZooAmigo> {
        public int compare(ZooAmigo telefono1, ZooAmigo telefono2){

            if (telefono1.nombre.compareTo(telefono2.nombre) < 0){
                return -1;
            }else if(telefono1.nombre.equals(telefono2.nombre)){
                return 0;
            }else{
                return 1;
            }
        }
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

    public void setAnimal(Animal animalNuevo, ZooAmigo zooAmigo) {
        animales.add(animalNuevo);
        animalNuevo.zooAmigo=zooAmigo;
    }

    public void setZoologico(Zoologico zoologico, ZooAmigo zooAmigo) {
        zoologico.zooAmigos.add(zooAmigo);
        this.zoologico = zoologico;
    }
}
