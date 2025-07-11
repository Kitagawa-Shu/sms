package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String entYearStr = "";
		String classNum = "";
		String subjectStr = "";
		String timesNumStr = "";
		int entYear = 0;
		int num = 0;
		Subject subject = null;

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		LocalDate todaysDate = LocalDate.now();
		TestDao testDao =new TestDao();
		List<Test> tests = null;
		int year = todaysDate.getYear();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectStr = req.getParameter("f3");
		timesNumStr = req.getParameter("f4");



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
		List<Integer> testNumList = Arrays.asList(1, 2);


		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		/* timesNumStrをnumに数値変換して代入 */
		if (timesNumStr != null) {
			num = Integer.parseInt(timesNumStr);
		}


		if (entYear !=0 && !classNum.equals("0") && !subjectStr.equals("0") && num !=0 ) {
			/* subjectを1件取得する */
			subject = subjectDao.get(subjectStr, teacher.getSchool());
			String subject_name = subjectDao.get(subjectStr,teacher.getSchool()).getName();
			req.setAttribute("subject_name", subject_name);
			tests = testDao.filter( entYear, classNum, subject, num,teacher.getSchool());
		}else {
			errors.put("a", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
		}


		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectStr);
		req.setAttribute("f4", timesNumStr);


		// JSPに渡すデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set", subjectList); // ★ 科目一覧
		req.setAttribute("times_num_set", testNumList); // ★ テスト回数

		req.setAttribute("tests", tests);




		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
