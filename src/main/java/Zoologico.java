import java.util.ArrayList;

public class Zoologico {
    String nit;
    String nombre;
    String siglas;
    String ciudad;
    ArrayList<Bioma> biomas = new ArrayList<>();
    ArrayList<Profesional> profesionales = new ArrayList<>();
    ArrayList<ZooAmigo> zooAmigo = new ArrayList<>();

    public Zoologico(String nit, String nombre, String siglas, String ciudad) {
        this.nit = nit;
        this.nombre = nombre;
        this.siglas = siglas;
        this.ciudad = ciudad;
    }

    public String getBiomasId(){
        ArrayList<Integer> idBiomas = new ArrayList<>();
        for (Bioma bioma : biomas) {
            idBiomas.add(bioma.id);
        }
        return idBiomas.toString();
    }

    public String getProfesionalesCedula(){
        ArrayList<Integer> cedulaProfecional = new ArrayList<>();
        for (Profesional profesional : profesionales) {
            cedulaProfecional.add(profesional.cedula);
        }
        return cedulaProfecional.toString();
    }

    public String getZooAmigoCedula(){
        ArrayList<Integer> cedulaZooAmigo = new ArrayList<>();
        for (ZooAmigo zooAmigo : zooAmigo) {
            cedulaZooAmigo.add(zooAmigo.cedula);
        }
        return cedulaZooAmigo.toString();
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
