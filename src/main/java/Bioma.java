import java.util.ArrayList;

public class Bioma {
    int id;
    Double temperatura;
    String humedad; // Se utiliza una descripcion, seco, arido, etc
    String tipo;
    Zoologico zoologico;
    ArrayList<Profesional> profesionales = new ArrayList<>();
    ArrayList<Habitat> habitats = new ArrayList<>();

    public Bioma(int id, Double temperatura, String humedad, String tipo) {
        this.id = id;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.tipo = tipo;
    }

    public String getProfesionalesCedula(){
        ArrayList<Integer> cedulaProfecional = new ArrayList<>();
        for (Profesional profesional : profesionales) {
            cedulaProfecional.add(profesional.cedula);
        }
        return cedulaProfecional.toString();
    }

    public String getHabitatId(){
        ArrayList<Integer> idHabitats = new ArrayList<>();
        for (Habitat habitat : habitats) {
            idHabitats.add(habitat.id);
        }
        return idHabitats.toString();
    }

    @Override
    public String toString() {
        return "Bioma{" +
                "id=" + id +
                ", temperatura=" + temperatura +
                ", humedad='" + humedad + '\'' +
                ", tipo='" + tipo + '\'' +
                ", NIT de zoologico asociados= " + zoologico.nit+
                ", id de habitats asociados= " + getHabitatId()+
                ", cedula de profesionales asociados= " + getProfesionalesCedula()+
                '}';
    }
}
