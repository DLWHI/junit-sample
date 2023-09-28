package edu.school21.numbers;

import edu.school21.exceptions.IllegalNumberException;

public class NumberWorker 
{
    public boolean isPrime(int number) {
        if (number*number < 4) {
            throw new IllegalNumberException("Invalid number");
        }
        int i = 2;
        for (; i*i < number && number%i != 0; ++i);
        return i*i >= number; 
    }

    public int digitsSum(int number) {
        int sum = 0;
        for (; number != 0; sum += number%10, number/=10);
        return (sum < 0) ? -sum : sum;
    }
}
