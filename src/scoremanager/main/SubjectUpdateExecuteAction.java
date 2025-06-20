package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        String schoolCd = (String) session.getAttribute("school_cd");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "このフィールドを入力してください。");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        if (name.length() > 20) {
            req.setAttribute("error", "科目名は20文字以内で入力してください。");
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }


        School school = new School();
        school.setCd(schoolCd);


        Subject subject = new Subject();
        subject.setSchool(school);
        subject.setCd(cd);
        subject.setName(name);


        SubjectDao dao = new SubjectDao();
        dao.save(subject);


        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}
