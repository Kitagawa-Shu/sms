package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		/* ローカル変数の定義 */
		List<Test> testlist = new ArrayList<Test>();
		TestDao testDao =new TestDao();
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ
		String[] student_no;
		String subject = "";
		int count = 0;
		int test_point = 0;

		/* リクエストパラメータの取得 */
		subject = req.getParameter("subject");
		count = Integer.parseInt(req.getParameter("count"));
		student_no = req.getParameterValues("regist");

		/* 学生番号のリスト分繰り返す */
		for(String no:student_no){

			test_point = Integer.parseInt(req.getParameter("point_" + no));


			if (test_point < 0 || test_point > 100) { // 入学年度が未選択だった場合
				errors.put("1", "0から100の範囲で入力してください");
				// リクエストにエラーメッセージをセット
				req.setAttribute("errors", errors);
				break;

				} else {
					// studentに学生情報をセット
					Test test = new Test();
					test.setStudent(studentDao.get(no));
					test.setClassNum(studentDao.get(no).getClassNum());
					test.setSubject(subjectDao.get(subject, teacher.getSchool()));
					test.setSchool(teacher.getSchool());
					test.setNo(count);
					test.setPoint(test_point);

					testlist.add(test);

				}
		}


		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			testDao.save(testlist);
			req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合

			/* 元の一覧表示のための処理 */
			req.setAttribute("errors", errors); // ★ エラー文を渡す
		    req.setAttribute("f1", req.getParameter("ent_year"));   // 入学年度
		    req.setAttribute("f2", req.getParameter("class_num"));  // クラス
		    req.setAttribute("f3", req.getParameter("subject"));    // 科目コード
		    req.setAttribute("f4", req.getParameter("count"));      // テスト回数

			// 登録画面にフォワード
			req.getRequestDispatcher("TestRegist.action").forward(req, res);
		}
	}

}
