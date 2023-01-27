package com.feefo.technicaltest.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feefo.technicaltest.application.usecase.NormaliserUseCase;
import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.impl.NormaliserUseCaseImpl;
import com.feefo.technicaltest.entrypoint.data.converter.JobTitleConverter;
import com.feefo.technicaltest.entrypoint.data.converter.impl.JobTitleConverterImpl;
import com.feefo.technicaltest.entrypoint.data.input.JobTitleInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest
class NormalizeControllerTest {
    private MockMvc mvc;
    @InjectMocks
    private NormalizeController controller;
    @Spy
    private NormaliserUseCase usecase = spy(new NormaliserUseCaseImpl());
    @Spy
    private JobTitleConverter converter = spy(new JobTitleConverterImpl());


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldNormalizeJobTitle() throws Exception {
        HashMap<String,String> values = buildValues();
        JobTitleInput input = buildInput();

        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/normalize")
                                .content(new ObjectMapper().writeValueAsString(input))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.results").exists());
    }


    @Test
    void shouldNotNormalizeJobTitle() throws Exception {
        mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/normalize")
                                .content(new ObjectMapper().writeValueAsString("error"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }


    private HashMap<String,String> buildValues(){
        HashMap<String,String> values = new HashMap<>();
        values.put("Java engineer", "Software engineer");
        return values;
    }

    private JobTitleInput buildInput(){
        JobTitleInput input = new JobTitleInput();
        input.setJobTitles(Collections.singletonList("Java engineer"));
        return input;
    }

    private NormalizedJobTitleBusinessInput buildBusinessInput(){
        NormalizedJobTitleBusinessInput businessInput = NormalizedJobTitleBusinessInput.builder()
                .jobTitles(buildInput().getJobTitles())
                .build();

        return businessInput;
    }
}