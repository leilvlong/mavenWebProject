<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>用户角色管理</title>
<body>
<div>
    <form id="userRoleForm" action="/user/role/add" method="post">
        <table id="dataList"
               class="table table-bordered table-striped table-hover dataTable">
            <thead>
            <tr>
                <th class="" style="padding-right: 0px">
                    <input id="selall" type="checkbox" class="icheckbox_square-blue">
                </th>
                <th class="sorting_asc">ID</th>
                <th class="sorting">角色名字</th>
                <th class="sorting">角色描述</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${roleList}" var="role">
                <tr>
                    <td><input name="ids" <c:if test="${fn:contains(userRole,'['.concat(role.id).concat(']'))}">checked="checked"</c:if> type="checkbox" value="${role.id}"></td>
                    <td>${role.id}</td>
                    <td>${role.roleName }</td>
                    <td>${role.roleDesc}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="modal-footer">
            <%--
                隐藏表单,用户ID
            --%>
            <input type="hidden" name="userId" value="${id}" />
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" id="formbtn" class="btn btn-primary">提交更改</button>
        </div>
    </form>
</div>
</body>
<script>
    $(function () {
        $("#formbtn").click(function () {
            $.ajax({
                url:'${pageContext.request.contextPath}/user/roleaddto',
                type:'post',
                data:$('#userRoleForm').serialize(),
                dataType:'json',
                success:function (data) {
                    if(data.success){
                        //添加成功
                        location.reload();
                    }else{
                        alert(data.message);
                    }
                }
            });
        });
    });
</script>
</html>