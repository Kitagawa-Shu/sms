package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(false);


        Teacher teacher = (Teacher) session.getAttribute("user");


        String subject_cd = req.getParameter("cd");


        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subject_cd, teacher.getSchool());

        if (subject == null) {
            req.setAttribute("error", "指定された科目が見つかりません。");
            req.getRequestDispatcher("error.jsp").forward(req, res);
            return;
        }

        // subjectをJSPに渡す
        req.setAttribute("subject", subject);

        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
