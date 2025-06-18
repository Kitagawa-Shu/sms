<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>


			<c:choose>
				<c:when test="true">
					<table class="table table-bordered w-75 mx-auto">
						<c:forEach var="subject" items="${subjects}">
						<label>
							<tr>
								<th>科目コード</th>
								text = input(subject_cd)
							</tr>
							<c:set var="message" value="" />
							<p>${message}</p>

						</label>
							<tr>
								<th>科目名</th><td>${subject.name}</td>
							</tr>



						</c:forEach>
					</table>
				</c:when>
			</c:choose>
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>


<%--<c:when test="${subjects.size() > 0}">--%>