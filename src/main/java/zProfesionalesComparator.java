import java.util.Comparator;

public class zProfesionalesComparator implements Comparator<Zoologico> {
    public int compare(Zoologico profesional1, Zoologico profesional2) {
        for (int i = 0; i < profesional1.profesionales.size(); i++) {
            for (int j = 0; j < profesional2.profesionales.size(); j++) {

                if (profesional1.profesionales.get(i).area.compareTo(profesional2.profesionales.get(j).area) < 0) {
                    return -1;
                } else if (profesional1.profesionales.get(i).area.equals(profesional2.profesionales.get(j).area)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }return 0;
    }
}