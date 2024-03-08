package com.project.plateapi.UserTest;

import com.google.gson.Gson;
import com.project.plateapi.user.UserController;
import com.project.plateapi.user.UserService;
import com.project.plateapi.user.Users;
import com.project.plateapi.user.dto.UserRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @DisplayName("회원가입 성공")
    @Test
    void signUpSuccess() throws Exception {
        UserRequestDto requestDto = userRequestDto();
        Mockito.doReturn(true).when(userService).createUser(ArgumentMatchers.any(Users.class));

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(userRequestDto()))
        );

        MvcResult mvcResult = actions.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(content,"Success");
    }

    private UserRequestDto userRequestDto(){
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUserId("user");
        userRequestDto.setUserPassword("password");
        userRequestDto.setEmail("email@naver.com");
        userRequestDto.setName("김철수");
        userRequestDto.setNickname("user");

        return userRequestDto;
    }

}
