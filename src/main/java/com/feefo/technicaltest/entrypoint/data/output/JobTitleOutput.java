package com.feefo.technicaltest.entrypoint.data.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobTitleOutput {
    List<NormalizedOutput> results;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NormalizedOutput {
        String jobTitle;
        String normalized;
    }
}
