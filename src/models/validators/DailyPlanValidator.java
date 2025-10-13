package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.DailyPlan;

public class DailyPlanValidator {
    public static List<String> validate(DailyPlan plan, List<String> tasks) {
        List<String> errors = new ArrayList<String>();

        String planDateError = _validatePlanDate(plan.getPlan_date());
        if(!planDateError.equals("")) {
            errors.add(planDateError);
        }

        String nearFutureError = _validateNearFutureGoal(plan.getNear_future_goal());
        if(!nearFutureError.equals("")) {
            errors.add(nearFutureError);
        }

        String todayGoalError = _validateTodayGoal(plan.getToday_goal());
        if(!todayGoalError.equals("")) {
            errors.add(todayGoalError);
        }

        String tasksError = _validateTasks(tasks);
        if(!tasksError.equals("")) {
            errors.add(tasksError);
        }

        String reflectionError = _validateReflection(plan.getReflection());
        if(!reflectionError.equals("")) {
            errors.add(reflectionError);
        }

        return errors;
    }

    private static String _validatePlanDate(java.sql.Date date) {
        if(date == null) {
            return "日付を入力してください。";
        }
        return "";
    }

    private static String _validateNearFutureGoal(String goal) {
        if(goal == null || goal.trim().equals("")) {
            return "近い将来の目標を入力してください。";
        }
        return "";
    }

    private static String _validateTodayGoal(String goal) {
        if(goal == null || goal.trim().equals("")) {
            return "今日の目標を入力してください。";
        }
        return "";
    }

    private static String _validateTasks(List<String> tasks) {
        if(tasks == null) {
            return "今日やることを入力してください。";
        }
        for(String task : tasks) {
            if(task != null && !task.trim().equals("")) {
                return "";
            }
        }
        return "今日やることを1つ以上入力してください。";
    }

    private static String _validateReflection(String reflection) {
        if(reflection == null || reflection.trim().equals("")) {
            return "今日の振り返りを入力してください。";
        }
        return "";
    }
}
