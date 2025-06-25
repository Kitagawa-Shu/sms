package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject; // ← 追加
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao; // ← 追加
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の定義
		ClassNumDao classNumDao = new ClassNumDao();
		TestDao testDao = new TestDao();
		SubjectDao subjectDao = new SubjectDao(); // ← 科目用DAOを初期化
		Test test = new Test();
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		// リクエストパラメーターの取得（※不要なら省略）

		// 学校コードからクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());

		// 入学年度セットの作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}

		// ★ 科目一覧を取得
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

		// ★ テスト回数リスト


		// JSPに渡すデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set", subjectList); // ★ 科目一覧
		req.setAttribute("times_num_set", null);     // ★ テスト回数

		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
