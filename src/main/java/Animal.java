import org.json.simple.*;

public class Animal {
    int id;
    String especie; // Sea un Leon, foca, etc
    int nivelAgresividad; // Se mide del 0 al 5, siendo por ejemplo 0 un gorrion y 5 un leon
    String alimentacion; // Carnivoro, Hervivoro, Omnivoro.
    Habitat habitat;
    String idHab = "";
    ZooAmigo zooAmigo;
    String idZooA = "";

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

    public Animal(JSONObject toJavaObj){
        JSONObject animal = (JSONObject) toJavaObj.get("animal");
        Long preid = (Long) animal.get("id");
        this.id = preid.intValue();
        this.especie = (String) animal.get("especie");
        Long preNA = (Long) animal.get("nivelAgresividad");
        this.nivelAgresividad = preNA.intValue();
        this.alimentacion = (String) animal.get("alimentacion");
        this.idHab = (String) animal.get("ID Habitat");
        this.idZooA = (String) animal.get("ID zooAmi");
    }

    public JSONObject toJSONObj(){
        if (habitat == null) idHab = "NA";
        else idHab = Integer.toString(habitat.id);
        if (zooAmigo == null) idZooA = "NA";
        else idZooA = Integer.toString(zooAmigo.cedula);

        JSONObject aniDetails = new JSONObject();
        aniDetails.put("id", id);
        aniDetails.put("especie", especie);
        aniDetails.put("nivelAgresividad", nivelAgresividad);
        aniDetails.put("alimentacion", alimentacion);
        aniDetails.put("ID Habitat", idHab);
        aniDetails.put("ID zooAmi", idZooA);

        JSONObject animalObj = new JSONObject();
        animalObj.put("animal", aniDetails);

        return animalObj;
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
