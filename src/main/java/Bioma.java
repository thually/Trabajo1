import java.util.ArrayList;
import java.util.Comparator;
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

    public void setZoo(Zoologico nuevoZoo, Bioma bioma){
        this.zoologico = nuevoZoo;
        nuevoZoo.biomas.add(bioma);
    }

    public void setProfesional(Profesional nuevoPro, Bioma bioma){
        profesionales.add(nuevoPro);
        nuevoPro.biomas.add(bioma);
    }

    public void setHabitat(Habitat habitat, Bioma bioma){
        bioma.habitats.add(habitat);
        habitat.bioma = bioma;
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

    public class temperaturaComparator implements Comparator<Bioma> {
        public int compare(Bioma temperatura1, Bioma temperatura2){
            if (temperatura1.temperatura < temperatura2.temperatura){
                return -1;
            }else if(temperatura1.temperatura == temperatura2.temperatura){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class IDComparator implements Comparator<Bioma> {
        public int compare(Bioma id1, Bioma id2){
            if (id1.id < id2.id){
                return -1;
            }else if(id1.id == id2.id){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class humedadComparator implements Comparator<Bioma> {
        public int compare(Bioma humedad1, Bioma humedad2){

            if (humedad1.humedad.compareTo(humedad2.humedad) < 0){
                return -1;
            }else if(humedad1.humedad.equals(humedad2.humedad)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    public class tipoComparator implements Comparator<Bioma> {
        public int compare(Bioma tipo1, Bioma tipo2){

            if (tipo1.tipo.compareTo(tipo2.tipo) < 0){
                return -1;
            }else if(tipo1.tipo.equals(tipo2.tipo)){
                return 0;
            }else{
                return 1;
            }
        }
    }



    @Override
    public String toString() {
        return "Bioma{ " +
                "id=" + id +
                ", temperatura=" + temperatura +
                ", humedad='" + humedad + '\'' +
                ", tipo='" + tipo + '\'' +
                ", NIT de zoologico asociados= " + ((zoologico == null) ? "N/A" : zoologico.nit) +
                ", id de habitats asociados= " + getHabitatId()+
                ", cedula de profesionales asociados= " + getProfesionalesCedula()+
                " }";
    }
}
