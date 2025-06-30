package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		int test_point = 0;

		test_point = Integer.parseInt(req.getParameter("point"));

		Test test = new Test();
		TestDao testDao =new TestDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		if (test_point >= 0 && test_point <= 100) { // 入学年度が未選択だった場合
			errors.put("1", "0から100の範囲で入力してください");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
			} else {
				// studentに学生情報をセット
				test.setPoint(test_point);
				// saveメソッドで情報を登録
				testDao.save((List<Test>) test);
			}


		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("TestRegist.action").forward(req, res);
		}
	}

}
