<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section>

      <form action="SubjectUpdateExecute.action" method="post">
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">
          科目情報変更
        </h2>

        <!-- 科目コード -->
        <div class="mb-3">
          <label for="cd">科目コード</label>
          <input
            class="form-control-plaintext ps-3"
            type="text"
            id="cd"
            name="cd"
            value="<c:out value='${subject != null ? subject.cd : ""}'/>"
            readonly
          />
        </div>

        <!-- 科目名 -->
        <div class="mb-3">
          <label for="name">科目名</label>
          <input
            class="form-control"
            type="text"
            id="name"
            name="name"
            value="<c:out value='${subject != null ? subject.name : ""}'/>"
            required
            maxlength="20"
          />
        </div>

        <!-- 変更ボタン -->
        <div class="text-center py-2">
          <button class="btn btn-secondary" id="login" name="login">変更</button>
        </div>
      </form>

      <!-- 戻るリンク -->
      <div class="mt-2">
        <a href="SubjectList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
