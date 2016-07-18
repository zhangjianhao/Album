<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if (session.getAttribute("adminname") != null){
response.sendRedirect("index.jsp");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'admin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	    <link href="css/login.css" rel="stylesheet">

  </head>
  
  <body>
  
  
      <jsp:include page="header.jsp"/>
 <div class="login">
	<div style="margin-left: 120px;">后台管理系统登陆</div>
    <form role="form" action="<%=path%>/admin.action" method="post">
            <!--<label for="name">用户名:</label>-->
            <input required type="text" class="form-control" name="username" style="height: 55px; margin-top: 30px;"
                   placeholder="请输入管理员用户名"><s:fielderror fieldName="username"></s:fielderror>
            <!--<label for="name"></label>-->
            <input required type="password" class="form-control" name="password" style="height: 55px;margin-top: 30px;"
                   placeholder="请输入管理员密码"><s:fielderror fieldName="password"></s:fielderror><br/>
        <div style="height: 100px;width: 100%;margin-top: 30px;margin-left: 30px;">
            <div style="float:left;width: 100%;padding: 20px;">
                <input type="submit" class="btn btn-primary" value="登陆"
                   style="margin:auto;width: 80%;height: 50px;padding: 13px;"></input>
            </div>

        </div>

    </form>

</div>
  
  
    <jsp:include page="bottom.jsp"/>
     
     
  </body>
  <script type="text/javascript" src="js/jquery.validate.min.js"></script>
  <script type="text/javascript">
  	$(function(){
  	 	$("#signupForm").validate();
  	});
  </script>
</html>
