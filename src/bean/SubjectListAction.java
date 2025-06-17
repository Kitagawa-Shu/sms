package bean;

import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subject_cd = "";
        String subject_name = "";
        int subject = 0;

        Subject subjects = new Subject();
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();

        // パラメータ取得
        subject_cd = req.getParameter("cd");


        subject = SubjectDao.get(subject_cd);


        List<String> class_num_set = classNumDao.filter(teacher.getSchool());



        subject_cd = subject_cd.getCd());
        subject_name = subject_name.getName();



        req.setAttribute("cd", subject_cd);
        req.setAttribute("name", subject_name);
        req.setAttribute("class_num", class_num);
        req.setAttribute("is_attend", isAttend);

        req.setAttribute("class_num_set", class_num_set);

        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}
