package Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.azure.core.http.HttpResponse;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import Model.GenerateToken;
import Model.ResponseHandler;
import Model.TaskDetails;
import Model.Users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import Service.TaskDetailsService;
import Service.TokenService;
import Service.UsersService;

@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "Authorization")
public class Controller {

	@Autowired
	private UsersService adminService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private TaskDetailsService taskService;

	@Autowired
	private UsersService userService;

	GenerateToken generateToken = new GenerateToken();

	@Autowired
	private HttpServletRequest request;

	private Resource blobFile;

	@PostMapping("/getList/{selectedId}")
	public List<Map<String, Object>> getList(@PathVariable("selectedId") String selectedId) {
		return taskService.getList(selectedId);
	}

	@PostMapping("/getTaskDetails/{selectedId}")
	public List<TaskDetails> getTaskDetailsId(@PathVariable("selectedId") int selectedId,TaskDetails dto) {
		dto.setid(selectedId);
		return taskService.getId(dto);
	}
	@PostMapping("/deleteTasks")
	public boolean deleteTasks(@RequestParam("selectedId") int selectedId) {
		return taskService.delete(selectedId);
	}
	@PostMapping("/addTask")
	public ResponseEntity<Object> save(@RequestParam("status") String status,
			@RequestParam("title") String title,TaskDetails dto) {
		dto.settitle(title);
		dto.setstatus(status);
		try {
			return ResponseHandler.generateResponse("Success", HttpStatus.OK, taskService.save(dto));			
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	@PostMapping("/updateTask")
	public ResponseEntity<Object> updateTask(@RequestParam("status") String status,
			@RequestParam("title") String title,TaskDetails dto) {
		dto.settitle(title);
		dto.setstatus(status);
		try {
			return ResponseHandler.generateResponse("Success", HttpStatus.OK, taskService.update(dto));			
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	@PostMapping("/moveTask")
	public boolean dragDropTask(@RequestParam("status") String status,
			@RequestParam("id") int id,TaskDetails dto) {
		dto.setid(id);
		dto.setstatus(status);
		return taskService.dragDropTask(id,status);
	}
}
