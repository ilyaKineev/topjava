package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealService {

    private MealRepository mealRepository;

    public MealService(MealRepository repository) {
        this.mealRepository = repository;
    }

    public Meal create(Meal meal , int userId) {
        return mealRepository.save(meal, userId);
    }

    public void delete(int id,int userId) {
        checkNotFoundWithId(mealRepository.delete(id,userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(mealRepository.get(id,userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        return mealRepository.getAll(userId);
    }

    public void update(Meal meal,int userId) {
        checkNotFoundWithId(mealRepository.save(meal,userId), meal.getId());
    }

    public Collection<Meal> getAllBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return mealRepository.getAllBetween(startDateTime,endDateTime,userId);
    }
}