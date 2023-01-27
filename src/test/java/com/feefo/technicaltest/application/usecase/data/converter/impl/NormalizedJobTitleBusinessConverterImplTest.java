package com.feefo.technicaltest.application.usecase.data.converter.impl;

import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NormalizedJobTitleBusinessConverterImplTest {
    private NormalizedJobTitleBusinessConverterImpl converter = new NormalizedJobTitleBusinessConverterImpl();

    @Test
    void toBusinessOutput(){
        List<HashMap<String, String>> input = new ArrayList<>();
        HashMap<String,String> values = new HashMap<>();

        values.put("jobTitle", "normalized");
        input.add(values);

        NormalizedJobTitleBusinessOutput output = converter.toBusinessOutput(input);

        assertTrue(output.getResults().size() == 1);
        assertTrue(output.getResults().get(0).equals(values));
    }


    @Test
    void prepareToBusinessOutput(){
        String input = "jobTitle";
        String normalized = "normalized";

        HashMap<String, String> output = converter.prepareToBusinessOutput(input, normalized);

        assertTrue(output.containsValue(normalized));
        assertTrue(output.containsKey(input));
    }
}