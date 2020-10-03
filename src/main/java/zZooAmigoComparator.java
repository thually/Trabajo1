import java.util.Comparator;

public class zZooAmigoComparator implements Comparator<Zoologico> {
    public int compare(Zoologico zooAmigo1, Zoologico zooAmigo2) {
        for (int i = 0; i < zooAmigo1.profesionales.size(); i++) {
            for (int j = 0; j < zooAmigo2.profesionales.size(); j++) {

                if (zooAmigo1.zooAmigos.get(i).nombre.compareTo(zooAmigo2.zooAmigos.get(j).nombre) < 0) {
                    return -1;
                } else if (zooAmigo1.zooAmigos.get(i).nombre.equals(zooAmigo2.zooAmigos.get(j).nombre)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }return 0;
    }
}



