package edu.school21.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImplTest {
    private static UsersRepository repoMock;
    private static UsersServiceImpl service;
    @BeforeAll
    public static void init() {
        repoMock = Mockito.mock(UsersRepository.class);
        Mockito.when(repoMock.findByLogin("steve_pathnem")).
                thenReturn(new User("steve_pathnem", "mirina"));
        Mockito.when(repoMock.findByLogin("steve_potnem")).
                thenReturn(null);
        User authed = new User("authed", "passwd");
        authed.authenticate("passwd");
        Mockito.when(repoMock.findByLogin("authed")).thenReturn(authed);
        service = new UsersServiceImpl(repoMock);
    }

    @Test
    public void correctAuthTest() {
        assertDoesNotThrow(() ->
            assertTrue(service.authenticate("steve_pathnem", "mirina"))
        );
    }

    @Test
    public void alreadyAuthTest() {
        assertThrows(
            AlreadyAuthenticatedException.class,
            () -> service.authenticate("authed", "passwd")
        );
    }

    @Test
    public void invalidPasswdTest() {
        assertDoesNotThrow(() ->
            assertFalse(service.authenticate("steve_pathnem", "not mirina"))
        );
    }

    @Test
    public void invalidLoginTest() {
        assertDoesNotThrow(() ->
            assertFalse(service.authenticate("steve_pothnem", "mirina"))
        );
    }
}
