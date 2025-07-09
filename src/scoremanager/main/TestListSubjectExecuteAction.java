package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	HttpSession session = req.getSession();
    	Teacher teacher = (Teacher)session.getAttribute("user");
    	School school = teacher.getSchool();


    	//ローカル変数の定義
//    	int ent_year = 0;
//    	String class_num = req.getParameter("class_num");
//    	String subject_cd = req.getParameter("subject_cd");
//    	String student_no = req.getParameter("student_no");

		/*
		 * 入学年度・クラス・科目の情報をJSPから受け取る
		 */

    	int ent_year = 0;

    	if (!req.getParameter("f1").isEmpty()){
    		ent_year = Integer.parseInt(req.getParameter("f1"));
    	}


    	String class_num = req.getParameter("f2");
    	String subject_cd = req.getParameter("f3");

    	String errors = ""; // エラーメッセージ
		SubjectDao subjectDao = new SubjectDao();
		int year = LocalDate.now().getYear();
		ClassNumDao cNumDao = new ClassNumDao();




		/*
		 * 入力内容の確認
		 */

		if (ent_year == 0 ||
			class_num == null || class_num.isEmpty() ||
		    subject_cd == null || subject_cd.isEmpty()) {


			/*
			 * エラーメッセージをリクエストに設定
			 * エラーがあれば「入学年度とクラスと科目を選択してください」とメッセージを返す
			 */
			errors = "入学年度とクラスと科目を選択してください";
			req.setAttribute("errors", errors);


//			req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
//            return;
			}else{

				/*
				 * 入力された入学年度、クラス、科目の成績データを取得する(DAOを使用する)
				 */

				Subject subject = subjectDao.get(subject_cd,teacher.getSchool());
				req.setAttribute("subject", subject.getName());

				TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
				List<TestListSubject> test_list = testListSubjectDao.filter(ent_year,class_num,subject,school);
				req.setAttribute("testList", test_list);

			}

				List<Integer> entYearSet = new ArrayList<>();
				for (int i = year -10; i< year + 11; i++){
					entYearSet.add(i);
				}

				List<String> cNumlist = cNumDao.filter(teacher.getSchool());

				List<Subject>subject_list = subjectDao.filter(teacher.getSchool());

				req.setAttribute("subject_list", subject_list);
				req.setAttribute("ent_year_set", entYearSet);
				req.setAttribute("class_num_set", cNumlist);

		        req.setAttribute("class_num", class_num);
		        req.setAttribute("subject_cd", subject_cd);
		        req.setAttribute("school", school);

		        req.setAttribute("f1", ent_year);
		        req.setAttribute("f2", class_num);
		        req.setAttribute("f3", subject_cd);







		/*
		 * JSPの表示
		 */
        if(errors.isEmpty()){
        	req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);

		}else{
			req.getRequestDispatcher("TestList.action").forward(req, res);
		}


    }
}