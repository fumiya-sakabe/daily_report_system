package controllers.dailyplans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DailyPlan;
import models.Employee;
import utils.DButil;

@WebServlet("/dailyplans/edit")
public class DailyPlansEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DailyPlansEditServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DButil.createEntityManager();

        DailyPlan plan = em.find(DailyPlan.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        Employee loginEmployee = (Employee)request.getSession().getAttribute("login_employee");
        if(plan != null && loginEmployee.getId() == plan.getEmployee().getId()) {
            request.setAttribute("plan", plan);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("plan_id", plan.getId());
            request.setAttribute("tasks", _expandTasks(plan.getTasks()));
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/dailyplans/edit.jsp");
        rd.forward(request, response);
    }

    private List<String> _expandTasks(String stored) {
        String[] splitted = stored == null ? new String[]{} : stored.split("\n", -1);
        if(splitted.length >= 5) {
            return Arrays.asList(splitted);
        }
        List<String> tasks = new ArrayList<String>();
        tasks.addAll(Arrays.asList(splitted));
        while(tasks.size() < 5) {
            tasks.add("");
        }
        return tasks;
    }
}
