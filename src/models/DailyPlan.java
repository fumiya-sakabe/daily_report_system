package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "daily_plans")
@NamedQueries({
    @NamedQuery(
            name = "getMyDailyPlans",
            query = "SELECT d FROM DailyPlan AS d WHERE d.employee = :employee ORDER BY d.plan_date DESC, d.id DESC"
            ),
    @NamedQuery(
            name = "getMyDailyPlansCount",
            query = "SELECT COUNT(d) FROM DailyPlan AS d WHERE d.employee = :employee"
            ),
    @NamedQuery(
            name = "getMyDailyPlanByDate",
            query = "SELECT d FROM DailyPlan AS d WHERE d.employee = :employee AND d.plan_date = :plan_date"
            ),
    @NamedQuery(
            name = "getMyDailyPlanById",
            query = "SELECT d FROM DailyPlan AS d WHERE d.id = :id AND d.employee = :employee"
            )
})
@Entity
public class DailyPlan {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "plan_date", nullable = false)
    private Date plan_date;

    @Column(name = "near_future_goal", length = 255, nullable = false)
    private String near_future_goal;

    @Column(name = "today_goal", length = 255, nullable = false)
    private String today_goal;

    @Lob
    @Column(name = "tasks", nullable = false)
    private String tasks;

    @Lob
    @Column(name = "reflection", nullable = false)
    private String reflection;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(Date plan_date) {
        this.plan_date = plan_date;
    }

    public String getNear_future_goal() {
        return near_future_goal;
    }

    public void setNear_future_goal(String near_future_goal) {
        this.near_future_goal = near_future_goal;
    }

    public String getToday_goal() {
        return today_goal;
    }

    public void setToday_goal(String today_goal) {
        this.today_goal = today_goal;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getReflection() {
        return reflection;
    }

    public void setReflection(String reflection) {
        this.reflection = reflection;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
