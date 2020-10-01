import java.util.Comparator;

public class Animal {
    int id;
    String especie; // Sea un Leon, foca, etc
    int nivelAgresividad; // Se mide del 0 al 5, siendo por ejemplo 0 un gorrion y 5 un leon
    String alimentacion; // Carnivoro, Hervivoro, Omnivoro.
    Habitat habitat;
    ZooAmigo zooAmigo;

    public Animal(int id, String especie, int nivelAgresividad, String alimentacion) {
        this.id = id;
        this.especie = especie;
        this.nivelAgresividad = nivelAgresividad;
        this.alimentacion = alimentacion;
    }
    public static void setHabitats(Habitat nuevoHabitat, Animal animal){
        nuevoHabitat.animales.add(animal);
        animal.habitat = nuevoHabitat;
    }

    public static void setZooAmigo(ZooAmigo nuevoZooAmigo, Animal animal){
       nuevoZooAmigo.animales.add(animal);
        animal.zooAmigo = nuevoZooAmigo;
    }

    public class IDComparator implements Comparator<Animal> {
        public int compare(Animal id1, Animal id2){
            if (id1.id < id2.id){
                return -1;
            }else if(id1.id == id2.id){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class especieComparator implements Comparator<Animal> {
        public int compare(Animal especie1, Animal especie2){

            if (especie1.especie.compareTo(especie2.especie) < 0){
                return -1;
            }else if(especie1.especie.equals(especie2.especie)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class nivelAgresividadComparator implements Comparator<Animal> {
        public int compare(Animal nivelAgresividad1, Animal nivelAgresividad2){
            if (nivelAgresividad1.nivelAgresividad < nivelAgresividad2.nivelAgresividad){
                return -1;
            }else if(nivelAgresividad1.nivelAgresividad == nivelAgresividad2.nivelAgresividad){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class alimentacionComparator implements Comparator<Animal> {
        public int compare(Animal alimentacion1, Animal alimentacion2){

            if (alimentacion1.alimentacion.compareTo(alimentacion2.alimentacion) < 0){
                return -1;
            }else if(alimentacion1.alimentacion.equals(alimentacion2.alimentacion)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", especie='" + especie + '\'' +
                ", nivelAgresividad=" + nivelAgresividad +
                ", alimentacion='" + alimentacion + '\'' +
                ", ID del habitat= " + ((habitat == null) ? "N/A" : habitat.id) +
                ", Cedula del ZooAmigo= " + ((zooAmigo == null) ? "N/A" : zooAmigo.cedula) +
                '}';
    }

}
