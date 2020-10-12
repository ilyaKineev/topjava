package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> mealRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = mealRepository.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            mealRepository.put(userId, meals);
            return null;
        } else {
            meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            mealRepository.put(userId, meals);
            return meal;
        }

    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = mealRepository.get(userId);
        return meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = mealRepository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = mealRepository.get(userId);
        return meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public Collection<Meal> getAllBetween(LocalDateTime startDateTime,
                                       LocalDateTime endDateTime,
                                       int userId) {
        return getAll(userId).stream()
                .filter(m-> DateTimeUtil.isBetweenHalfOpen(m.getTime(),
                        startDateTime.toLocalTime(),
                        endDateTime.toLocalTime()))
                .collect(Collectors.toList());
    }
}

