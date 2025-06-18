<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">

			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<form method="get">

				<div class="row border mx-1 mb-1 py-1 align-items-center rounded" id="filter">

					<p>科目情報</p>

					<div class="col-auto">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1" style="font-size: 14px; padding: 2px 4px;width: 80px;">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-auto">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2" style="font-size: 14px; padding: 2px 4px;  width: 80px;">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-auto">
						<label class="form-label" for="student-f2-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3" style="font-size: 14px; padding: 2px 4px;  width: 200px;">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%-- 現在のnumと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject }" <c:if test="${subject==f3 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>

					<p>学生情報</p>

					<div class="col-auto">
						<label class="form-label" for="student-f2-select">学生番号</label>
						<select class="form-select" id="student-f3-select" name="f3" style="font-size: 14px; padding: 2px 4px;  width: 200px;">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%-- 現在のnumと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject }" <c:if test="${subject==f3 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>

				</div>
			</form>
		</section>
	</c:param>
</c:import>