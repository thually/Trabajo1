import java.util.Comparator;

public class aNivelAgresividadComparator implements Comparator<Animal> {
    public int compare(Animal nivelAgresividad1, Animal nivelAgresividad2){
        if (nivelAgresividad1.nivelAgresividad < nivelAgresividad2.nivelAgresividad){
            return -1;
        }else if(nivelAgresividad1.nivelAgresividad == nivelAgresividad2.nivelAgresividad){
            return 0;
        }else{
            return 1;
        }
    }
}
