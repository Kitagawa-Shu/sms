<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>

      <!-- 検索フォーム -->
      <form action="TestListStudentExecute.action" method="get">
        <div class="row border mx-1 mb-3 py-3 align-items-center rounded">

          <!-- 入学年度・クラス・科目（任意） -->
          <div class="col-2">
            <label class="form-label">入学年度</label>
            <select class="form-select" name="f1">
              <option value="">--------</option>
              <c:forEach var="year" items="${ent_year_set}">
                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-2">
            <label class="form-label">クラス</label>
            <select class="form-select" name="f2">
              <option value="">--------</option>
              <c:forEach var="num" items="${class_num_set}">
                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-4">
            <label class="form-label">科目</label>
            <select class="form-select" name="f3">
              <option value="">--------</option>
              <c:forEach var="subject" items="${subject_set}">
                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>
                  ${subject.name}
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- 学生番号 -->
          <div class="col-3">
            <label class="form-label">学生番号</label>
            <input class="form-control" type="text" name="f4" value="${f4}" maxlength="10"
                   placeholder="学生番号を入力してください" required />
          </div>

          <!-- 検索ボタン -->
          <div class="col-1 text-center align-self-end">
            <button type="submit" class="btn btn-secondary">検索</button>
          </div>
        </div>
      </form>

      <!-- 氏名表示 -->
      <c:if test="${not empty student}">
        <div class="mb-3">
          氏名：${student.name}（${student.no}）
        </div>
      </c:if>

      <!-- 成績テーブル表示 -->
      <c:if test="${not empty test_list}">
        <table class="table table-hover mt-2">
          <thead class="table-light">
            <tr>
              <th>科目名</th>
              <th>科目コード</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="test" items="${test_list}">
              <tr>
                <td>${test.subjectName}</td>
                <td>${test.subjectCd}</td>
                <td>${test.num}</td>
                <td>${test.point}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <!-- データがない場合の表示 -->
      <c:if test="${empty test_list}">
        <div class="mt-3">学生情報が存在しませんでした。</div>
      </c:if>
    </section>
  </c:param>
</c:import>
