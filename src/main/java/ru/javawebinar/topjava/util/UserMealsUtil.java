package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).toString());
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealListFilter = new ArrayList<>();
        Map<LocalDate, Integer> dateMapCaloriesSum = new HashMap<>();
        for (UserMeal uM : mealList) {
            LocalDate lD = uM.getDateTime().toLocalDate();
            if (!dateMapCaloriesSum.containsKey(lD))
                dateMapCaloriesSum.put(lD, uM.getCalories());
            else dateMapCaloriesSum.put(lD, dateMapCaloriesSum.get(lD) + uM.getCalories());
        }
        for (UserMeal uM2 : mealList) {
            if (TimeUtil.isBetween(uM2.getDateTime().toLocalTime(), startTime, endTime))
                mealListFilter.add(new UserMealWithExceed(uM2.getDateTime(), uM2.getDescription(), uM2.getCalories(), dateMapCaloriesSum.get(uM2.getDateTime().toLocalDate()) > caloriesPerDay));
        }
        return mealListFilter;
    }
}
