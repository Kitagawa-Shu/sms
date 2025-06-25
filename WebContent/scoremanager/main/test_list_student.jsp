<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

      <form method="get">
        <div class="row border mx-1 mb-1 py-2 align-items-center rounded" id="filter">

          <!-- 科目情報 -->
          <div class="col-12 mb-2">
            <p>科目情報</p>
          </div>

          <div class="col-2">
            <label class="form-label" for="f1">入学年度</label>
            <select class="form-select" name="f1">
              <option value="">--------</option>
              <c:forEach var="year" items="${ent_year_set}">
                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-2">
            <label class="form-label" for="f2">クラス</label>
            <select class="form-select" name="f2">
              <option value="">--------</option>
              <c:forEach var="num" items="${class_num_set}">
                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-4">
            <label class="form-label" for="f3">科目</label>
            <select class="form-select" name="f3">
              <option value="">--------</option>
              <c:forEach var="subject" items="${subject_set}">
                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
                  ${subject.name}
                </option>
              </c:forEach>
            </select>
          </div>

          <div class="col-2 text-center align-self-end">
            <button type="submit" class="btn btn-secondary">検索</button>
          </div>

          <hr class="my-3">

          <!-- 学生情報 -->
          <div class="col-12 mb-2">
            <p>学生情報</p>
          </div>

          <div class="col-4">
            <label class="form-label" for="f4">学生番号</label>
            <input class="form-control" type="text" name="f4" value="${f4}" maxlength="10"
                   placeholder="学生番号を入力してください" required />
          </div>

          <div class="col-2 text-center align-self-end">
            <button type="submit" class="btn btn-secondary">検索</button>
          </div>
        </div>
      </form>

      <!-- 氏名表示 -->
      <c:if test="${not empty student}">
        <div class="mt-4 mb-2">
          氏名：${student.name}（${student.no}）
        </div>
      </c:if>

      <!-- 成績一覧表示 -->
      <c:if test="${not empty test_list}">
        <table class="table table-hover mt-2">
          <thead>
            <tr>
              <th>科目名</th>
              <th>科目コード</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="test" items="${test_list}">
              <c:if test="${not empty test.point}">
                <tr>
                  <td>${test.subject.name}</td>
                  <td>${test.subject.cd}</td>
                  <td>${test.no}</td>
                  <td>${test.point}</td>
                </tr>
              </c:if>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <!-- データがない場合のメッセージ -->
      <c:if test="${empty test_list}">
        <div class="mt-3">学生情報が存在しませんでした。</div>
      </c:if>
    </section>
  </c:param>
</c:import>
