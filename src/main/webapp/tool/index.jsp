<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="blackboard.data.user.User,
                 blackboard.data.course.Course" 
%>
                 
<%@ taglib uri="/bbNG" prefix="bbNG" %>

<bbNG:learningSystemPage ctxId="ctx" title="Hello World">
  <bbNG:pageHeader>
  	<bbNG:breadcrumbBar>
      <bbNG:breadcrumb>Hello World</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>
    <bbNG:pageTitleBar title="Hello World"/>
  </bbNG:pageHeader>
  <p>Please migrate me to LTI and REST. I am so tired of being stuck in this JVM tied to Java and sandboxed by Learn. :(</p>

  <h3>Here is some basic information for you:</h3>
  <p>
    <%
      User user=ctx.getUser();
      Course course=ctx.getCourse();    
    %>
    <table>
      <thead>
        <tr>
          <th>Key</th>
          <th>Value</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Username</td>
          <td><%=user.getUserName()%></td>
        </tr>
        <tr>
          <td>User UUID</td>
          <td><%=user.getUuid()%></td>
        </tr>
        <tr>
          <td>User BatchUID</td>
          <td><%=user.getBatchUid()%></td>
        </tr>
        <tr>
          <td>Course ID</td>
          <td><%=course.getCourseId()%></td>
        </tr>
        <tr>
          <td>Course Title</td>
          <td><%=course.getTitle()%></td>
        </tr>
        <tr>
          <td>Course UUID</td>
          <td><%=course.getUuid()%></td>
        </tr>
        <tr>
          <td>Course BatchUID</td>
          <td><%=course.getBatchUid()%></td>
        </tr>
      </tbody>
    </table>
  </p>

  
</bbNG:learningSystemPage>