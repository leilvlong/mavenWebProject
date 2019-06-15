<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>角色权限管理</title>
<body>
<div>
    <form id="rolePermissionForm" action="/role/permission/add" method="post">
        <table id="dataList"
               class="table table-bordered table-striped table-hover dataTable">
            <thead>
            <tr>
                <th class="" style="padding-right: 0px">
                    <input id="selall" type="checkbox" class="icheckbox_square-blue">
                </th>
                <th class="sorting_asc">ID</th>
                <th class="sorting">权限名称</th>
                <th class="sorting">URL</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${permissions}" var="p">
                <tr>
                    <td><input name="ids" <c:if test="${fn:contains(rolePermission, '['.concat(p.id).concat(']'))}">checked="checked"</c:if> type="checkbox" value="${p.id}"></td>
                    <td>${p.id}</td>
                    <td>${p.permissionName }</td>
                    <td>${p.url}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="modal-footer">
            <%--
                隐藏表单,角色ID
            --%>
            <input type="hidden" name="roleId" value="${id}" />
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
                url:'${pageContext.request.contextPath}/role/permissionaddto',
                type:'post',
                data:$('#rolePermissionForm').serialize(),
                dataType:'json',
                success:function (data) {
                    // {"success":true,"message":"保存成功"}
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