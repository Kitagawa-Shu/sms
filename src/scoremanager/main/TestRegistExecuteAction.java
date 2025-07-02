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

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
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
			String entYearStr = "";
			String classNum = "";
			String subjectStr = "";
			String timesNumStr = "";
			int entYear = 0;
			int num = 0;

			ClassNumDao classNumDao = new ClassNumDao();
			LocalDate todaysDate = LocalDate.now();
			List<Test> tests = null;
			int year = todaysDate.getYear();

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


			if (entYear !=0 && !classNum.equals("0") && subjectList !=null && testNumList !=null ) {
				/* subjectを1件取得する */
				subject = subjectDao.get(student_no, teacher.getSchool());

				tests = testDao.filter( entYear, classNum, subject, num,teacher.getSchool());
			}


			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectStr);
			req.setAttribute("f4", timesNumStr);


			// JSPに渡すデータをセット
			req.setAttribute("class_num_set", list);
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("subject_set", subjectList); // ★ 科目一覧
			req.setAttribute("times_num_set", testNumList);     // ★ テスト回数
			req.setAttribute("tests", tests);

			// 登録画面にフォワード
			req.getRequestDispatcher("TestRegist.action").forward(req, res);
		}
	}

}
