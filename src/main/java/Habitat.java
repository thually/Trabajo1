import java.util.ArrayList;

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
