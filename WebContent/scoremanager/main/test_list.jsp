<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>


		<div class="row border mx-1 mb-1 py-2 align-items-center rounded" id="filter">
            <form method="get" action="TestListSubjectExecute.action">
            	<div class="row border mx-1 mb-1 py-2 align-items-center rounded">

                    <div class="col">
                        <p>科目情報</p>
                    </div>

                    <div class="col-2">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value="">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value="">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-4">
                        <label class="form-label" for="student-f3-select">科目</label>
                        <select class="form-select" id="student-f3-select" name="f3">
                            <option value="">--------</option>
                            <c:forEach var="subject" items="${subject_list}">
                                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
                                    ${subject.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" type="submit">検索</button>
                    </div>

					<div class ="me-2 text-warnig">${errors.get("1")}</div>


					</div>
                </form>

                <hr style="margin-top: 20px; width: 100%;">

			<form method="get" action="TestListStudentExecute.action">
                <div class="row border mx-1 mb-1 py-2 align-items-center rounded">



                    <div class="col-2">
                        <p>学生情報</p>
                    </div>

                    <div class="col-4">
                        <label class="form-label" for="student-f4-input">学生番号</label>
                        <input class="form-control" type="text" id="student-f4-input" name="f4"
                               value="${f4}" maxlength="10" placeholder="学生番号を入力してください" required />
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" type="submit">検索</button>
                    </div>



                </div>
            </form>



        </div>
	<p style="color: aqua">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>



        </section>
    </c:param>
</c:import>
