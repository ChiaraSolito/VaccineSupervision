package View.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VaccinesList {
    static final Map<String, ArrayList<String>> covidVaccines = new HashMap<>();

    static final ArrayList<String> influenceVaccines = new ArrayList<>();

    public static ArrayList<String> getCovidVaccinesDoses(String vaccine) {
        return covidVaccines.get(vaccine);
    }

    public static ArrayList<String> getInfluenceVaccines() {
        return influenceVaccines;
    }

    public static ArrayList<String> getCovidVaccinesString() {
        return new ArrayList<>(covidVaccines.keySet());
    }

    public static void populate() {
        covidVaccines.put("Moderna", new ArrayList<>(Arrays.asList("Prima dose", "Seconda dose", "Dose booster")));
        covidVaccines.put("Pfizer", new ArrayList<>(Arrays.asList("Prima dose", "Seconda dose", "Dose booster")));
        covidVaccines.put("Astrazeneca", new ArrayList<>(Arrays.asList("Prima dose", "Seconda dose", "Dose booster")));
        covidVaccines.put("Novavax", new ArrayList<>(Arrays.asList("Prima dose", "Seconda dose", "Dose booster")));
        covidVaccines.put("Pfizer pediatrico", new ArrayList<>(Arrays.asList("Prima dose", "Seconda dose", "Dose booster")));
        covidVaccines.put("Janssen", new ArrayList<>(Arrays.asList("Unica")));
        influenceVaccines.add("Antinfluenzale A/Victoria/2570/2019");
        influenceVaccines.add("Antinfluenzale B/Austria/1359417/2021");
        influenceVaccines.add("Antinfluenzale B/Phuket/3073/2013");
        influenceVaccines.add("Antinfluenzale A/Wisconsin/588/2019");
        influenceVaccines.add("Antinfluenzale B/Austria/1359417/2021");
        influenceVaccines.add("Antinfluenzale A/Darwin/6/2021");
    }
}
