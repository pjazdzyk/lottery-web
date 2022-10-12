package pl.lotto.testutils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class MockMcvCaller {

    protected final MockMvc mockMvc;

    public MockMcvCaller(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public MvcResult makeControllerCall(String controllerUrl, String callContentAsJson) throws Exception {
        return mockMvc.perform(post(controllerUrl)
                        .content(callContentAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

}
