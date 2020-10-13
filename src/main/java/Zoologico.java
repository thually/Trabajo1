import org.json.simple.*;

import java.util.ArrayList;

public class Zoologico {
    String nit;
    String nombre;
    String siglas;
    String ciudad;
    ArrayList<Bioma> biomas = new ArrayList<>();
    String IDBios = "";
    ArrayList<Profesional> profesionales = new ArrayList<>();
    String IDPros = "";
    ArrayList<ZooAmigo> zooAmigos = new ArrayList<>();
    String IDZooA = "";


    public Zoologico(String nit, String nombre, String siglas, String ciudad) {
        this.nit = nit;
        this.nombre = nombre;
        this.siglas = siglas;
        this.ciudad = ciudad;
    }

    public void setBiomas(Bioma nuevoBio, Zoologico zoologico){
        biomas.add(nuevoBio);
        nuevoBio.zoologico = zoologico;
    }

    public void setProfesional(Profesional nuevoPro, Zoologico zoologico){
        profesionales.add(nuevoPro);
        nuevoPro.zoologico = zoologico;
    }

    public void setZooAmigo(ZooAmigo nuevoZA, Zoologico zoologico){
        zooAmigos.add(nuevoZA);
        nuevoZA.zoologico = zoologico;
    }

    public String getBiomasId(){
        ArrayList<Integer> idBiomas = new ArrayList<>();
        for (Bioma bioma : biomas) {
            idBiomas.add(bioma.id);
        }
        return idBiomas.toString();
    }

    public String getProfesionalesCedula(){
        ArrayList<Integer> cedulaProfesional = new ArrayList<>();
        for (Profesional profesional : profesionales) {
            cedulaProfesional.add(profesional.cedula);
        }
        return cedulaProfesional.toString();
    }

    public String getZooAmigoCedula(){
        ArrayList<Integer> cedulaZooAmigo = new ArrayList<>();
        for (ZooAmigo zooAmigo : zooAmigos) {
            cedulaZooAmigo.add(zooAmigo.cedula);
        }
        return cedulaZooAmigo.toString();
    }

    public Zoologico(JSONObject toJavaObj){
        JSONObject zoologico = (JSONObject) toJavaObj.get("zoologico");
        this.nit = (String) zoologico.get("nit");
        this.nombre = (String) zoologico.get("nombre");
        this.siglas = (String) zoologico.get("siglas");
        this.ciudad = (String) zoologico.get("ciudad");
        this.IDBios = (String) zoologico.get("ID Biomas");
        this.IDPros = (String) zoologico.get("ID Profesionales");
        this.IDZooA = (String) zoologico.get("ID zooAmigos");
    }

    public JSONObject toJSONObj(){
        IDBios = "";
        IDPros = "";
        IDZooA = "";
        if (biomas.isEmpty()) IDBios = "NA";
        else for (Bioma bioma : biomas) { IDBios += bioma.id + " "; }
        if (profesionales.isEmpty()) IDPros = "NA";
        else for (Profesional pro : profesionales) { IDPros += pro.cedula + " "; }
        if (zooAmigos.isEmpty()) IDZooA = "NA";
        else for (ZooAmigo zooA : zooAmigos) { IDZooA += zooA.cedula + " "; }

        JSONObject zooDetails = new JSONObject();
        zooDetails.put("nit", nit);
        zooDetails.put("nombre", nombre);
        zooDetails.put("siglas", siglas);
        zooDetails.put("ciudad", ciudad);
        zooDetails.put("ID Biomas", IDBios);
        zooDetails.put("ID Profesionales", IDPros);
        zooDetails.put("ID zooAmigos", IDZooA);

        JSONObject zoologicoObj = new JSONObject();
        zoologicoObj.put("zoologico", zooDetails);

        return zoologicoObj;
    }

    @Override
    public String toString() {
        return "Zoologico{ " +
                "nit=" + nit +
                ", nombre='" + nombre + '\'' +
                ", siglas='" + siglas + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", id de biomas asociados= " + getBiomasId()+
                ", cedula de profesionales asociados= " + getProfesionalesCedula()+
                ", cedula de ZooAmigos asociados= " + getZooAmigoCedula()+
                " }";
    }
}
