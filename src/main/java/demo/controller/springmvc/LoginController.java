package demo.controller.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.data.bean.Order;
import demo.data.service.OrderDAO;

@Controller
public class LoginController {

	@Autowired
	private OrderDAO orderDao;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public String loginForm(ModelMap model, HttpServletRequest request, HttpSession session) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username: " +username+ " | password: " +password);
		if ("zk".equals(username) && "zk".equals(password)) {
			session.setAttribute("logged", true);
			return "redirect:shopping/shop";
		}
		return "redirect:index";
	}
	
	@RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
	public String confirmForm(@ModelAttribute Order order, HttpSession session, ModelMap model) {
		if (session.getAttribute("logged") != null) {
			int id = orderDao.findMaxId();
			order.setId(id);
			orderDao.saveOrder(order);
			return "redirect:confirm/" + id;
		}
		return "redirect:index";
	}
}
