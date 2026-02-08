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

import poly.edu.models.entities.Category;
import poly.edu.models.entities.Product;
import poly.edu.models.services.CategoryServices;
import poly.edu.models.services.ProductServices;
import poly.edu.utils.ImageUtil;
import poly.edu.utils.PaginationUtil;

@Controller
@RequestMapping("/dashboard")
public class ProductController {

	@Autowired
	private ProductServices productServices;

	@Autowired
	private CategoryServices categoryServices;

	@ModelAttribute("product")
	public Product initProduct() {
		return new Product();
	}

	@ModelAttribute("categories")
	public List<Category> getCategories() {

		return categoryServices.findAll();
	}

	@ModelAttribute("products")
	public List<Product> getProducts() {

		return productServices.findAllDescById();
	}

	@GetMapping("/product")
	public String show(Model model, @RequestParam(required = false) String keyword,
			@RequestParam(required = false) String categoryId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<Product> filtered = productServices.filter(keyword, categoryId);
		List<Product> products = PaginationUtil.paginate(filtered, page, size);
		int pages = PaginationUtil.getTotalPages(filtered, size);

		model.addAttribute("categoryId", categoryId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pages", pages);
		model.addAttribute("page", page);
		model.addAttribute("products", products);
		model.addAttribute("currentPage", "product");

		return "product-dashboard";
	}

	@PostMapping("/product/save")
	public String save(RedirectAttributes redirect, @ModelAttribute Product product,
			@RequestParam("imageFile") MultipartFile imageFile) throws IOException {

		if (!imageFile.isEmpty()) {
			String fileName = ImageUtil.save(imageFile);
			product.setImage(fileName);
		}

		try {
			productServices.save(product);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			redirect.addFlashAttribute("message", "saveFail");
			return "redirect:/dashboard/product";
		}

		redirect.addFlashAttribute("products", List.of(product));
		redirect.addFlashAttribute("message", "saveTrue");
		return "redirect:/dashboard/product";
	}

	@GetMapping("/product/confirm-delete/{id}")
	public String confirmDelete(RedirectAttributes redirect, @PathVariable("id") Integer id) {
		Product product = productServices.findById(id);
		redirect.addFlashAttribute("deleteProduct", product);
		redirect.addFlashAttribute("showDeleteModal", true);
		return "redirect:/dashboard/product";
	}

	@GetMapping("/product/delete/{id}")
	public String delete(RedirectAttributes redirect, @PathVariable("id") Integer id) {

		try {
			Product deleted = productServices.delete(id);
			ImageUtil.delete(deleted.getImage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			redirect.addFlashAttribute("message", "deleteFail");
			return "redirect:/dashboard/product";
		}

		redirect.addFlashAttribute("message", "deleteTrue");
		return "redirect:/dashboard/product";
	}

	@GetMapping("/product/edit/{id}")
	public String edit(RedirectAttributes redirect, @PathVariable("id") Integer id) {

		Product product = productServices.findById(id);
		redirect.addFlashAttribute("product", product);
		redirect.addFlashAttribute("showModal", true); // Add this to control modal visibility

		return "redirect:/dashboard/product";
	}

	@GetMapping("/product/reset")
	public String reset() {
		return "redirect:/dashboard/product";
	}
	
	@GetMapping("/product/cancel")
	public String cancel() {
		return "redirect:/dashboard/product";
	}
}
