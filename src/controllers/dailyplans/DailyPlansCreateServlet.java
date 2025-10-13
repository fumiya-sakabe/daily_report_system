package controllers.dailyplans;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DailyPlan;
import models.Employee;
import models.validators.DailyPlanValidator;
import utils.DButil;

@WebServlet("/dailyplans/create")
public class DailyPlansCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DailyPlansCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token == null || ! _token.equals(request.getSession().getId())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        EntityManager em = DButil.createEntityManager();

        DailyPlan plan = new DailyPlan();
        plan.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        plan.setPlan_date(_toDate(request.getParameter("plan_date")));
        plan.setNear_future_goal(request.getParameter("near_future_goal"));
        plan.setToday_goal(request.getParameter("today_goal"));

        List<String> tasks = _extractTasks(request.getParameterValues("tasks"));
        List<String> sanitizedTasks = _sanitizeTasks(tasks);
        plan.setTasks(String.join("\n", sanitizedTasks));
        plan.setReflection(request.getParameter("reflection"));

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        plan.setCreated_at(currentTime);
        plan.setUpdated_at(currentTime);

        List<String> errors = DailyPlanValidator.validate(plan, sanitizedTasks);
        if(errors.size() > 0) {
            em.close();

            request.setAttribute("plan", plan);
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("tasks", sanitizedTasks);
            request.setAttribute("errors", errors);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/dailyplans/new.jsp");
            rd.forward(request, response);
            return;
        }

        em.getTransaction().begin();
        em.persist(plan);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "今日のプランを登録しました。");
        response.sendRedirect(request.getContextPath() + "/dailyplans/index");
    }

    private Date _toDate(String param) {
        try {
            return Date.valueOf(param);
        } catch(Exception e) {
            return null;
        }
    }

    private List<String> _extractTasks(String[] taskParams) {
        if(taskParams == null) {
            return new ArrayList<String>();
        }
        return Arrays.asList(taskParams);
    }

    private List<String> _sanitizeTasks(List<String> tasks) {
        List<String> sanitized = tasks.stream()
                .map(task -> task == null ? "" : task.trim())
                .collect(Collectors.toList());
        while(sanitized.size() < 5) {
            sanitized.add("");
        }
        return sanitized;
    }
}
