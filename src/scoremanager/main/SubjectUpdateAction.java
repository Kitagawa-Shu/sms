package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


        HttpSession session = req.getSession();
        String schoolCd = (String) session.getAttribute("school_cd");


        School school = new School();
        school.setCd(schoolCd);


        String cd = req.getParameter("cd");


        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd, school);


        req.setAttribute("cd", subject.getCd());
        req.setAttribute("name", subject.getName());


        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
