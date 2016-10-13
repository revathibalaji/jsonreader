package jsonreader;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Revathi
 * This class is used to read the uploaded json file and return the json values
 *
 */
@Controller
public class JsonReader {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(JsonReader.class);
	private static final String SLASH="/";
	private static final String APPLICATIONJSON="application/json";
	private static final String FILE="file";
	private static final String FILEUPLOADPATH="file.uploadPath";
	private static final String PLEASE_UPLOAD_FILE="Please Upload a File";
	private static final String ERROR="error";
	private static final String NOT_JSON_FORMAT="File Not in Json Format";
	private static final String OBJECT="object";
	private static final String INDEX="index";
	private static final String JSON="json";
	

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = SLASH, method = RequestMethod.GET)
	public ModelAndView getLoginCount(HttpServletRequest request) {

		return new ModelAndView(INDEX);
	}

	/**
	 * Upload the json file and read the file
	 * @param file
	 * @return jsonData
	 */
	@RequestMapping(value =SLASH, method = RequestMethod.POST,  produces =APPLICATIONJSON )
	@ResponseBody
	public ModelAndView uploadFileHandler(@RequestParam(FILE) MultipartFile file) {

		String path=MessageUtils.getMessage(FILEUPLOADPATH);//get the upload file path from property file
		String fileName = file.getOriginalFilename(); // get the file name
		
		//checking here file is uploaded or not
		if (file.getOriginalFilename().equals("")) {
			return new ModelAndView(INDEX, ERROR,
					PLEASE_UPLOAD_FILE);
		}
		//get the file path
		String filePath=path.concat(fileName);

		/**
		 * Check the file format is json or not
		 */
		boolean fileType = findFileType(filePath, fileName); 

		// if file format is mismatched return the error
		if (fileType == false) {
			LOGGER.info("false");
			return new ModelAndView(INDEX, ERROR,
					NOT_JSON_FORMAT);
		}

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file	
				File dir = new File(path);
				if (!dir.exists())
					dir.mkdirs(); //make directory				

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				LOGGER.info("Server File Location="
						+ serverFile.getAbsolutePath());


			} catch (Exception e) {
				e.getMessage();
			}
		} 

		String jsonData = "";
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader(path
					+ File.separator + fileName)); //
			while ((line = br.readLine()) != null) {
				jsonData += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		LOGGER.info("jsonData ============"+jsonData);

		//return the json data
		return new ModelAndView(INDEX,OBJECT, jsonData);

	}

	/**
	 * Check the uploaded file type is json or not
	 * @param path
	 * @param fileName
	 * @return fileType valid or not
	 */
	public boolean findFileType(String path, String fileName) {
		char extensionSeparator = '.';
		int dot = path.lastIndexOf(extensionSeparator);
		String extension = path.substring(dot + 1);

		LOGGER.info("extension" + extension);
		if (extension.equals(JSON)) {
			
			LOGGER.info(extension);
			
				return true;
			
		}
		return false;
	}






}
