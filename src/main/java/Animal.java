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

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", especie='" + especie + '\'' +
                ", nivelAgresividad=" + nivelAgresividad +
                ", alimentacion='" + alimentacion + '\'' +
                '}';
    }
}
