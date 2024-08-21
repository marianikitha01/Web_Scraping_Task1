package com.task1.NLP_Project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class NlpController {
    
	// Path to the file containing aggregated NLP statistics
    private static final String AGGREGATED_STATS_FILE = "C:\\\\\\\\Users\\\\\\\\Nikitha\\\\\\\\Desktop\\\\\\\\NLP- RA\\\\\\\\aggregated_results.csv";
    
    // This method handles the root URL ("/") and returns the "index" view (homepage)
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    
    // This method handles the form submission (POST request) and processes the text or file input
    @PostMapping("/process")
    public String processText(@RequestParam("text") String text,
                              @RequestParam("file") MultipartFile file,
                              Model model) throws IOException {

        // Read file content if uploaded
        if (!file.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                text = reader.lines().collect(Collectors.joining("\n"));
            }
        }

        // Generate NLP statistics for input text
        Map<String, Integer> inputStats = generateNlpStatistics(text);

        // Read aggregated results
        Map<String, Double> aggregatedStats = readAggregatedStatistics();

        // Compare statistics
        Map<String, Double> comparison = compareStatistics(inputStats, aggregatedStats);

        // Add statistics and comparison to the model
        model.addAttribute("inputStats", inputStats);
        model.addAttribute("aggregatedStats", aggregatedStats);
        model.addAttribute("comparison", comparison);
        
        // Return the "result" view to display the statistics and comparison
        return "result";
    }
    
 // This method generates NLP statistics for the given text
    private Map<String, Integer> generateNlpStatistics(String text) {
        Map<String, Integer> stats = new HashMap<>();
        String[] words = text.split("\\s+");
        String[] sentences = text.split("[.!?]\\s*");

        int wordCount = words.length;
        int sentenceCount = sentences.length;
        int nounCount = 0;
        int verbCount = 0;
        int adjCount = 0;

        // POS tagging
        for (String word : words) {
            String pos = getPosTag(word);  // Simple POS tagging logic
            if (pos.startsWith("NN")) nounCount++;
            if (pos.startsWith("VB")) verbCount++;
            if (pos.startsWith("JJ")) adjCount++;
        }
        
        // Store statistics in the map
        stats.put("Word Count", wordCount);
        stats.put("Sentence Count", sentenceCount);
        stats.put("Noun Count", nounCount);
        stats.put("Verb Count", verbCount);
        stats.put("Adjective Count", adjCount);
        return stats;
    }
    
 // method to determine the POS tag of a word
    private String getPosTag(String word) {
        if (word.endsWith("ing") || word.endsWith("ed")) return "VB";  // verb
        if (word.endsWith("ly")) return "RB";  // adverb
        if (word.endsWith("ous") || word.endsWith("ful")) return "JJ";  // adjective
        return "NN";  // assume noun as default
    }

 // This method reads the aggregated NLP statistics from a CSV file
    private Map<String, Double> readAggregatedStatistics() throws IOException {
        Map<String, Double> stats = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(AGGREGATED_STATS_FILE))) {
            String headerLine = reader.readLine(); // Read header line
            String dataLine = reader.readLine(); // Read data line
            
            if (headerLine != null && dataLine != null) {
                String[] headers = headerLine.split(",");
                String[] values = dataLine.split(",");
                
             // Check that headers and values match in length
                if (headers.length == values.length) {
                    for (int i = 0; i < headers.length; i++) {
                        String key = headers[i].trim();
                        try {
                            Double value = Double.parseDouble(values[i].trim());
                            stats.put(key, value);
                        } catch (NumberFormatException e) {
                            stats.put(key, 0.0); // Default value in case of parsing error
                        }
                    }
                }
            }
        }
        return stats;
    }

 // This method compares the input text statistics with the aggregated statistics
    private Map<String, Double> compareStatistics(Map<String, Integer> inputStats, Map<String, Double> aggregatedStats) {
        Map<String, Double> comparison = new HashMap<>();
        for (String key : inputStats.keySet()) {
            double inputValue = inputStats.getOrDefault(key, 0);
            double aggregatedValue = aggregatedStats.getOrDefault(getAggregatedKey(key), 0.0);
            comparison.put(key, inputValue - aggregatedValue);  // Difference is calculated
        }
        return comparison;
    }

    private String getAggregatedKey(String inputKey) {
        switch (inputKey) {
            case "Word Count":
                return "avg_word_count";
            case "Sentence Count":
                return "avg_sentence_count";
            case "Noun Count":
                return "avg_noun_count";
            case "Verb Count":
                return "avg_verb_count";
            case "Adjective Count":
                return "avg_adj_count";
            default:
                return ""; // Default case
        }
    }

}

