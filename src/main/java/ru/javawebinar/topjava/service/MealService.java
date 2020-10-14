package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository repository) {
        this.mealRepository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return mealRepository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(mealRepository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(mealRepository.get(id, userId), id);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(mealRepository.save(meal, userId), meal.getId());
    }

    public List<Meal> getAll(int userId) {
        return mealRepository.getAll(userId);
    }

    public List<Meal> getAllBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return mealRepository.getAllBetween(startDateTime, endDateTime, userId);
    }
}