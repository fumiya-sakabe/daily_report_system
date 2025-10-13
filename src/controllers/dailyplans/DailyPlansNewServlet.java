package controllers.dailyplans;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DailyPlan;

@WebServlet("/dailyplans/new")
public class DailyPlansNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DailyPlansNewServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DailyPlan plan = new DailyPlan();
        plan.setPlan_date(new Date(System.currentTimeMillis()));

        request.setAttribute("plan", plan);
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("tasks", _initializeTasks());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/dailyplans/new.jsp");
        rd.forward(request, response);
    }

    private List<String> _initializeTasks() {
        List<String> tasks = new ArrayList<String>();
        for(int i = 0; i < 5; i++) {
            tasks.add("");
        }
        return tasks;
    }
}
