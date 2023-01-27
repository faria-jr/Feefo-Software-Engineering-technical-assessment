package com.feefo.technicaltest.application.usecase.data.converter.impl;

import com.feefo.technicaltest.application.usecase.data.converter.NormalizedJobTitleBusinessConverter;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class NormalizedJobTitleBusinessConverterImpl implements NormalizedJobTitleBusinessConverter {

    @Override
    public NormalizedJobTitleBusinessOutput toBusinessOutput(List<HashMap<String, String>> input) {
        NormalizedJobTitleBusinessOutput result = NormalizedJobTitleBusinessOutput.builder().build();
        if (input.isEmpty()) {
            result.setResults(Collections.singletonList(new HashMap<>()));
        }
        result.setResults(input);
        return result;
    }

    @Override
    public HashMap<String, String> prepareToBusinessOutput(String input, String normalized) {
        HashMap<String, String> result = new HashMap<>();
        result.put(input, normalized);
        return result;
    }

}
