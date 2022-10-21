package pl.lottery.mockmvccaller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.function.Supplier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
class MockMcvCaller {

    private final MockMvc mockMvc;

    public MockMcvCaller(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    MvcResult makePostMockedCallWithJson(String controllerUrl, String callContentAsJson) throws Exception {
        return mockMvc.perform(post(controllerUrl)
                        .content(callContentAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    MvcResult makeGetMockedCallWithParam(String controllerUrl, String paramName, String paramValue) throws Exception {
        return mockMvc.perform(get(controllerUrl)
                        .param(paramName, paramValue))
                .andExpect(status().isOk())
                .andReturn();
    }

}
