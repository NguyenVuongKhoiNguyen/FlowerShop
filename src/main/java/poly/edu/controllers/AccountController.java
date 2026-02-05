package poly.edu.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import poly.edu.models.entities.Account;
import poly.edu.models.services.AccountServices;
import poly.edu.utils.ImageUtil;
import poly.edu.utils.PaginationUtil;

@Controller
@RequestMapping("/dashboard")
public class AccountController {

	@Autowired
	private AccountServices accountServices;

	@ModelAttribute("account")
	public Account initAccount() {
		return new Account();
	}

	@ModelAttribute("accounts")
	public List<Account> getAccounts() {
		return accountServices.findAll();
	}

	@GetMapping("/account")
	public String show(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) Boolean admin, @RequestParam(required = false) Boolean activated,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		List<Account> filtered = accountServices.filter(keyword, activated, admin);
		List<Account> accounts = PaginationUtil.paginate(filtered, page, size);
		int pages = PaginationUtil.getTotalPages(filtered, size);

		model.addAttribute("keyword", keyword);
		model.addAttribute("activated", activated);
		model.addAttribute("admin", admin);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("accounts", accounts);
		model.addAttribute("currentPage", "account");

		return "account-dashboard";
	}

	@PostMapping("/account/save")
	public String save(RedirectAttributes redirect, @ModelAttribute Account account,
			@RequestParam("photoFile") MultipartFile photoFile) throws IOException {

		if (!photoFile.isEmpty()) {
			String fileName = ImageUtil.save(photoFile);
			account.setPhoto(fileName);
		}

		try {
			accountServices.save(account);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			redirect.addFlashAttribute("message", "saveFail");
			return "redirect:/dashboard/account";
		}

		redirect.addFlashAttribute("accounts", List.of(account));
		redirect.addFlashAttribute("message", "saveTrue");
		return "redirect:/dashboard/account";
	}

	@GetMapping("/account/delete/{username}")
	public String delete(RedirectAttributes redirect, @PathVariable("username") String username) {

		try {
			Account deleted = accountServices.delete(username);
			ImageUtil.delete(deleted.getPhoto());
		} catch (Exception e) {
			// TODO: handle exception
			redirect.addFlashAttribute("message", "deleteFalse");
			return "redirect:/dashboard/account";
		}

		redirect.addFlashAttribute("message", "deleteTrue");
		return "redirect:/dashboard/account";
	}

	@GetMapping("/account/edit/{username}")
	public String edit(RedirectAttributes redirect, @PathVariable("username") String username) {

		Account account = accountServices.findById(username);
		redirect.addFlashAttribute("account", account);
		redirect.addFlashAttribute("showModal", true); // Add this to control modal visibility
		return "redirect:/dashboard/account";
	}

	@GetMapping("/account/reset")
	public String reset() {
		return "redirect:/dashboard/account";
	}
	
	@GetMapping("/account/cancel")
	public String cancel() {
		return "redirect:/dashboard/account";
	}
}
