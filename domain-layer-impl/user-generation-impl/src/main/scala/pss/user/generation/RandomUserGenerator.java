package pss.user.generation;

import com.github.javafaker.Faker;
import pss.data.user.User;
import pss.mapper.user.UserMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RandomUserGenerator implements UserGenerable {
    protected final Faker faker;
    protected final int userCount;

    public RandomUserGenerator(Faker faker, int userCount) {
        this.faker = faker;
        this.userCount = userCount;
    }

    @Override
    public UserMapper generateUsers() {
        Set<User> userSet = new HashSet<>();
        Map<User, Integer> userToIdMap = new HashMap<>();
        Map<Integer, User> idToUserMap = new HashMap<>();

        for (int i = 0; i < userCount; i++) {
            int userId = i + 1;
            User user = new User(userId, faker.name().firstName());
            userToIdMap.put(user, userId);
            idToUserMap.put(userId, user);
            userSet.add(user);
        }

        return new UserMapper(userSet, userToIdMap, idToUserMap);
    }
}
