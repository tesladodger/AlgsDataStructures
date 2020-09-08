package algs.search;

import com.tesladodger.dodgerlib.algs.search.BruteForceTSP;
import com.tesladodger.dodgerlib.algs.search.GeneticAlgTSP;
import com.tesladodger.dodgerlib.structures.HashTable;

import java.util.List;


public class TSPTest {

    public static void main (String[] args) {

        BruteForceTSP<String> bruteForceTSP = new BruteForceTSP<>();

        HashTable<String, Double> Faro = new HashTable<>();
        Faro.put("Beja", 147d);
        Faro.put("Lisboa", 278d);
        Faro.put("Porto", 554d);
        Faro.put("Guimaraes", 604d);
        Faro.put("Viseu", 530d);
        Faro.put("Guarda", 537d);
        Faro.put("Evora", 224d);
        bruteForceTSP.addNode("Faro", Faro);

        HashTable<String, Double> Beja = new HashTable<>();
        Beja.put("Faro", 147d);
        Beja.put("Lisboa", 177d);
        Beja.put("Porto", 444d);
        Beja.put("Guimaraes", 494d);
        Beja.put("Viseu", 429d);
        Beja.put("Guarda", 363d);
        Beja.put("Evora", 79d);
        bruteForceTSP.addNode("Beja", Beja);

        HashTable<String, Double> Lisboa = new HashTable<>();
        Lisboa.put("Faro", 278d);
        Lisboa.put("Beja", 177d);
        Lisboa.put("Porto", 312d);
        Lisboa.put("Guimaraes", 363d);
        Lisboa.put("Viseu", 289d);
        Lisboa.put("Guarda", 318d);
        Lisboa.put("Evora", 134d);
        bruteForceTSP.addStart("Lisboa", Lisboa);

        HashTable<String, Double> Porto = new HashTable<>();
        Porto.put("Faro", 554d);
        Porto.put("Beja", 444d);
        Porto.put("Lisboa", 312d);
        Porto.put("Guimaraes", 55d);
        Porto.put("Viseu", 128d);
        Porto.put("Guarda", 202d);
        Porto.put("Evora", 410d);
        bruteForceTSP.addNode("Porto", Porto);

        HashTable<String, Double> Guimaraes = new HashTable<>();
        Guimaraes.put("Faro", 604d);
        Guimaraes.put("Beja", 494d);
        Guimaraes.put("Lisboa", 363d);
        Guimaraes.put("Porto", 55d);
        Guimaraes.put("Viseu", 177d);
        Guimaraes.put("Guarda", 251d);
        Guimaraes.put("Evora", 415d);
        bruteForceTSP.addNode("Guimaraes", Guimaraes);

        HashTable<String, Double> Viseu = new HashTable<>();
        Viseu.put("Faro", 530d);
        Viseu.put("Beja", 429d);
        Viseu.put("Lisboa", 289d);
        Viseu.put("Porto", 128d);
        Viseu.put("Guimaraes", 177d);
        Viseu.put("Guarda", 76d);
        Viseu.put("Evora", 289d);
        bruteForceTSP.addNode("Viseu", Viseu);

        HashTable<String, Double> Guarda = new HashTable<>();
        Guarda.put("Faro", 537d);
        Guarda.put("Beja", 363d);
        Guarda.put("Lisboa", 318d);
        Guarda.put("Porto", 202d);
        Guarda.put("Guimaraes", 251d);
        Guarda.put("Viseu", 76d);
        Guarda.put("Evora", 289d);
        bruteForceTSP.addNode("Guarda", Guarda);

        HashTable<String, Double> Evora = new HashTable<>();
        Evora.put("Faro", 224d);
        Evora.put("Beja", 79d);
        Evora.put("Lisboa", 134d);
        Evora.put("Porto", 410d);
        Evora.put("Guimaraes", 415d);
        Evora.put("Viseu", 360d);
        Evora.put("Guarda", 289d);
        bruteForceTSP.addNode("Evora", Evora);

        long t = System.currentTimeMillis();
        List<String> solution = bruteForceTSP.solve();
        System.out.println("Calculation time: " + (System.currentTimeMillis() - t));

        System.out.println("Brute Force solution: \nLisboa");
        for (String elem : solution) {
            System.out.println(elem);
        }
        System.out.println("Lisboa");

        GeneticAlgTSP<String> geneticAlgTSP = new GeneticAlgTSP<>();
        geneticAlgTSP.addNode("Faro", Faro);
        geneticAlgTSP.addNode("Beja", Beja);
        geneticAlgTSP.addStart("Lisboa", Lisboa);
        geneticAlgTSP.addNode("Porto", Porto);
        geneticAlgTSP.addNode("Guimaraes", Guimaraes);
        geneticAlgTSP.addNode("Viseu", Viseu);
        geneticAlgTSP.addNode("Guarda", Guarda);
        geneticAlgTSP.addNode("Evora", Evora);

        System.out.println(geneticAlgTSP.solve(solution));

    }

}
