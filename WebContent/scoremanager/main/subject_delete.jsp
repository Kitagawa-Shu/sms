<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div id="wrap_box">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">科目情報削除</h2>
			<form action="SubjectDeleteExecute.action" method="get">
					<p>「${subject_name}(${subject_cd})」を削除してもよろしいですか</p>
					<button class="btn btn-danger" type="submit" value="削除">削除</button>

					<br>
					<br>
					<br>
					<br>
					<a href="SubjectList.action">戻る</a>
			</form>
		</div>
	</c:param>
</c:import>