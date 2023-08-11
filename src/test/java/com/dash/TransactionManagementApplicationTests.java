package com.dash;

import com.dash.entity.Transaction;
import com.dash.repository.TransactionRepository;
import com.dash.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionManagementApplicationTests {

	@Mock
	private TransactionRepository transactionRepository;

	private Transaction transaction;

	@MockBean
	private TransactionService transactionService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeEach
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


		transaction =
				Transaction.builder().id(1L).senderName("john").receiverName("maina").amount(BigDecimal.valueOf(100)).build();
	}

	@Test
	void testTransactionCreation() throws Exception {
		transaction = Transaction.builder()
				.id(1L)
				.senderName("john")
				.receiverName("maina")
				.transactionDate(LocalDate.now())
				.amount(BigDecimal.valueOf(100).stripTrailingZeros())
				.build();

		given(transactionService.createTransaction(any(Transaction.class)))
				.willAnswer((invocation)-> invocation.getArgument(0));

		ResultActions response = mockMvc.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaction)));

		response.andDo(print()).
				andExpect(status().isCreated())
				.andExpect(jsonPath("$.senderName",
						is(transaction.getSenderName())))
				.andExpect(jsonPath("$.receiverName",
						is(transaction.getReceiverName())))
				.andExpect(jsonPath("$.transactionDate",
				is(transaction.getTransactionDate().toString())));
	}


	@Test
	void givenFetchAllThenReturnAllList() throws Exception {
		List<Transaction> listOfTransactions = new ArrayList<>();
		listOfTransactions.add(Transaction.builder().id(1L).senderName("john").receiverName("maina").amount(BigDecimal.valueOf(100)).build());
		listOfTransactions.add(Transaction.builder().id(2L).senderName("john").receiverName("maina").amount(BigDecimal.valueOf(100)).build());
		given(transactionService.getAllTransactions()).willReturn(listOfTransactions);

		ResultActions response = mockMvc.perform(get("/transactions"));
		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()",
						is(listOfTransactions.size())));
	}


	void testValidGetTransactionById() throws Exception {
		long transactionId = 1L;
		transaction=Transaction.builder().id(1L).senderName("john").receiverName("maina").amount(BigDecimal.valueOf(100)).build();
		ResultActions response = mockMvc.perform(get("/transactions/{id}", transactionId));
		given(transactionService.getTransactionById(transactionId)).willReturn(transaction);

		response.andDo(print()).
				andExpect(status().isOk())
				.andExpect(jsonPath("$.senderName",
						is(transaction.getSenderName())))
				.andExpect(jsonPath("$.receiverName",
						is(transaction.getReceiverName())))
				.andExpect(jsonPath("$.transactionDate",
						is(transaction.getTransactionDate().toString())));
	}

	@Test
	void contextLoads() {
	}


}
