package com.ada.moneymorpher.Transaction;

import com.ada.moneymorpher.login.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(TransactionRestController.class)
public class TransactionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "userTest")
    public void shouldReturn200AndValue_whenUserIsValid() throws Exception {
        BigDecimal expectedBalance = BigDecimal.valueOf(500);

        Mockito.when(transactionService.listBalance(CurrencyTypeEnum.BRL, "userTest")).thenReturn(
                new BigDecimal(500)
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/balance")
                        .param("currency", "BRL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedBalance));
    }

    @Test
    @WithMockUser(username = "userTest")
    public void shouldReturn400_whenCurrencyIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/balance")
                        .param("currency", "BTC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn401_whenInvalidUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/balance")
                        .param("currency", "BRL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
