package com.redmath.studentapp.student;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;



@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testStudentGet() throws Exception {
        /*mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());*/

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/246"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        /*mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student")
                        .param("name","ali a"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());*/

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student")
                        .param("name","%"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    @Order(2)
    public void testStudentDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/2")
                        .with(testUser("administrator","ADMINISTRATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/2")
                        .with(testUser("coordinator","ADMINISTRATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/2")
                        .with(testUser("coordinator","COORDINATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/2")
                        .with(testUser("coordinator","COORDINATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    @Order(3)
    public void testStudentPost() throws Exception {
        //testCreateStudent
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student")
                        .with(testUser("administrator","ADMINISTRATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"id\":1,\"name\":\"kamal\",\"age\":\"90\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student")
                        .with(testUser("coordinator","COORDINATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"id\":56,\"name\":\"kamal\",\"age\":\"90\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student")
                        .with(testUser("coordinator","ADMINISTRATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"id\":117,\"name\":\"kamal\",\"age\":\"90\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("kamal")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Matchers.is(90)));

        //-------------- testUpdateStudent ------------
        //trying to update deleted student
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student/2")
                        .with(testUser("coordinator","COORDINATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"name\":\"kamal\",\"age\":\"90\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student/1")
                        .with(testUser("administrator","ADMINISTRATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"name\":\"kamal\",\"age\":\"90\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student/1")
                        .with(testUser("coordinator","ADMINISTRATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"name\":\"kamal\",\"age\":\"90\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student/1")
                        .with(testUser("coordinator","COORDINATOR"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content("{\"name\":\"kamal\",\"age\":\"90\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    private RequestPostProcessor testUser(String uname, String authority) {
        return SecurityMockMvcRequestPostProcessors.user(uname).authorities(new SimpleGrantedAuthority(authority));
    }
}
