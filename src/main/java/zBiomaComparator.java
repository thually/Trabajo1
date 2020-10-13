import java.util.Comparator;

public class zBiomaComparator implements Comparator<Zoologico> {
    public int compare(Zoologico bioma1, Zoologico bioma2) {
        for (int i = 0; i < bioma1.biomas.size(); i++) {
            for (int j = i; j < bioma2.biomas.size(); j++) {
                if (bioma1.biomas.get(i).tipo.compareTo(bioma2.biomas.get(j).tipo) < 0) {
                    return -1;
                } else if (bioma1.biomas.get(i).tipo.equals(bioma2.biomas.get(j).tipo)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }return 0;
    }
}