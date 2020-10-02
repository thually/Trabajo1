import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.Comparator;

public class Habitat {
    int id;
    String tipoSuelo; // Tierra, nevado, desertico, etc.
    String vegetacion; // Abundante, sin vegetacion, etc
    String tipoJaula; // Aviario, barrotes, malla, etc
    ArrayList<Animal> animales = new ArrayList<>();
    Bioma bioma;
    ArrayList<Tecnico> tecnicos = new ArrayList<>();

    public Habitat(int id, String tipoSuelo, String vegetacion, String tipoJaula) {
        this.id = id;
        this.tipoSuelo = tipoSuelo;
        this.vegetacion = vegetacion;
        this.tipoJaula = tipoJaula;
    }

    public void setBioma(Bioma nuevoBio, Habitat habitat){
        this.bioma = nuevoBio;
        nuevoBio.habitats.add(habitat);
    }

    public void setTecnico(Tecnico nuevoTec, Habitat habitat){
        tecnicos.add(nuevoTec);
        nuevoTec.habitats.add(habitat);
    }

    public void setAni(Animal ani, Habitat habitat){
        habitat.animales.add(ani);
        ani.habitat = habitat;
    }

    public String getTecnicosCedula(){
        ArrayList<Integer> cedulaTecnicos = new ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            cedulaTecnicos.add(tecnico.cedula);
        }
        return cedulaTecnicos.toString();
    }

    public String getAnimalId(){
        ArrayList<Integer> idAnimal = new ArrayList<>();
        for (Animal animal : animales) {
            idAnimal.add(animal.id);
        }
        return idAnimal.toString();
    }







    public class vegetacionComparator implements Comparator<Habitat> {
        public int compare(Habitat vegetacion1, Habitat vegetacion2){

            if (vegetacion1.vegetacion.compareTo(vegetacion2.vegetacion) < 0){
                return -1;
            }else if(vegetacion1.vegetacion.equals(vegetacion2.vegetacion)){
                return 0;
            }else{
                return 1;
            }
        }
    }

    @Override
    public String toString() {
        return "Habitat{ " +
                "id=" + id +
                ", Tipo de suelo ='" + tipoSuelo + '\'' +
                ", vegetacion='" + vegetacion + '\'' +
                ", Tipo de jaula ='" + tipoJaula + '\'' +
                ", Id de bioma asociados= " + ((bioma == null) ? "N/A" : bioma.id) +
                ", id de animales asociados= " + getAnimalId()+
                ", cedula de tecnicos asociados= " + getTecnicosCedula()+
                " }";
    }
}
