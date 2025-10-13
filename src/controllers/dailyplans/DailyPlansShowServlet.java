package controllers.dailyplans;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.DailyPlan;
import utils.DButil;

@WebServlet("/dailyplans/show")
public class DailyPlansShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DailyPlansShowServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DButil.createEntityManager();

        DailyPlan plan = em.find(DailyPlan.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("plan", plan);
        request.setAttribute("tasks", plan == null ? new String[]{} : plan.getTasks().split("\n", -1));
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/dailyplans/show.jsp");
        rd.forward(request, response);
    }
}
