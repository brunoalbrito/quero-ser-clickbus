package br.com.clickbus.challenge.contoller;


import br.com.clickbus.challenge.controller.PlaceController;
import br.com.clickbus.challenge.entity.Place;
import br.com.clickbus.challenge.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService service;


    @Test
    public void whenFindAllPlacesThenReturnASimpleItem() throws Exception {

        when(service.findAll()).thenReturn(Arrays.asList(Place.builder("Cotia", "ct", "Sao Paulo", "SP")));

        MockHttpServletResponse response = mockMvc.perform(get("/place")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Cotia")))
                .andExpect(jsonPath("$[0].slug", is("ct")))
                .andExpect(jsonPath("$[0].city", is("Sao Paulo")))
                .andExpect(jsonPath("$[0].state", is("SP")))
                .andReturn().getResponse();


        verify(service, atLeastOnce()).findAll();

    }
}