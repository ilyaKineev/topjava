package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        return userRepository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userRepository.put(user.getId(), user);
            return user;
        } else {
            return userRepository.computeIfPresent(user.getId(), (id, oldUser) -> user);
        }
    }

    @Override
    public User get(int id) {
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.values()
                .stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.values()
                .stream()
                .filter(e -> email.equals(e.getEmail()))
                .findFirst().orElse(null);
    }
}
