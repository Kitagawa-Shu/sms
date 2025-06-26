<%-- 成績管理一覧 --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>

			<form method="get">
				<div class="row border mx-1 mb-1 py-2 align-items-center rounded" id="filter">

					<div class="col-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_set }">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${subject.name }" <c:if test="${subject.name==f3 }">selected</c:if>>${subject.name }</option>
							</c:forEach>
						</select>
					</div>

						<div class="col-2">
						<label class="form-label" for="student-f4-select">回数</label>
						<select class="form-select" id="student-f4-select" name="f4">
							<option value="0">--------</option>
							<c:forEach var="timesnum" items="${times_num_set }">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${timesnum }" <c:if test="${timesnum==f4 }">selected</c:if>>${timesnum }</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>

				</div>
			</form>
			<c:choose>
				<c:when test="${students.size()>0 }">
					<div>科目：${subject.name } (${timesnum })</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>点数</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="student" items="${students }">
							<tr>
								<td>${student.entYear }</td>
								<td>${student.classNum }</td>
								<td>${student.no }</td>
								<td>${student.name }</td>
								<td>
									<input class="form-control" type="text" id="point_${student.no}" name="point_${student.no}" value="${test.point}"/>
									<div class="text-warning">${errors.get(f5) }</div>
								</td>
							</tr>
						</c:forEach>
					</table>

					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">登録して終了</button>
					</div>
				</c:when>
			</c:choose>



		</section>
	</c:param>
</c:import>


