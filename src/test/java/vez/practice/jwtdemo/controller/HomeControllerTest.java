package vez.practice.jwtdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vez.practice.jwtdemo.config.SecurityConfig;
import vez.practice.jwtdemo.service.TokenService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest({HomeController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
class HomeControllerTest {

    @Autowired MockMvc mvc;

    @Test
    void home_whenUnauthenticated_then401() throws Exception {

        this.mvc.perform( MockMvcRequestBuilders.get("/") )
                .andExpect(
                        MockMvcResultMatchers.status().isUnauthorized()
                );

    }

    @Test
    void home_whenAuthenticated_then200() throws Exception {

        MvcResult mvcResult = this.mvc.perform(
                MockMvcRequestBuilders.post("/token")
                        .with(httpBasic("vzateychuk", "admin"))
        )
        .andExpect( MockMvcResultMatchers.status().isOk() )
        .andReturn();

        String token = mvcResult.getResponse().getContentAsString();

        this.mvc.perform( MockMvcRequestBuilders.get("/")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        content().string("Hello: vzateychuk")
                );

    }

    @Test
    @WithMockUser
    void home_whenMockUser_then200() throws Exception {

        this.mvc.perform( MockMvcRequestBuilders.get("/") )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );

    }


}