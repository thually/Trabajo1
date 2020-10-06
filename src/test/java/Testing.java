import org.json.simple.JSONObject;

import java.util.Arrays;

public class Testing {
    public static void main(String[] args) {
        Zoologico zoologico = new Zoologico("1111", "Santa Fe", "SF","Medellin");
        zoologico.biomas.add(new Bioma(101, 13.312,"aaaa", "wwww"));
        zoologico.biomas.add(new Bioma(102, 13.312,"aaaa", "wwww"));
        zoologico.biomas.add(new Bioma(103, 13.312,"aaaa", "wwww"));
        zoologico.profesionales.add(new Profesional(201, "eee", "1200", "4000"));
        zoologico.profesionales.add(new Profesional(202, "eee", "1200", "4000"));
        zoologico.profesionales.add(new Profesional(203, "eee", "1200", "4000"));
        zoologico.zooAmigos.add(new ZooAmigo(10, "qqq", "31031231"));
        zoologico.zooAmigos.add(new ZooAmigo(11, "qqq", "31031231"));
        zoologico.zooAmigos.add(new ZooAmigo(12, "qqq", "31031231"));

        JSONObject zooJSON = zoologico.toJSONObj();
        System.out.println(zooJSON+"\n");
        JSONObject zooDetails = (JSONObject) zooJSON.get("zoologico");
        System.out.println(zooDetails+"\n");
        String StrBiomas = (String) zooDetails.get("ID Biomas");
        String[] a = StrBiomas.split(" ");
        System.out.println(Arrays.toString(a));
    }
}
