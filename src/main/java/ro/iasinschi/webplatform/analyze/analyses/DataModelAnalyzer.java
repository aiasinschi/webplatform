package ro.iasinschi.webplatform.analyze.analyses;

import ro.iasinschi.webplatform.model.ColumnAnalysis;
import ro.iasinschi.webplatform.util.constants.ColumnType;

import java.util.*;

/**
 * File DataModelAnalyzer.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/31/2015 5:07 PM.
 */
public class DataModelAnalyzer {

    private static Map<ColumnType, Integer> countingMap;

    private static Set<String> categoricalValueSet;

    private static Set<String> numericValueSet;


    public static List<ColumnAnalysis> analyzeColumn(List<String> column){
        countingMap = new HashMap<>();
        categoricalValueSet = new HashSet<>();
        numericValueSet = new HashSet<>();
        List<ColumnAnalysis> list = new ArrayList<>();
        boolean categorical;
        for (String elem: column){
            categorical = false;
            if ((elem == null) || (elem.trim().length() == 0)){
                addToMap(ColumnType.MISSING);
                continue;
            }
            for (char c: elem.toCharArray()){
                // could be in scientific notation
                if ((!Character.isDigit(c)) && (c != 'E') && (c != 'e')
                        && (c != '-') && (c != '.') && (c != ',')){
                    addToMap(ColumnType.CATEGORICAL);
                    categoricalValueSet.add(elem.trim());
                    categorical = true;
                    break;
                }
            }
            if (! categorical) {
                try {
                    Double value = Double.parseDouble(elem);
                    addToMap(ColumnType.NUMERIC);
                    numericValueSet.add(elem.trim());
                } catch(Exception ex){
                    addToMap(ColumnType.CATEGORICAL);
                    categoricalValueSet.add(elem.trim());
                }
            }
        }
        double SIZE = column.size();
        double missingCount = countingMap.get(ColumnType.MISSING) == null ? 0 :  countingMap.get(ColumnType.MISSING);
        double categoricalCount = countingMap.get(ColumnType.CATEGORICAL) == null ? 0 :  countingMap.get(ColumnType.CATEGORICAL);
        double numericCount = countingMap.get(ColumnType.NUMERIC) == null ? 0 :  countingMap.get(ColumnType.NUMERIC);
        ColumnAnalysis ca = new ColumnAnalysis(ColumnType.MISSING, (100.0d * missingCount) / SIZE, new HashSet<String>());
        list.add(ca);
        ca = new ColumnAnalysis(ColumnType.CATEGORICAL, (100.0d * categoricalCount) / SIZE, categoricalValueSet);
        list.add(ca);
        ca = new ColumnAnalysis(ColumnType.NUMERIC, (100.0d * numericCount) / SIZE, numericValueSet);
        list.add(ca);
        return list;
    }

    private static void addToMap(ColumnType type){
        Integer count = countingMap.get(type);
        if (count == null){
            countingMap.put(type, 1);
        } else {
            count ++;
            countingMap.put(type, count);
        }
    }

    public static void main(String[] args) {
        List<String> column = Arrays.asList(new String[]{"1.234", "-1.545", "1.3434E3", "1.E64E-1", "343223", null, "3223", "122.4"});
        List<ColumnAnalysis> analyses =  DataModelAnalyzer.analyzeColumn(column);
        System.out.println(analyses.get(0));
        System.out.println(analyses.get(1));
        System.out.println(analyses.get(2));
    }

}
