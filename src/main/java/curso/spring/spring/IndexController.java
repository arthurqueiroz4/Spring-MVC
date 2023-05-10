package curso.spring.spring;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@Controller
public class IndexController {
    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
    @GetMapping("/login")
    public ModelAndView showLoginPage(HttpServletRequest request, HttpServletResponse response,
                                      @PathParam("error") String erro) {

        ModelAndView model =  new ModelAndView("login");
        return model;
    }
}
