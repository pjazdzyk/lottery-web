package pl.lotto.testutils;

import org.jetbrains.annotations.NotNull;
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
public class MockMcvCaller {

    protected final MockMvc mockMvc;

    public MockMcvCaller(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public MvcResult makeGenericCall(Supplier<MockHttpServletRequestBuilder> requestBuilder, String callContentAsJson) throws Exception {
        return mockMvc.perform(requestBuilder.get()
                        .content(callContentAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    public MvcResult makePostControllerCall(String controllerUrl, String callContentAsJson) throws Exception {
        Supplier<MockHttpServletRequestBuilder> requestPostBuilder = () -> post(controllerUrl);
        return makeGenericCall(requestPostBuilder,callContentAsJson);
    }

    public MvcResult makeGetControllerCall(String controllerUrl, String callContentAsJson) throws Exception {
        Supplier<MockHttpServletRequestBuilder> requestGetBuilder = () -> get(controllerUrl);
        return makeGenericCall(requestGetBuilder,callContentAsJson);
    }

}
