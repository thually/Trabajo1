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
