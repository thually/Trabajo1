import org.json.simple.*;

import java.util.ArrayList;

public class Bioma {
    int id;
    double temperatura;
    String humedad; // Se utiliza una descripcion, seco, arido, etc
    String tipo;
    Zoologico zoologico;
    String IDZoo = "";
    ArrayList<Profesional> profesionales = new ArrayList<>();
    String IDPros = "";
    ArrayList<Habitat> habitats = new ArrayList<>();
    String IDHab = "";

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

    public Bioma(JSONObject toJavaObj){
        JSONObject bioma = (JSONObject) toJavaObj.get("bioma");
        Long preid = (Long) bioma.get("id");
        this.id = preid.intValue();
        this.temperatura = (double) bioma.get("temperatura");
        this.humedad = (String) bioma.get("humedad");
        this.tipo = (String) bioma.get("tipo");
        this.IDZoo = (String) bioma.get("ID Zoologico");
        this.IDPros = (String) bioma.get("ID Profesionales");
        this.IDHab = (String) bioma.get("ID Habitats");
    }

    public JSONObject toJSONObj(){
        IDPros = "";
        IDHab = "";
        if (zoologico == null) IDZoo = "NA";
        else IDZoo = zoologico.nit;
        if (profesionales.isEmpty()) IDPros = "NA";
        else for (Profesional pro : profesionales) { IDPros += pro.cedula + " "; }
        if (habitats.isEmpty()) IDHab = "NA";
        else for (Habitat hab : habitats) { IDHab += hab.id + " "; }

        JSONObject bioDetails = new JSONObject();
        bioDetails.put("id", id);
        bioDetails.put("temperatura", temperatura);
        bioDetails.put("humedad", humedad);
        bioDetails.put("tipo", tipo);
        bioDetails.put("ID Zoologico", IDZoo);
        bioDetails.put("ID Profesionales", IDPros);
        bioDetails.put("ID Habitats", IDHab);

        JSONObject biomaObj = new JSONObject();
        biomaObj.put("bioma", bioDetails);

        return biomaObj;
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
