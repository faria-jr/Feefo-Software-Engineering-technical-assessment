package com.feefo.technicaltest.application.usecase.data.converter;

import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;

import java.util.HashMap;
import java.util.List;

public interface NormalizedJobTitleBusinessConverter {
    NormalizedJobTitleBusinessOutput toBusinessOutput(List<HashMap<String, String>> input);

    HashMap<String, String> prepareToBusinessOutput(String input, String normalized);
}
