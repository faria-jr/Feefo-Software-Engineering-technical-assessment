package com.feefo.technicaltest.application.usecase.data.output;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Builder
@Data
public class NormalizedJobTitleBusinessOutput {
    List<HashMap<String,String>> results;
}
