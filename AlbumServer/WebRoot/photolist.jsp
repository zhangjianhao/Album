<%@page import="com.zjianhao.biz.AlbumBiz"%>
<%@page import="com.zjianhao.model.Album"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
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
    
    <title>My JSP 'photolist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

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
                 <li role="presentation"><a href="<%=path%>/userlist.jsp">用户列表</a></li>
                <li role="presentation"  class="active"><a href="<%=path%>/photolist.jsp">照片列表</a></li>

            </ul>
        </div>

        <div class="col-md-9">

            <ul class="list-group">
                <a class="list-group-item active">
                    相册列表
                </a>

 <%
                String pageStr = request.getParameter("page");
                if (pageStr == null)
                	pageStr = "1";
                	
                	int  pageNum = Integer.parseInt(pageStr);
                ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  		 			AlbumBiz albumBiz = (AlbumBiz)context.getBean("albumBiz");
  		 			List<Album> albums = albumBiz.getAlbums(pageNum, 10);
  		 			for (Album album:albums){
                 %>
                
                <div class="list-group-item">
                    <img src="<%=path%>/<%=album.getThumbnail()%>"  class="img-thumbnail" style="width: 80px; height: 80px"
                         alt="图片">
                    <a href="<%=path %>/photos.jsp?userId=<%=album.getUser().getId()%>" style="float:right;line-height:80px;margin-right: 20px;">查看相册</a>
                    <p style="float: right;line-height: 80px;margin-right: 20px;"><%=album.getDate() %></p>

                </div>
                
 <%} %>
            </ul>

        </div>
    </div>
</div>

    
  
    

  </body>
</html>
