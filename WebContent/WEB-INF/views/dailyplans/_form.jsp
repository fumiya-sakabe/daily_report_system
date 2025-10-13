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

<div id="plan_preview" class="plan-preview">
    <h3>プレビュー</h3>
    <table class="plan-preview-table">
        <tbody>
            <tr>
                <th>日付</th>
                <td id="plan_preview_date">--</td>
            </tr>
            <tr>
                <th>近い将来の目標</th>
                <td id="plan_preview_near_goal">--</td>
            </tr>
            <tr>
                <th>今日の目標</th>
                <td id="plan_preview_today_goal">--</td>
            </tr>
            <tr>
                <th>今日やること</th>
                <td>
                    <ul id="plan_preview_tasks" class="task_list">
                        <li class="empty">（未入力）</li>
                    </ul>
                </td>
            </tr>
            <tr>
                <th>今日の振り返り</th>
                <td><pre id="plan_preview_reflection">--</pre></td>
            </tr>
        </tbody>
    </table>
    <p id="plan_preview_empty" class="plan-preview-empty">入力するとここにプランのプレビューが表示されます。</p>
</div>

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">保存</button>

<script>
(function() {
    var script = document.currentScript;
    if(!script) {
        var scripts = document.getElementsByTagName('script');
        script = scripts[scripts.length - 1];
    }

    var form = null;
    var parent = script ? script.parentNode : null;
    while(parent) {
        if(parent.nodeName && parent.nodeName.toLowerCase() === 'form') {
            form = parent;
            break;
        }
        parent = parent.parentNode;
    }

    if(!form) {
        return;
    }

    var dateInput = form.querySelector("input[name='plan_date']");
    var nearGoalInput = form.querySelector("input[name='near_future_goal']");
    var todayGoalInput = form.querySelector("input[name='today_goal']");
    var taskInputs = Array.prototype.slice.call(form.querySelectorAll("input[name='tasks']"));
    var reflectionInput = form.querySelector("textarea[name='reflection']");

    var datePreview = document.getElementById('plan_preview_date');
    var nearGoalPreview = document.getElementById('plan_preview_near_goal');
    var todayGoalPreview = document.getElementById('plan_preview_today_goal');
    var tasksPreview = document.getElementById('plan_preview_tasks');
    var reflectionPreview = document.getElementById('plan_preview_reflection');
    var emptyIndicator = document.getElementById('plan_preview_empty');

    function isBlank(value) {
        return !value || value.trim().length === 0;
    }

    function formatDate(value) {
        if(isBlank(value)) {
            return '--';
        }
        return value;
    }

    function updateTextContent(input, previewElement) {
        if(!input || !previewElement) {
            return;
        }
        var value = input.value;
        if(isBlank(value)) {
            previewElement.textContent = '--';
        } else {
            previewElement.textContent = value;
        }
    }

    function updateTasks() {
        if(!tasksPreview) {
            return;
        }

        while(tasksPreview.firstChild) {
            tasksPreview.removeChild(tasksPreview.firstChild);
        }

        var hasTask = false;
        taskInputs.forEach(function(input) {
            if(isBlank(input.value)) {
                return;
            }
            hasTask = true;
            var item = document.createElement('li');
            item.textContent = input.value.trim();
            tasksPreview.appendChild(item);
        });

        if(!hasTask) {
            var emptyItem = document.createElement('li');
            emptyItem.className = 'empty';
            emptyItem.textContent = '（未入力）';
            tasksPreview.appendChild(emptyItem);
        }
    }

    function updateEmptyIndicator() {
        if(!emptyIndicator) {
            return;
        }

        var hasNearGoal = !isBlank(nearGoalInput && nearGoalInput.value);
        var hasTodayGoal = !isBlank(todayGoalInput && todayGoalInput.value);
        var hasReflection = !isBlank(reflectionInput && reflectionInput.value);
        var hasTasks = false;

        taskInputs.forEach(function(input) {
            if(!isBlank(input.value)) {
                hasTasks = true;
            }
        });

        if(hasNearGoal || hasTodayGoal || hasReflection || hasTasks) {
            emptyIndicator.style.display = 'none';
        } else {
            emptyIndicator.style.display = '';
        }
    }

    function updatePreview() {
        if(datePreview && dateInput) {
            datePreview.textContent = formatDate(dateInput.value);
        }
        updateTextContent(nearGoalInput, nearGoalPreview);
        updateTextContent(todayGoalInput, todayGoalPreview);
        updateTasks();
        if(reflectionPreview && reflectionInput) {
            var reflection = reflectionInput.value;
            reflectionPreview.textContent = isBlank(reflection) ? '--' : reflection;
        }
        updateEmptyIndicator();
    }

    var inputs = [dateInput, nearGoalInput, todayGoalInput, reflectionInput];
    inputs.forEach(function(input) {
        if(!input) {
            return;
        }
        input.addEventListener('input', updatePreview);
        input.addEventListener('change', updatePreview);
    });

    taskInputs.forEach(function(input) {
        input.addEventListener('input', updatePreview);
        input.addEventListener('change', updatePreview);
    });

    updatePreview();
})();
</script>
