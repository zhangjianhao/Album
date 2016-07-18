<%@page import="com.zjianhao.model.Photo"%>
<%@page import="com.zjianhao.biz.PhotoBiz"%>
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
    
    <title>My JSP 'photos.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/bootstrap.min.css" rel="stylesheet">


  </head>
  
  <body>
        <jsp:include page="header.jsp"/>
   <div class="container" style="margin-top: 80px">
    <div class="row">
        <div class="col-xs-3">
            <ul class="nav nav-pills nav-stacked">
                <li role="presentation" ><a href="<%=path%>/userlist.jsp">用户列表</a></li>
                <li role="presentation" class="active"><a href="<%=path%>/photolist.jsp">照片列表</a></li>

            </ul>
        </div>


        <div class="col-md-9">

            <div class="panel panel-default" style="min-height: 400px">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        照片列表
                    </h3>
                </div>
                <div class="panel-body">
			<%
			
			String id = request.getParameter("userId");
			if (id != null){
				int userId = Integer.parseInt(id);
				System.out.println("query userId:"+userId);
				ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  		 			PhotoBiz photoBiz = (PhotoBiz)context.getBean("photoBiz");
  		 			if (photoBiz == null)
  		 			System.out.println("photobiz is null");
  		 			List<Photo> photos = photoBiz.getPhotos(userId);
  		 			int rows = photos.size()/6;
  		 			System.out.println("rows:"+rows);
  		 			for (int i=0; i<rows; i++){ 		 			
			 
			
			 %>

                    <div class="row">
                    <% for (int j=0; j<6; j++) {%>
                        <div class="col-sm-6 col-md-2">
                                <img src="<%=path%>/<%=photos.get(i*6+j).getPhotoUrl() %>"  class="img-thumbnail" style=";width: 100%; height: 100px"
                                     alt="图片">
                                <a href="<%=path%>/delete.action?&userId=<%=userId%>&photoId=<%=photos.get(i*6+j).getId()%>" style="text-align: center;display: block;margin: 10px auto; align-content: center">删除</a>
                        </div>
                        <%} %>
                       
                    </div>
                    
                    <%}%>
                     <div class="row">
                     <%
                    int remainSize = photos.size()%6;
                    System.out.println("remaind:"+remainSize);
                    for (int i=0; i<remainSize; i++){
                     %>
                    
                        <div class="col-sm-6 col-md-2">
                                <img src="<%=path%>/<%=photos.get(rows*6+i).getPhotoUrl() %>"  class="img-thumbnail" style=";width: 100%; height: 100px"
                                     alt="图片">
                                <a href="<%=path%>/delete.action?&userId=<%=userId%>&photoId=<%=photos.get(rows*6+i).getId()%>" style="text-align: center;display: block;margin: 10px auto; align-content: center">删除</a>
                        </div>                                   
                    <%} %>
                    </div>
                     <%} %>   





                </div>
            </div>

        </div>


    </div>
</div>


   
       <jsp:include page="bottom.jsp"/>
   
  </body>
</html>
