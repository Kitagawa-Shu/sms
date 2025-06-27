package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

	public class SubjectCreateExecuteAction extends Action {

	    @Override
	    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	        HttpSession session = req.getSession();
	        Teacher teacher = (Teacher)session.getAttribute("user");
	        String schoolCd = (String) session.getAttribute("school_cd");


	        String cd = req.getParameter("cd");
	        String name = req.getParameter("name");



	        /*
	         *  DBからcd変数の値で検索する
	         *  　→検索結果が「ない」：科目コードは使われていない
	         *  　→検索結果が「ある」：科目コードが使われている
	         */

	        Subject subject = new Subject();
	        SubjectDao subjectDao = new SubjectDao();

	        subject = subjectDao.get(cd,teacher.getSchool() );

	        System.out.println(subject);

	        if (subject != null) {
	            req.setAttribute("error1", "科目コードが重複しています。");
	            req.setAttribute("cd", cd);
	            req.setAttribute("name", name);

	            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
	            return;

	        }





	        if (name.length() != 3) {
	            req.setAttribute("error2", "科目コードは3文字以内で入力してください。");
	            req.setAttribute("cd", cd);
	            req.setAttribute("name", name);
	            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
	            return;
	        }


	        School school = new School();
	        school.setCd(schoolCd);


	        System.out.println(teacher.getSchool());

	        subject.setSchool(teacher.getSchool());
	        subject.setCd(cd);
	        subject.setName(name);


	        SubjectDao dao = new SubjectDao();
	        dao.save(subject);


	        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	    }
	}
