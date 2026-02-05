package poly.edu.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {

	private static final String IMAGE_DIR = "static/images/products";
	private static final String UPLOAD_SUBDIR = "uploads"; // Subfolder for uploaded images

	public static String save(MultipartFile imageFile) throws IOException {

		if (imageFile == null || imageFile.isEmpty()) {
			return null;
		}

		File imageDir = new ClassPathResource(IMAGE_DIR).getFile();

		// Create uploads subdirectory for new uploads
		File uploadDir = new File(imageDir, UPLOAD_SUBDIR);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
		Path path = Paths.get(uploadDir.getAbsolutePath(), fileName);
		System.out.println("Saving image to: " + path.toString());

		Files.copy(imageFile.getInputStream(), path);

		// Return path relative to /images/ (e.g., "products/uploads/123456_image.jpg")
		return "products/" + UPLOAD_SUBDIR + "/" + fileName;
	}

	public static boolean delete(String fileName) {

		if (fileName == null || fileName.isBlank()) {
			return false;
		}

		try {
			// Handle both old format (just filename) and new format
			// (products/uploads/filename)
			Path path;

			if (fileName.startsWith("products/")) {
				// New format: "products/uploads/123456_image.jpg" or
				// "products/hoabo/hoabo_01.jpg"
				// Extract the part after "products/"
				String relativePath = fileName.substring("products/".length());
				File imageDir = new ClassPathResource(IMAGE_DIR).getFile();
				path = Paths.get(imageDir.getAbsolutePath(), relativePath);
			} else {
				// Old format: just filename
				File imageDir = new ClassPathResource(IMAGE_DIR).getFile();
				path = Paths.get(imageDir.getAbsolutePath(), fileName);
			}

			System.out.println("Deleting image: " + path.toString());
			// Only delete if file exists
			return Files.deleteIfExists(path);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
