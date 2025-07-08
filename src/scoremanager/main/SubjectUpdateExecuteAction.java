package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションの取得とnullチェック
        HttpSession session = req.getSession(false);


        // ユーザー情報の取得とnullチェック
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String subject_cd = req.getParameter("cd");
        String subject_name = req.getParameter("name");


        // 文字数チェック
        if (subject_name.length() > 20) {
            Subject s = new Subject();
            s.setCd(subject_cd);
            s.setName(subject_name);
            s.setSchool(teacher.getSchool());
            req.setAttribute("subject", s);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // 更新処理
        Subject subject = new Subject();
        subject.setCd(subject_cd);
        subject.setName(subject_name);
        subject.setSchool(teacher.getSchool());

        SubjectDao subjectDao = new SubjectDao();
        boolean success = subjectDao.save(subject); // saveがbooleanを返す想定

        if (!success) {
            req.setAttribute("error", "科目情報の更新に失敗しました。");
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        // 正常完了時
        req.setAttribute("subject", subject);
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}
