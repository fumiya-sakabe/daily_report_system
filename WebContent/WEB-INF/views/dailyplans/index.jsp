<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" />
            </div>
        </c:if>
        <h2>日々のプラン一覧</h2>
        <p><a href="<c:url value='/dailyplans/new' />">新しいプランを作成する</a></p>
        <table id="plan_list">
            <tbody>
                <tr>
                    <th class="plan_date">日付</th>
                    <th class="plan_near_goal">近い将来の目標</th>
                    <th class="plan_today_goal">今日の目標</th>
                    <th class="plan_action">操作</th>
                </tr>
                <c:forEach var="plan" items="${plans}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="plan_date"><fmt:formatDate value='${plan.plan_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="plan_near_goal"><c:out value="${plan.near_future_goal}" /></td>
                        <td class="plan_today_goal"><c:out value="${plan.today_goal}" /></td>
                        <td class="plan_action">
                            <a href="<c:url value='/dailyplans/show?id=${plan.id}' />">詳細</a>&nbsp;
                            <a href="<c:url value='/dailyplans/edit?id=${plan.id}' />">編集</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div id="pagination">
            （全 ${plans_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((plans_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/dailyplans/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>
