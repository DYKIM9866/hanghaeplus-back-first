package io.hhplus.tdd;

import io.hhplus.tdd.point.PointController;
import io.hhplus.tdd.point.PointService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PointController.class)
public class PointControllerTest {

    @Mock
    private PointService pointService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void point() throws Exception {
        mockMvc.perform(
                get("/point")
                        .param("id", Long.toString(1L)))
                .andExpect(status().isOk());//200

    }


}
