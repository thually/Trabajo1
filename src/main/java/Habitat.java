import org.json.simple.*;

import java.util.ArrayList;

public class Habitat {
    int id;
    String tipoSuelo; // Tierra, nevado, desertico, etc.
    String vegetacion; // Abundante, sin vegetacion, etc
    String tipoJaula; // Aviario, barrotes, malla, etc
    ArrayList<Animal> animales = new ArrayList<>();
    String IDAnis = "";
    Bioma bioma;
    String IDBio = "";
    ArrayList<Tecnico> tecnicos = new ArrayList<>();
    String IDTecs = "";

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

    public Habitat(JSONObject toJavaObj){
        JSONObject habitat = (JSONObject) toJavaObj.get("habitat");
        Long preid = (Long) habitat.get("id");
        this.id = preid.intValue();
        this.tipoSuelo = (String) habitat.get("tipoSuelo");
        this.vegetacion = (String) habitat.get("vegetacion");
        this.tipoJaula = (String) habitat.get("tipoJaula");
        this.IDBio = (String) habitat.get("ID Bioma");
        this.IDTecs = (String) habitat.get("ID Tecnicos");
        this.IDAnis = (String) habitat.get("ID Animales");
    }

    public JSONObject toJSONObj(){
        IDTecs = "";
        IDAnis = "";
        if (bioma == null) IDBio = "NA";
        else IDBio = Integer.toString(bioma.id);
        if (tecnicos.isEmpty()) IDTecs = "NA";
        else for (Tecnico tec : tecnicos) { IDTecs += tec.cedula + " "; }
        if (animales.isEmpty()) IDAnis = "NA";
        else for (Animal ani : animales) { IDAnis += ani.id + " "; }

        JSONObject habDetails = new JSONObject();
        habDetails.put("id", id);
        habDetails.put("tipoSuelo", tipoSuelo);
        habDetails.put("vegetacion", vegetacion);
        habDetails.put("tipoJaula", tipoJaula);
        habDetails.put("ID Bioma", IDBio);
        habDetails.put("ID Tecnicos", IDTecs);
        habDetails.put("ID Animales", IDAnis);

        JSONObject habObj = new JSONObject();
        habObj.put("habitat", habDetails);

        return habObj;
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
