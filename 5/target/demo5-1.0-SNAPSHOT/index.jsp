<%--
 Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.

  Number Guess Game
  Written by Jason Hunter, CTO, K&A Software
  http://www.servlets.com
--%>

<%@ page import = "com.demo5.NumberGuessBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mine" uri="/WEB-INF/my.tld" %><!--指定标签库的tld文件位置-->

<jsp:useBean id="numguess" class="com.demo5.NumberGuessBean" scope="session"/>
<jsp:setProperty name="numguess" property="*"/>

<html>
<head><title>Number Guess</title></head>
<body bgcolor="white">
<font size=4>

<c:choose>
	<c:when test="${numguess.success}">
		Congratulations!  You got it.
		And after just <c:out value="${numguess.numGuesses}"> </c:out> tries.<p>

<%--		<% numguess.reset(); %>--%>
		<mine:ResetTag>${numguess.reset()}</mine:ResetTag>
		Care to <a href="index.jsp">try again</a>?
	</c:when>
	
	<c:otherwise>
	
		<c:if test="${numguess.numGuesses eq 0}" >
			Welcome to the Number Guess game.<p>

			I'm thinking of a number between 1 and 100.<p>

			<form method=get>
				What's your guess? <input type=text name=guess>
				<input type=submit value="Submit">
			</form>
  
		</c:if>

		<c:if test="${numguess.numGuesses ne 0}" >

			Good guess, but nope.  Try <b><c:out value="${numguess.hint}"> </c:out></b>.

			You have made <c:out value="${numguess.numGuesses}"> </c:out> guesses.<p>

			I'm thinking of a number between 1 and 100.<p>

			<form method=get>
				What's your guess? <input type=text name=guess>
				<input type=submit value="Submit">
			</form>

		</c:if>
	
	</c:otherwise>

</c:choose>


</font>
</body>
</html>
