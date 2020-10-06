import java.util.ArrayList;
import org.json.simple.*;

public class Profesional {
    int cedula;
    String area; //Tipo de profesional, ej: Veterinario, Administración
    String horaInicio;
    String horaSalida;
    ArrayList<Bioma> biomas = new ArrayList<>();
    String IDBios = "";
    Zoologico zoologico ;
    String IDZoo = "";

    public Profesional(int cedula, String area, String horaInicio, String horaSalida) {
        this.cedula = cedula;
        this.area = area;
        this.horaInicio = horaInicio;
        this.horaSalida = horaSalida;
    }

    public static void setZoologicos(Zoologico zoologico, Profesional profesional) {
        profesional.zoologico = zoologico;
        zoologico.profesionales.add(profesional);
    }

    public String getBiomaID(){
        ArrayList<Integer> idBiomas = new ArrayList<>();
        for (Bioma bioma : biomas) {
            idBiomas.add(bioma.id);
        }
        return idBiomas.toString();
    }

    public static void setBiomas(Bioma nuevoBio, Profesional profesional){
        profesional.biomas.add(nuevoBio);
        nuevoBio.profesionales.add(profesional);
    }

    public Profesional(JSONObject toJavaObj){
        JSONObject profesional = (JSONObject) toJavaObj.get("profesional");
        this.cedula = (int) profesional.get("cedula");
        this.area = (String) profesional.get("area");
        this.horaInicio = (String) profesional.get("horaInicio");
        this.horaSalida = (String) profesional.get("horaSalida");
        this.IDZoo = (String) profesional.get("ID Zoologico");
        this.IDBios = (String) profesional.get("ID Biomas");

    }

    public JSONObject toJSONObj(){
        if (biomas.isEmpty()) IDBios = "N/A";
        else for (Bioma bioma : biomas) { IDBios += bioma.id + " "; }
        if (zoologico == null) IDZoo = "N/A";
        else IDZoo = zoologico.nit;

        JSONObject proDetails = new JSONObject();
        proDetails.put("cedula", cedula);
        proDetails.put("area", area);
        proDetails.put("horaInicio", horaInicio);
        proDetails.put("horaSalida", horaSalida);
        proDetails.put("ID Zoologico", IDZoo);
        proDetails.put("ID Biomas", IDBios);

        JSONObject profesionalObj = new JSONObject();
        profesionalObj.put("profesional", proDetails);

        return profesionalObj;
    }

    @Override
    public String toString() {
        return "Profesional{" +
                "cedula=" + cedula +
                ", area='" + area + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", ID de biomas asociados= " + getBiomaID() +
                ", ID del zoologico asociado= " + ((zoologico == null) ? "N/A" : zoologico.nit) +
                '}';
    }
}
