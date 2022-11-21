package pl.lottery.mockmvccallers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
class MockMcvCaller {

    private final MockMvc mockMvc;

    public MockMcvCaller(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    MvcResult makePostMockedCallWithJsonBody(String controllerUrl, String callContentAsJson) throws Exception {
        return mockMvc.perform(post(controllerUrl)
                        .content(callContentAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    MvcResult makeGetMockedCallWithPathVariable(String controllerUrl, String pathVariable) throws Exception {
        return mockMvc.perform(get(controllerUrl + "/{pathVariable}", pathVariable))
                .andExpect(status().isOk())
                .andReturn();
    }

}
