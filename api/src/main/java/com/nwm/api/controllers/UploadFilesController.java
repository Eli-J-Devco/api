package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nwm.api.messages.ResponseMessage;
import com.nwm.api.services.FilesStorageService;
import com.nwm.api.services.A8810Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.nio.file.Path;

import com.nwm.api.entities.A8810Entity;

@RestController
@ApiIgnore
@RequestMapping("/files")
public class UploadFilesController extends BaseController {
	/**
	 * @description upload files datalogger and insert datalogger to database
	 * @author long.pham
	 * @since 2020-08-19
	 * @params RequestParam, files
	 */
	@Autowired
	FilesStorageService storageService;

	@PostMapping("/upload")
	@ResponseBody
	//
//	@RequestParam("LOGFILE") MultipartFile[] files,
//	@RequestParam("SENDDATATRACE") String senddatatrace, @RequestParam("MODE") String mode,
//	@RequestParam("SERIALNUMBER") String serialnumber, @RequestParam("PASSWORD") String password,
//	@RequestParam("LOOPNAME") String koopname, @RequestParam("MODBUSIP") String modbusip,
//	@RequestParam("MODBUSPORT") String modbusport, @RequestParam("MODBUSDEVICE") String modbusdevice,
//	@RequestParam("MODBUSDEVICENAME") String modbusdevicename,
//	@RequestParam("MODBUSDEVICETYPE") String modbusdevicetype,
//	@RequestParam("MODBUSDEVICETYPENUMBER") String modbusdevicetypenumber,
//	@RequestParam("MODBUSDEVICECLASS") String modbusdeviceclass,
//	@RequestParam("MD5CHECKSUM") String md5checksum, @RequestParam("FILESIZE") String filesize,
//	@RequestParam("FILETIME") String filetime
//	
	public ResponseEntity<ResponseMessage> uploadFiles(
			@RequestParam(name = "LOGFILE", required = false) MultipartFile files[],
			@RequestParam(name = "SENDDATATRACE", required = false) String senddatatrace,
			@RequestParam(name = "MODE", required = false) String mode,
			@RequestParam(name = "SERIALNUMBER", required = false) String serialnumber,
			@RequestParam(name = "PASSWORD", required = false) String password,
			@RequestParam(name = "LOOPNAME", required = false) String koopname,
			@RequestParam(name = "MODBUSIP", required = false) String modbusip,
			@RequestParam(name = "MODBUSPORT", required = false) String modbusport,
			@RequestParam(name = "MODBUSDEVICE", required = false) String modbusdevice,
			@RequestParam(name = "MODBUSDEVICENAME", required = false) String modbusdevicename,
			@RequestParam(name = "MODBUSDEVICETYPE", required = false) String modbusdevicetype,
			@RequestParam(name = "MODBUSDEVICETYPENUMBER", required = false) String modbusdevicetypenumber,
			@RequestParam(name = "MODBUSDEVICECLASS", required = false) String modbusdeviceclass,
			@RequestParam(name = "MD5CHECKSUM", required = false) String md5checksum,
			@RequestParam(name = "FILESIZE", required = false) String filesize,
			@RequestParam(name = "FILETIME", required = false) String filetime) {

		String message = "";
		try {

			String LOGFILEUPLOAD = "LOGFILEUPLOAD";
			List<String> fileNames = new ArrayList<>();
			System.out.println("File length: "+ files.length);
			if (mode.equals(LOGFILEUPLOAD) && files.length > 0) {
				Arrays.asList(files).stream().forEach(file -> {
					String fileName = file.getOriginalFilename();
					System.out.println("File name: "+fileName);
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
					fileNames.add(file.getOriginalFilename());
					
					Path root = Paths.get(
							Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					byte[] bytes;
					try {
						bytes = file.getBytes();
						switch (ext) {
						case "gz":

							Path path = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
									Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice + "." + timeStamp
									+ ".log.gz");
							Files.write(path, bytes);

							FileInputStream fis = new FileInputStream(path.toString());
							GZIPInputStream gis = new GZIPInputStream(fis);

							fileName = "bm-" + modbusdevice + "." + timeStamp + ".log";
							FileOutputStream fos = new FileOutputStream(root.resolve(fileName).toString());
							byte[] buffer = new byte[1024];
							int len;
							while ((len = gis.read(buffer)) != -1) {
								fos.write(buffer, 0, len);
							}
							// close resources
							fos.close();
							gis.close();
							break;
						case "log":
							// code block
							Path pathLogUplad = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName,
									Constants.uploadRootPathConfigKey) + "/" + "bm-" + modbusdevice + "." + timeStamp
									+ ".log");
							Files.write(pathLogUplad, bytes);
							fileName = "bm-" + modbusdevice + "." + timeStamp + ".log";
							break;
						default:
							// code block
						}

						boolean exists = new File(root.resolve(fileName).toString()).isFile();
						if (exists) {
							File readFile = new File(root.resolve(fileName).toString());
							FileReader fr = new FileReader(readFile); // reads the file
							BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
							StringBuffer sb = new StringBuffer(); // constructs a string buffer with no characters
							String line;
							A8810Service service = new A8810Service();
							boolean statusInsert = false;
							// Check insert database status
							while ((line = br.readLine()) != null) {
								sb.append(line); // appends line to string buffer
								sb.append("\n"); // line feed
								// Convert string to array
								String[] words = line.split(",");
								if (words.length > 0) {
									A8810Entity dataE = new A8810Entity();
									dataE.setTime(words[0].replace("'", ""));
									dataE.setError(Integer.parseInt(words[1]));
									dataE.setLow_alarm(Integer.parseInt(words[2]));
									dataE.setHigh_alarm(Integer.parseInt(words[3]));
									dataE.setIon6200_kwh(Float.parseFloat(words[4]));
									dataE.setIon6200_demand_kw(Float.parseFloat(words[5]));
									boolean dataInsert = service.insertA8810(dataE);
									if (!dataInsert) {
										statusInsert = true;
									}
								}
							}
							fr.close(); // closes the stream and release the resources
							// Delete file when upload file and insert to database complete
							if (!statusInsert) {
//								File logFile = new File(root.resolve(fileName).toString());
//								logFile.delete();
//								
//								Path path = Paths
//										.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey)
//												+ "/" + "bm-" + modbusdevice + "." + timeStamp + ".log.gz");
//								File logGzFile = new File(path.toString());
//								logGzFile.delete();				
							}
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});
				message = "Uploaded the files successfully: " + fileNames;
			} else {
				message = "Mode type " + mode + " not supported by this sample script.";
			}
//			
//			
//			
//			Arrays.asList(files).stream().forEach(file -> {
//		        storageService.save(file);
//		        fileNames.add(file.getOriginalFilename());
//				if (mode.equals(LOGFILEUPLOAD) && !file.isEmpty()) {
//					String fileName = file.getOriginalFilename();
//					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
//					Path root = Paths
//							.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
//					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
//					byte[] bytes = file.getBytes();

//				} else {
//					message = "Mode type " + mode + " not supported by this sample script.";
//				}
//			});

//			if (mode.equals(LOGFILEUPLOAD) && !files.isEmpty()) {
//				String fileName = files.getOriginalFilename();
//
//				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
//				Path root = Paths
//						.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey));
//				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
//				byte[] bytes = files.getBytes();
//				switch (ext) {
//				case "gz":
//
//					Path path = Paths
//							.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey)
//									+ "/" + "bm-" + modbusdevice + "." + timeStamp + ".log.gz");
//					Files.write(path, bytes);
//
//					FileInputStream fis = new FileInputStream(path.toString());
//					GZIPInputStream gis = new GZIPInputStream(fis);
//
//					fileName = "bm-" + modbusdevice + "." + timeStamp + ".log";
//					FileOutputStream fos = new FileOutputStream(root.resolve(fileName).toString());
//					byte[] buffer = new byte[1024];
//					int len;
//					while ((len = gis.read(buffer)) != -1) {
//						fos.write(buffer, 0, len);
//					}
//					// close resources
//					fos.close();
//					gis.close();
//					break;
//				case "log":
//					// code block
//					Path pathLogUplad = Paths
//							.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey)
//									+ "/" + "bm-" + modbusdevice + "." + timeStamp + ".log");
//					Files.write(pathLogUplad, bytes);
//					fileName = "bm-" + modbusdevice + "." + timeStamp + ".log";
//					break;
//				default:
//					// code block
//				}
//
//				boolean exists = new File(root.resolve(fileName).toString()).isFile();
//				System.out.println(exists);
//				System.out.println(fileName);
//				System.out.println(root.resolve(fileName));
//				if (exists) {
//					try {
//						File readFile = new File(root.resolve(fileName).toString());
//						FileReader fr = new FileReader(readFile); // reads the file
//						BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
//						StringBuffer sb = new StringBuffer(); // constructs a string buffer with no characters
//						String line;
//						A8810Service service = new A8810Service();
//						// Check insert database status
//						boolean statusInsert = false;
//						while ((line = br.readLine()) != null) {
//							sb.append(line); // appends line to string buffer
//							sb.append("\n"); // line feed
//							// Convert string to array
//							String[] words = line.split(",");
//							if (words.length > 0) {
//								A8810Entity dataE = new A8810Entity();
//								dataE.setTime(words[0].replace("'", ""));
//								dataE.setError(Integer.parseInt(words[1]));
//								dataE.setLow_alarm(Integer.parseInt(words[2]));
//								dataE.setHigh_alarm(Integer.parseInt(words[3]));
//								dataE.setIon6200_kwh(Float.parseFloat(words[4]));
//								dataE.setIon6200_demand_kw(Float.parseFloat(words[5]));
//								boolean dataInsert = service.insertA8810(dataE);
//								if (!dataInsert) {
//									statusInsert = true;
//								}
//							}
//						}
//						fr.close(); // closes the stream and release the resources
//						// Delete file when upload file and insert to database complete
//						if (!statusInsert) {
////							File logFile = new File(root.resolve(fileName).toString());
////							logFile.delete();
////							
////							Path path = Paths
////									.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey)
////											+ "/" + "bm-" + modbusdevice + "." + timeStamp + ".log.gz");
////							File logGzFile = new File(path.toString());
////							logGzFile.delete();
//
//						}
//
//					} catch (Exception e) {
//					}
//				}
//
//				message = "Uploaded the files successfully";
//			} else {
//				message = "Mode type " + mode + " not supported by this sample script.";
//			}

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

		} catch (Exception e) {
			message = "Fail to upload files!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
}
