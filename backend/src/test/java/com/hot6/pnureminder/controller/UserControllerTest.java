package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.Dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hot6.pnureminder.domain.user.User;
import com.hot6.pnureminder.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testAddUser() throws Exception {
        // create a new member DTO
        UserDto userDto = new UserDto();
        userDto.setKeyword("키워드 추가 테스트");


        // convert the DTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userDto);

        // send a POST request to create the new user
        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk())
                        .andReturn();

        // verify that the user was added successfully
        String responseJson = result.getResponse().getContentAsString();
        UserDto responseDto = objectMapper.readValue(responseJson, UserDto.class);
        assertNotNull(responseDto.getId());
        assertEquals("키워드 추가 테스트", responseDto.getKeyword());

    }
    @Test
    public void getUserTest() throws Exception {
        User user = new User();
        user.setKeyword("John");
        userRepository.save(user);

        // when
        MvcResult result = mockMvc.perform(
                        get("/members/" + user.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        UserDto userDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertEquals(user.getKeyword(), userDto.getKeyword());

    }
}