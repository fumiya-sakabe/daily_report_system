package controllers.dailyplans;

import java.io.IOException;
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

@WebServlet("/dailyplans/index")
public class DailyPlansIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DailyPlansIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DButil.createEntityManager();

        Employee loginEmployee = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<DailyPlan> plans = em.createNamedQuery("getMyDailyPlans", DailyPlan.class)
                                  .setParameter("employee", loginEmployee)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        long plansCount = em.createNamedQuery("getMyDailyPlansCount", Long.class)
                             .setParameter("employee", loginEmployee)
                             .getSingleResult();

        em.close();

        request.setAttribute("plans", plans);
        request.setAttribute("plans_count", plansCount);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/dailyplans/index.jsp");
        rd.forward(request, response);
    }
}
