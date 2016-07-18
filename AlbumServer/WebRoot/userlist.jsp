<%@page import="com.zjianhao.model.User"%>
<%@page import="com.zjianhao.biz.UserBiz"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if (session.getAttribute("username") == null){
	response.sendRedirect(path+"/admin.jsp");
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Title</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta charset="UTF-8">

<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</head>

<body>
	<jsp:include page="header.jsp" />

	<div class="container" style="margin-top: 80px">
		<div class="row">
			<div class="col-xs-3">
				<ul class="nav nav-pills nav-stacked">
					 <li role="presentation" class="active"><a href="<%=path%>/userlist.jsp">用户列表</a></li>
                <li role="presentation"><a href="<%=path%>/photolist.jsp">照片列表</a></li>


				</ul>
			</div>

			<div class="col-md-9">

				<ul class="list-group">
					<a class="list-group-item active"> 用户列表 </a>

					<%
                	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  		 			UserBiz userBiz = (UserBiz)context.getBean("userBiz");
                String pageStr = request.getParameter("page");
                if (pageStr == null)
                pageStr = "1";
                System.out.println(pageStr);
                int pageNum = Integer.parseInt(pageStr);
                if (pageNum>0){
				List<User> uses = userBiz.getUserList(pageNum, 10);
				for (User user:uses){
                 %>
					<div class="list-group-item">
						<a href="" style="color:grey"> 用户名：<%=user.getUsername()%> </a> <a href="<%=path%>/photos.jsp?userId=<%=user.getId()%>"
							style="float: right">查看相册</a>
						
					</div>
					<%
					}}
					 %>

				</ul>

			</div>
		</div>
		
		
		
		
		
		  	 <ul class="pagination pagination-lg" style="float: right;margin-right: 20px;">
<% if (pageNum>1) { int pageIndex = pageNum -1;%>
    <li><a href="<%=path+"/userlist.jsp?page="+pageIndex%>">&laquo;</a></li>
    <%}
    	if (pageNum<=5){
    		for (int i=1; i<=5; i++){
    		if (pageNum == i){
     %>
    <li class="active"><a href="<%=path+"/userlist.jsp?page="+i%>"><%=i%></a></li>
    <%}else {
     %>
    <li><a href="<%=path+"/userlist.jsp?page="+i%>"><%=i%></a></li>
    <%}
    if (i ==5){
    %>
     <li><a href="<%=path+"/userlist.jsp?page="+6%>">&raquo;</a></li>
   <%}}}
    if (pageNum >5){
    int maxPage = pageNum+1;
    for (int i=4; i>=0; i--){
    	int pageIndex = pageNum - i;
    	if (i==0){
     %>
     <li class="active"><a href="<%=path+"/userlist.jsp?page="+pageIndex%>"><%=pageIndex%></a></li>
   
    <%}else {%>
     <li class=""><a href="<%=path+"/userlist.jsp?page="+pageIndex%>"><%=pageIndex%></a></li>
     <%}}%>
      <li><a href="<%=path+"/userlist.jsp?page="+maxPage%>">&raquo;</a></li>
    <%}%>
    
   
</ul><br>


	</div>
	
	
	
	 

	
</body>
</html>
