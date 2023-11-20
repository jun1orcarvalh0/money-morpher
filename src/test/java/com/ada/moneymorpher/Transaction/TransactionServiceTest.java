package com.ada.moneymorpher.Transaction;

import com.ada.moneymorpher.exceptions.ForbiddenException;
import com.ada.moneymorpher.exceptions.NotFoundException;
import com.ada.moneymorpher.profile.Profile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Spy
    ModelMapper modelMapper;

    @Test
    public void shouldReturnZeroBalance_whenNoTransactionsMade(){
        BigDecimal balance = transactionService.listBalance(CurrencyTypeEnum.BRL, "userTest");

        Assertions.assertEquals(balance, new BigDecimal(0));
    }

    @Test
    public void shouldReturnBRLValueBalance_whenTransactionsMade(){
        Mockito.when(transactionRepository.getBRLBalance("userTest")).thenReturn(new BigDecimal(500));

        BigDecimal balance = transactionService.listBalance(CurrencyTypeEnum.BRL, "userTest");

        Assertions.assertEquals(balance, new BigDecimal(500));
    }

    @Test
    public void shouldReturnUSDValueBalance_whenTransactionsMade(){
        Mockito.when(transactionRepository.getUSDBalance("userTest")).thenReturn(new BigDecimal(100));

        BigDecimal balance = transactionService.listBalance(CurrencyTypeEnum.USD, "userTest");

        Assertions.assertEquals(balance, new BigDecimal(100));
    }

    @Test
    public void shouldReturnEURValueBalance_whenTransactionsMade(){
        Mockito.when(transactionRepository.getEURBalance("userTest")).thenReturn(new BigDecimal(80));

        BigDecimal balance = transactionService.listBalance(CurrencyTypeEnum.EUR, "userTest");

        Assertions.assertEquals(balance, new BigDecimal(80));
    }

    @Test
    public void shoulReturnNotFoundError_whenTransactionDetailsDidntExists(){

        Assertions.assertThrows(NotFoundException.class, ()->{
            transactionService.listDetails(UUID.randomUUID(), "userTest");
        });
    }

    @Test
    public void shouldReturnForbiddenError_whenTransactionDetailsIsNotFromUser(){
        UUID uuid = UUID.randomUUID();
        String username = "userTestInvalid";

        Transaction transaction = new Transaction();
        Profile profile = new Profile();
        profile.setUsername(username);
        transaction.setProfile(profile);

        Mockito.when(transactionRepository.findByUuid(uuid)).thenReturn(
                Optional.of(transaction)
        );

        Assertions.assertThrows(ForbiddenException.class, ()->{
            transactionService.listDetails(uuid,"userTest");
        });
    }

    @Test
    public void shouldReturnTransactionDetails_whenTransactionExistsAndIsFromUser(){
        UUID uuid = UUID.randomUUID();
        String username = "userTest";

        Transaction transaction = new Transaction();
        Profile profile = new Profile();
        profile.setUsername(username);
        transaction.setProfile(profile);

        Mockito.when(transactionRepository.findByUuid(uuid)).thenReturn(
                Optional.of(transaction)
        );

        TransactionDto result = transactionService.listDetails(uuid, username);

        Assertions.assertNotNull(result);
    }

    @Test
    public void shoulReturnNotFoundError_whenTransactionUpdateDidntExists(){

        Assertions.assertThrows(NotFoundException.class, ()->{
            transactionService.update(UUID.randomUUID(), "new description","userTest");
        });
    }

    @Test
    public void shouldReturnForbiddenError_whenTransactionUpdateIsNotFromUser(){
        UUID uuid = UUID.randomUUID();
        String username = "userTestInvalid";

        Transaction transaction = new Transaction();
        Profile profile = new Profile();
        profile.setUsername(username);
        transaction.setProfile(profile);

        Mockito.when(transactionRepository.findByUuid(uuid)).thenReturn(
                Optional.of(transaction)
        );

        Assertions.assertThrows(ForbiddenException.class, ()->{
            transactionService.update(uuid,"new description","userTest");
        });
    }

    @Test
    public void shouldUpdateTransaction_whenTransactionExistsAndIsFromUser(){
        UUID uuid = UUID.randomUUID();
        String username = "userTest";

        Transaction transaction = new Transaction();
        transaction.setDescription("old description");
        Profile profile = new Profile();
        profile.setUsername(username);
        transaction.setProfile(profile);

        Mockito.when(transactionRepository.findByUuid(uuid)).thenReturn(
                Optional.of(transaction)
        );
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);

        TransactionDto updatedTransactionDto = transactionService.update(uuid, "new description", username);

        Assertions.assertNotNull(updatedTransactionDto);
        Assertions.assertEquals("new description", updatedTransactionDto.getDescription());
    }


    @Test
    public void shoulReturnNotFoundError_whenTransactionDeleteDidntExists(){

        Assertions.assertThrows(NotFoundException.class, ()->{
            transactionService.delete(UUID.randomUUID(), "userTest");
        });
    }

    @Test
    public void shouldReturnForbiddenError_whenTransactionDeleteIsNotFromUser(){
        UUID uuid = UUID.randomUUID();
        String username = "userTestInvalid";

        Transaction transaction = new Transaction();
        Profile profile = new Profile();
        profile.setUsername(username);
        transaction.setProfile(profile);

        Mockito.when(transactionRepository.findByUuid(uuid)).thenReturn(
                Optional.of(transaction)
        );

        Assertions.assertThrows(ForbiddenException.class, ()->{
            transactionService.delete(uuid,"userTest");
        });
    }

    @Test
    public void shouldDeleteTransaction_whenTransactionExistsAndIsFromUser(){
        UUID uuid = UUID.randomUUID();
        String username = "userTest";

        Transaction transaction = new Transaction();
        Profile profile = new Profile();
        profile.setUsername(username);
        transaction.setProfile(profile);

        Mockito.when(transactionRepository.findByUuid(uuid)).thenReturn(
                Optional.of(transaction)
        );

        Mockito.doNothing().when(transactionRepository).delete(transaction);

        Assertions.assertDoesNotThrow(()->{
            transactionService.delete(uuid,username);
        });
    }
}
