package com.example.demo.student;

import com.example.demo.core.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


import java.util.List;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)

public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @Order(1)
    public void testGetAllStudent() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("{\"content\":[{\"id\":1,\"name\":\"sara a\",\"age\":12},{\"id\":2,\"name\":\"ali a\",\"age\":15}]}")
                ));
    }
    @Test
    @Order(2)
    public void testGetStudentById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("{\"id\":2,\"name\":\"ali a\",\"age\":15}")
                ));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("")
                ));
    }
    @Test
    @Order(3)
    public void testCreateStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student").contentType("application/json").content("{\"id\":3,\"name\":\"basit\",\"age\":9}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("{\"id\":3,\"name\":\"basit\",\"age\":9}")
                ));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student").contentType("application/json").content("{\"id\":2,\"name\":\"ali a\",\"age\":15}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("")
                ));
    }
    @Test
    @Order(4)
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("")
                ));
        //as we have deleted now
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("")
                ));
    }
    @Test
    @Order(5)
    public void testUpdateStudent() throws Exception {
        //as we have deleted 2 above
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student/2").contentType("application/json").content("\"name\":\"basit\",\"age\":999}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("")
                ));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/student/1").contentType("application/json").content("{\"name\":\"mehreen\",\"age\":20}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("{\"id\":1,\"name\":\"mehreen\",\"age\":20}")
                ));
    }
    @Test
    @Order(6)
    public void testGetAllStudentAfterDeletingAllStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/1"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/student/3"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.equalTo("")
                ));
    }
}
