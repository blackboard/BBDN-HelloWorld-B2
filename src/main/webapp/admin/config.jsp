<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="   java.io.File,
                    java.io.FileWriter,
                    java.io.FileReader,
                    java.io.BufferedWriter,
                    java.io.BufferedReader,
                    java.io.IOException,
                    java.util.Base64,
                    java.util.HashMap,
                    blackboard.platform.plugin.PlugInUtil,
                    bbdn.sample.b2rest.util.CredentialUtil" 
%>
                 
<%@ taglib uri="/bbNG" prefix="bbNG" %>

<bbNG:genericPage title="Configure REST" entitlement="system.admin.VIEW">
  <bbNG:jspBlock>
    <%
        String key = "";
        String secret = "";
        
        if( request.getMethod().equals( "POST" ) )
        {
            String newKey = request.getParameter("key");
            String newSecret = request.getParameter("secret");

            String unencodedCreds = newKey + ":" + newSecret;

            CredentialUtil.saveCreds( unencodedCreds );
            response.sendRedirect("../../blackboard/admin/manage_plugins.jsp");
        }

        HashMap<String,String> creds = CredentialUtil.readCreds();

        if ( creds.size() == 2) {
            key = creds.get("key");
            secret = creds.get("secret");
        }

        pageContext.setAttribute("key",key);
        pageContext.setAttribute("secret",secret);
    %>
  </bbNG:jspBlock>
  <bbNG:pageHeader>
  	<bbNG:breadcrumbBar environment="SYS_ADMIN" navItem="admin_main">
      <bbNG:breadcrumb>REST Credentials</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>
    <bbNG:pageTitleBar title="Configure REST Credentials"/>
  </bbNG:pageHeader>
  <bbNG:form action="config.jsp" method="POST">
    <bbNG:dataCollection markUnsavedChanges="true" showSubmitButtons="true" hasRequiredFields="true">
      <bbNG:step title="Set Key and Secret">
        <bbNG:dataElement label="Key" isRequired="true">
          <bbNG:textElement name="key" value="${key}"/>
        </bbNG:dataElement>
        <bbNG:dataElement label="Secret" isRequired="true">
          <bbNG:textElement name="secret" value="${secret}"/>
        </bbNG:dataElement>
      </bbNG:step>
      <bbNG:stepSubmit title="Submit"/>
    </bbNG:dataCollection>
  </bbNG:form>
</bbNG:genericPage>