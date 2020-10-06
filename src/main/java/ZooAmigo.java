import java.util.ArrayList;
import org.json.simple.*;
import sun.security.krb5.KerberosSecrets;

public class ZooAmigo {
    int cedula;
    String nombre;
    String telefono;
    ArrayList<Animal> animales = new ArrayList<>();
    String IDAnis = "";
    Zoologico zoologico;
    String IDZoo = "";

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

    public ZooAmigo(JSONObject toJavaObj){
        JSONObject zooAmigo = (JSONObject) toJavaObj.get("zooAmigo");
        this.cedula = (int) zooAmigo.get("cedula");
        this.nombre = (String) zooAmigo.get("nombre");
        this.telefono = (String) zooAmigo.get("telefono");
        this.IDZoo = (String) zooAmigo.get("ID Zoologico");
        this.IDAnis = (String) zooAmigo.get("ID Animales");
    }

    public JSONObject toJSONObj(){
        if (zoologico == null) IDZoo = "N/A";
        else IDZoo = zoologico.nit;
        if (animales.isEmpty()) IDAnis = "N/A";
        else for (Animal ani : animales) { IDAnis += ani.id + " "; }

        JSONObject zooAmiDetails = new JSONObject();
        zooAmiDetails.put("cedula", cedula);
        zooAmiDetails.put("nombre", nombre);
        zooAmiDetails.put("telefono", telefono);
        zooAmiDetails.put("ID Animales", IDAnis);
        zooAmiDetails.put("ID Zoologico", IDZoo);

        JSONObject zooAmiOBj = new JSONObject();
        zooAmiOBj.put("zooAmigo", zooAmiDetails);

        return zooAmiOBj;
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
