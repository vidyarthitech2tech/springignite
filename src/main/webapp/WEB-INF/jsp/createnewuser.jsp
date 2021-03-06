<%--
  ~ Licensed to the Apache Software Foundation (ASF) under one
  ~ or more contributor license agreements.  See the NOTICE file
  ~ distributed with this work for additional information
  ~ regarding copyright ownership.  The ASF licenses this file
  ~ to you under the Apache License, Version 2.0 (the
  ~ "License"); you may not use this file except in compliance
  ~ with the License.  You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Apache Shiro Spring-Hibernate Sample Application</title>
      <%-- <link rel="stylesheet" type="text/css" href="<c:url value="/styles/sample.css"/>"/>  --%>
    <style>
     <%@ include file="/styles/sample.css"%>
</style>
</head>
<body>
    <div id="box">
        <div class="title">Apache Shiro Sample App - Create New User</div>

        <div class="content">
            <form:form modelAttribute="userCommand">

                <form:errors path="*" element="div" cssClass="errors"/>
                <div><div class="form-label">UserName:</div><form:input path="userName"/></div>
                <div><div class="form-label">Email:</div><form:input path="email"/></div>
                <div><div class="form-label">Password:</div><form:password path="password"/></div>
                <div><div class="form-label">Roles:</div>
               
                 <c:forEach var="role" items="${roles}">
		        		<form:checkbox path="roles" value="${role}" />${role}
                </c:forEach>
		       </div>
                  
                <div><input type="button" onclick="document.location.href='<c:url value="/manageUsers"/>'" value="Cancel"/>&nbsp;<input type="submit" value="Save Changes"/></div>
            </form:form>

            <p>Only edit the password field if you want to change the user's password.  Otherwise leave password blank.</p>
            
        </div>

    </div>

    <script type="text/javascript">
        document.getElementById('userName').focus();
    </script>

</body>
</html>
