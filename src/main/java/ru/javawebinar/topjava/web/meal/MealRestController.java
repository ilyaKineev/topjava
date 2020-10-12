package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} for User {}", meal, userId);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} with id={} for User {}", meal, id, userId);
        service.update(meal, userId);
    }

    public Collection<Meal> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for User {}", userId);
        return service.getAll(userId);
    }

    public Collection<Meal> getAllBetween(LocalDateTime startDateTime,
                                          LocalDateTime endDateTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for User {} between {} and {}", userId, startDateTime, endDateTime);
        return service.getAllBetween(startDateTime, endDateTime, userId);
    }
}