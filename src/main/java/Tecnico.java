import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Tecnico {
    int cedula;
    String area; //Tipo de trabajo, Guarda, Aseo, etc
    String horaInicio;
    String horaSalida;
    ArrayList<Habitat> habitats = new ArrayList<>();
    String IDHab = "";

    public Tecnico(int cedula, String area, String horaInicio, String horaSalida) {
        this.cedula = cedula;
        this.area = area;
        this.horaInicio = horaInicio;
        this.horaSalida = horaSalida;
    }

    public String getHabitatsId(){
        ArrayList<Integer> idHabitats = new ArrayList<>();
        for (Habitat habitat : habitats) {
            idHabitats.add(habitat.id);
        }
        return idHabitats.toString();
    }

    public static void setHabitats(Habitat nuevoHabitat, Tecnico tecnico){
        nuevoHabitat.tecnicos.add(tecnico);
        tecnico.habitats.add(nuevoHabitat);
    }

    public Tecnico(JSONObject toJavaObj){
        JSONObject tecnico = (JSONObject) toJavaObj.get("tecnico");
        Long preid = (Long) tecnico.get("cedula");
        this.cedula = preid.intValue();
        this.area = (String) tecnico.get("area");
        this.horaInicio = (String) tecnico.get("horaInicio");
        this.horaSalida = (String) tecnico.get("horaSalida");
        this.IDHab = (String) tecnico.get("ID Habitats");
    }

    public JSONObject toJSONObj(){
        IDHab = "";
        if (habitats.isEmpty()) IDHab = "NA";
        else for (Habitat hab : habitats) { IDHab += hab.id + " "; }

        JSONObject tecDetails = new JSONObject();
        tecDetails.put("cedula", cedula);
        tecDetails.put("area", area);
        tecDetails.put("horaInicio", horaInicio);
        tecDetails.put("horaSalida", horaSalida);
        tecDetails.put("ID Habitats", IDHab);

        JSONObject tecnicoObj = new JSONObject();
        tecnicoObj.put("tecnico", tecDetails);

        return tecnicoObj;
    }

    @Override
    public String toString() {
        return "Tecnico{" +
                "cedula=" + cedula +
                ", area='" + area + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", ID Habitats asociados= " + getHabitatsId() +
                '}';
    }
}
