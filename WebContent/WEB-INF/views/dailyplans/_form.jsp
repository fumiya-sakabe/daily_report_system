<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>

<label for="plan_date">日付</label><br />
<input type="date" name="plan_date" value="<fmt:formatDate value='${plan.plan_date}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="near_future_goal">近い将来の目標</label><br />
<input type="text" name="near_future_goal" value="${plan.near_future_goal}" />
<br /><br />

<label for="today_goal">今日の目標</label><br />
<input type="text" name="today_goal" value="${plan.today_goal}" />
<br /><br />

<label for="tasks">今日やること</label><br />
<c:forEach var="task" items="${tasks}" varStatus="status">
    <input type="text" name="tasks" value="${task}" placeholder="やること${status.count}" /><br />
</c:forEach>
<p class="note">※必要に応じて未使用の欄は空欄のままにしてください。</p>

<label for="reflection">今日の振り返り</label><br />
<textarea name="reflection" rows="8" cols="50">${plan.reflection}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">保存</button>
