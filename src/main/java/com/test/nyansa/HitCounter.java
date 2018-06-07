package com.test.nyansa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


/**
 * Complexity Analysis ---
 *
 * 1. Time Complexity:
 *      Insertion complexity in the outer TreeMap : O(n * log(n)) where n is the number of lines in input file.
 *      Insertion complexity in the nested HashMap : O(n)
 *      Sorting complexity of the url hits by count : (O(k * log(k)) * m) where k are the number of url records per day and
 *                                                                            m is the number of entries of the unique dates in the outer treemap
 * 2. Space Complexity:
 *      O(m * k) where m is number of unique dates and k is number of unique urls per date.
 *      In worst case the space complexity is O(n), when every date is unique or every URL is unique.
 */


public class HitCounter {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide input file path");
            return;
        }
        printHitCountReport(args[0]);
    }

    private static void printHitCountReport(String fileName) {
        // Data structure to hold the mapping of URLs and hit count of the URL on particular date.
        // I used tree map because I want the dates sorted. In the nested map I used URLCount object
        // a value to keep the track of url and its count. It helps while printing the urls in the sorted
        // order of the count.

        Map<LocalDate, Map<String, URLCount>> dateToURLCount = new TreeMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                int splitterInd = line.indexOf('|');
                long epochTime = Long.parseLong(line.substring(0, splitterInd));
                LocalDate date = Utils.convertFromEpoch(epochTime);
                String url = line.substring(splitterInd + 1);
                if (!dateToURLCount.containsKey(date)) {
                    dateToURLCount.put(date, new HashMap<String, URLCount>());
                }
                Map<String, URLCount> innerMap = dateToURLCount.get(date);
                if (!innerMap.containsKey(url)) {
                    innerMap.put(url, new URLCount(0, url));
                }
                innerMap.get(url).increamentCountBy(1);
            }
            List<URLCount> tempList = new ArrayList<>();
            dateToURLCount.forEach((date, nestedMap) -> {
                System.out.println(date + " GMT");
                nestedMap.forEach((url, obj) -> {
                    tempList.add(obj);
                });
                Collections.sort(tempList, new Comparator<URLCount>() {
                    @Override
                    public int compare(URLCount o1, URLCount o2) {
                        return o2.getCount() - o1.getCount();
                    }
                });
                tempList.forEach((item) -> {
                    System.out.println(item.getUrl() + " " + item.getCount());
                });

                tempList.clear();
            });
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        } catch (IOException e) {
            System.out.println("Problem encountered while reading from file");
        } catch (Exception ex){
            System.out.println("Something went wrong");
        }
    }
}
