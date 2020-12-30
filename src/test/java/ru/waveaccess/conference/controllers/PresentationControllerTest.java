package ru.waveaccess.conference.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.waveaccess.conference.dto.PresentationDto;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.repositories.UsersRepository;
import ru.waveaccess.conference.security.AuthenticationAttemptService;
import ru.waveaccess.conference.services.PresentationsService;
import ru.waveaccess.conference.services.UsersService;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PresentationController.class)
public class PresentationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    PresentationsService presentationsService;
    @MockBean
    AuthenticationAttemptService authenticationAttemptService;
    @MockBean
    UsersRepository usersRepository;
    @MockBean
    UsersService usersService;

    @SneakyThrows
    @WithMockUser(username = "presenter", password = "$2a$10$Kxsi6p3vU1Ljh8y7cukt9O649ADS.P/qwbmX/j7aSDchx1q1Vwdj6", authorities = "PRESENTER")
    @Test
    public void createPresentationTest_SupplyPresentationDtoAsPresenter_ReturnsOk() {
        PresentationDto presentationDto = PresentationDto.builder()
                .about("about")
                .title("title")
                .build();

        mockMvc.perform(post("/presentations")
                .contentType("application/json")
                .with(csrf())
                .content(objectMapper.writeValueAsString(presentationDto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "listener", password = "$2a$10$Kxsi6p3vU1Ljh8y7cukt9O649ADS.P/qwbmX/j7aSDchx1q1Vwdj6", authorities = "LISTENER")
    @Test
    public void createPresentationTest_SupplyPresentationDtoAsListener_ThrowsForbidden() {
        PresentationDto presentationDto = PresentationDto.builder()
                .about("about")
                .title("title")
                .build();

        mockMvc.perform(post("/presentations")
                .contentType("application/json")
                .with(csrf())
                .content(objectMapper.writeValueAsString(presentationDto)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "presenter", password = "$2a$10$Kxsi6p3vU1Ljh8y7cukt9O649ADS.P/qwbmX/j7aSDchx1q1Vwdj6", authorities = "PRESENTER")
    @Test
    public void createPresentationTest_SupplyInvalidPresentationDtoAsPresenter_ThrowsBadRequest() {
        PresentationDto presentationDto = PresentationDto.builder()
                .title("title")
                .build();

        mockMvc.perform(post("/presentations")
                .contentType("application/json")
                .with(csrf())
                .content(objectMapper.writeValueAsString(presentationDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "presenter", password = "$2a$10$Kxsi6p3vU1Ljh8y7cukt9O649ADS.P/qwbmX/j7aSDchx1q1Vwdj6", authorities = "PRESENTER")
    @Test
    public void getAllByPresenterTest_SupplyNothing_ReturnsListOfPresentationDto() {
        UserDto userDto = UserDto.builder()
                .email("presenter")
                .build();

        PresentationDto presentationDto = PresentationDto.builder()
                .title("title")
                .presenters(Collections.singletonList(userDto))
                .about("about")
                .build();

        when(presentationsService.getByPresenter(userDto))
                .thenReturn(Collections.singletonList(presentationDto));
        when(usersService.findByEmail("presenter"))
                .thenReturn(userDto);

        MvcResult mvcResult = mockMvc.perform(get("/presentations")).andReturn();
        String responseAsString = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(presentationDto);

        assertThat(responseAsString).isEqualToNormalizingPunctuationAndWhitespace(
                objectMapper.writeValueAsString(expectedResponseBody));
    }

    @SneakyThrows
    @WithMockUser(username = "presenter", password = "$2a$10$Kxsi6p3vU1Ljh8y7cukt9O649ADS.P/qwbmX/j7aSDchx1q1Vwdj6", authorities = "PRESENTER")
    @Test
    public void updateTest_SupplyPresentationDto_ReturnsOk() {
        PresentationDto presentationDto = PresentationDto.builder()
                .about("about")
                .title("title")
                .build();

        mockMvc.perform(post("/presentations")
                .contentType("application/json")
                .with(csrf())
                .content(objectMapper.writeValueAsString(presentationDto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "presenter", password = "$2a$10$Kxsi6p3vU1Ljh8y7cukt9O649ADS.P/qwbmX/j7aSDchx1q1Vwdj6", authorities = "PRESENTER")
    @Test
    public void updateTest_SupplyPresentationDto_ReturnsBadRequest() {
        PresentationDto presentationDto = PresentationDto.builder()
                .about("about")
                .build();

        mockMvc.perform(post("/presentations")
                .contentType("application/json")
                .with(csrf())
                .content(objectMapper.writeValueAsString(presentationDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @SneakyThrows
    @WithMockUser(username = "presenter", password = "$2a$10$Kxsi6p3vU1Ljh8y7cukt9O649ADS.P/qwbmX/j7aSDchx1q1Vwdj6", authorities = "PRESENTER")
    @Test
    public void deleteTest_SupplyPresentationDto_ReturnsBadRequest() {
        mockMvc.perform(delete("/presentations/{id}", 1L)
                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }
}
