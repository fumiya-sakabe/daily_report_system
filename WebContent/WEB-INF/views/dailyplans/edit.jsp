<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>今日のプランを編集</h2>
        <c:choose>
            <c:when test="${plan != null}">
                <form method="POST" action="<c:url value='/dailyplans/update' />">
                    <c:import url="_form.jsp" />
                </form>
            </c:when>
            <c:otherwise>
                <p>編集対象のプランが見つかりませんでした。</p>
            </c:otherwise>
        </c:choose>
        <p><a href="<c:url value='/dailyplans/index' />">プラン一覧に戻る</a></p>
    </c:param>
</c:import>
