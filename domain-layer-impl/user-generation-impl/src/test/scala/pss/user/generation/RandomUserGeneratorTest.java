package pss.user.generation;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import pss.mapper.user.UserMapper;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RandomUserGeneratorTest {
    protected int userCount = 5;
    protected RandomUserGenerator randomUserGenerator;

    @Before
    public void createUserGenerator() {
        randomUserGenerator = new RandomUserGenerator(new Faker(new Random(123)), userCount);
    }

    @Test
    public void generateAllUsers() {
        UserMapper userMapper = randomUserGenerator.generateUsers();
        assertEquals(userMapper.getUsers().size(), userCount);
    }
}