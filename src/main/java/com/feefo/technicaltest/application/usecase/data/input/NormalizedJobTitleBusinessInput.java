package com.feefo.technicaltest.application.usecase.data.input;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NormalizedJobTitleBusinessInput {
    List<String> jobTitles;
}
