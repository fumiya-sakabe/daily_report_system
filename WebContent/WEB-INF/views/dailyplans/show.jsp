<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>今日のプラン詳細</h2>
        <c:choose>
            <c:when test="${plan != null}">
                <table>
                    <tbody>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value='${plan.plan_date}' pattern='yyyy-MM-dd' /></td>
                        </tr>
                        <tr>
                            <th>近い将来の目標</th>
                            <td><c:out value="${plan.near_future_goal}" /></td>
                        </tr>
                        <tr>
                            <th>今日の目標</th>
                            <td><c:out value="${plan.today_goal}" /></td>
                        </tr>
                        <tr>
                            <th>今日やること</th>
                            <td>
                                <ul class="task_list">
                                    <c:forEach var="task" items="${tasks}">
                                        <c:if test="${not empty task}">
                                            <li><c:out value="${task}" /></li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <th>今日の振り返り</th>
                            <td><pre><c:out value="${plan.reflection}" /></pre></td>
                        </tr>
                    </tbody>
                </table>
                <p>
                    <a href="<c:url value='/dailyplans/edit?id=${plan.id}' />">このプランを編集する</a>
                </p>
            </c:when>
            <c:otherwise>
                <p>表示対象のプランが見つかりませんでした。</p>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/dailyplans/index' />">プラン一覧に戻る</a></p>
    </c:param>
</c:import>
