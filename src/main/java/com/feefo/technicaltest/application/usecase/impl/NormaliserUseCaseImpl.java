package com.feefo.technicaltest.application.usecase.impl;

import com.feefo.technicaltest.application.usecase.NormaliserUseCase;
import com.feefo.technicaltest.application.usecase.data.converter.NormalizedJobTitleBusinessConverter;
import com.feefo.technicaltest.application.usecase.data.converter.impl.NormalizedJobTitleBusinessConverterImpl;
import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class NormaliserUseCaseImpl implements NormaliserUseCase {
    @Autowired
    private final NormalizedJobTitleBusinessConverter converter = new NormalizedJobTitleBusinessConverterImpl();

    private static final List<String> NORMALIZED_TITLES = Arrays.asList(
            "Architect",
            "Software engineer",
            "Quantity surveyor",
            "Accountant");
    private static final int minParam = 0;
    private static final int maxParam = 1;

    @Override
    public NormalizedJobTitleBusinessOutput normalise(NormalizedJobTitleBusinessInput input) {
        if (Objects.isNull(input.getJobTitles())) {
            throw new RuntimeException("Invalid Input");
        }

        List<HashMap<String, String>> results = new ArrayList<>();
        for (String jobTitle : input.getJobTitles()) {
            String normalizedTitle = null;
            try {
                HashMap<String, String> result = new HashMap<>();
                normalizedTitle = this.calculateLevenshteinDistance(jobTitle.toLowerCase());
                result.put(jobTitle, normalizedTitle);
                results.add(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return this.setOutput(results);
    }


    private String calculateLevenshteinDistance(String jobTitle) {
        String normalizedJobTitle = "";
        for (String jobTitleSplitted : jobTitle.split(" ")) {
            for (String normalized : NORMALIZED_TITLES) {
                for (String normalizedSplitted : normalized.split(" ")) {
                    int jobTitleLimit = jobTitleSplitted.length() + 1;
                    int[][] distance = new int[jobTitleLimit][];
                    int normalizedLimit = normalizedSplitted.length() + 1;

                    for (int i = 0; i < jobTitleLimit; ++i) {
                        distance[i] = new int[normalizedLimit];
                    }

                    for (int i = 0; i < jobTitleLimit; ++i) {
                        distance[i][0] = i;
                    }

                    for (int j = 0; j < normalizedLimit; ++j) {
                        distance[0][j] = j;
                    }

                    for (int i = 1; i < jobTitleLimit; ++i) {
                        for (int j = 1; j < normalizedLimit; ++j) {
                            char jobTitleChar = jobTitleSplitted.charAt(i - 1);
                            char normalizedChar = normalizedSplitted.toLowerCase().charAt(j - 1);
                            distance[i][j] = calcuteMin(
                                    distance[i - 1][j] + 1,
                                    distance[i][j - 1] + 1,
                                    distance[i - 1][j - 1] + (Objects.equals(jobTitleChar, normalizedChar) ? 0 : 1) // + substitution cost
                            );
                        }
                    }
                    if (validateScore(distance[jobTitleSplitted.length()][normalizedSplitted.length()])) {
                        return normalized;
                    }
                }
            }
        }

        return normalizedJobTitle;
    }


    private int calcuteMin(int a, int b, int c) {
        int min = Math.min(a, b);
        return Math.min(min, c);
    }

    private boolean validateScore(int value) {
        return minParam <= value && value <= maxParam;
    }


    private NormalizedJobTitleBusinessOutput setOutput(List<HashMap<String, String>> results) {
        NormalizedJobTitleBusinessOutput result = this.converter.toBusinessOutput(results);
        System.out.format(result.toString());
        return result;
    }
}
