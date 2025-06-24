package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	HttpSession session = req.getSession();
    	Teacher teacher = (Teacher)session.getAttribute("user");

    	//ローカル変数の定義
    	String entYear = req.getParameter("ent_year");
    	String classNum = req.getParameter("class_num");
    	String subject_cd = req.getParameter("subject_cd");
    	String student_no = req.getParameter("student_no");

		/*
		 * 入学年度・クラス・科目の情報をJSPから受け取る
		 */
    	entYear = req.getParameter("f1");
    	classNum = req.getParameter("f2");
    	subject_cd = req.getParameter("f3");
    	student_no = req.getParameter("f4");
    	Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		/*
		 * 入力内容の確認
		 */

		if (entYear == null || entYear.isEmpty() ||
		    classNum == null || classNum.isEmpty() ||
		    subject_cd == null || subject_cd.isEmpty()) {

			/*
			 * エラーメッセージをリクエストに設定
			 * エラーがあれば「入学年度とクラスと科目を選択してください」とメッセージを返す
			 */
			errors.put("1", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);
			}

		/*
		 * 入力された入学年度、クラス、科目の成績データを取得する(DAOを使用する)
		 */
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subject_list = subjectDao.filter(school);
        req.setAttribute("subject_list", subject_list);


		/*
		 * 最初に受け取ったデータ(入学年度・クラス・科目)と、
		 * DBから検索して取得したリストをJSPに渡す(セットアトリビュート)
		 */



		/*
		 * JSPの表示
		 */


    }
}