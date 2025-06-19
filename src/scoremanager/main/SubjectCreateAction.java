package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

//        String subject_cd = req.getParameter("cd");
//        String subject_name = "";
//        String subject_shool = "";
//        String class_num = req.getParameter("class_num");
//        boolean isAttend = true;
//
//
//
//        StudentDao studentDao = new StudentDao();
//        ClassNumDao classNumDao = new ClassNumDao();
//        SubjectDao subjectDao = new SubjectDao();
//
//
//        Subject subject = subjectDao.get(subject_cd, teacher.getSchool());
//        if (subject == null) {
//            req.setAttribute("error", "科目が見つかりません。");
//            req.getRequestDispatcher("error.jsp").forward(req, res);
//            return;
//        }
//
//
//        List<String> class_num_set = classNumDao.filter(teacher.getSchool());
//
//
//        subject_cd = subject.getCd();
//        subject_name = subject.getName();
//
//        req.setAttribute("cd", subject_cd);
//        req.setAttribute("name", subject_name);
//        req.setAttribute("class_num", class_num);
//        req.setAttribute("is_attend", isAttend);
//        req.setAttribute("class_num_set", class_num_set);
//        req.setAttribute("school", subject_shool);


        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}
