package edu.school21.numbers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.school21.exceptions.IllegalNumberException;

public class NumberWorkerTest 
{
    private NumberWorker testSubject = new NumberWorker();

    @ParameterizedTest(name = "Test prime {0}")
    @ValueSource(ints = {47, 101, 2, 3, 5})
    void isPrimeForPrimes(int num) {
        assertTrue(testSubject.isPrime(num));
    }
    
    @ParameterizedTest(name = "Test not prime {0}")
    @ValueSource(ints = {8, 16, 15, 77, 9858, 322})
    void isPrimeForNotPrimes(int num) {
        assertFalse(testSubject.isPrime(num));
    }

    @ParameterizedTest(name = "Test invalid {0}")
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int num) {
        assertThrows(
            IllegalNumberException.class, 
            () -> testSubject.isPrime(num)
        );
    }
    
    @ParameterizedTest(name = "Test sum {0} eq {1}")
    @CsvFileSource(resources = {"/data.csv"}, numLinesToSkip = 0)
    void digitsSum(int num, int expected) {
        assertEquals(expected, testSubject.digitsSum(num));
    }
}
