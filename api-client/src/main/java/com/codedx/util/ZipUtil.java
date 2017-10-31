/*
 * Copyright (c) 2017. Code Dx, Inc. All Rights Reserved.
 * Author: Code Dx, Inc - Brandon Thorne
 * Project: codedx-intellij-plugin
 * ClassName: util.ZipUtil
 * FileName: ZipUtil.java
 */

package com.codedx.util;;

//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


//Adapted from ZipUtil.java in eclipse plugin
public class ZipUtil {

	/*private static final String[] whiteListValues = new String[]{"java", "jsp", "class", "c", "cpp", "h", "cxx", "rb", "py", "html", "js", "xml",
			"cs", "vb", "m", "php", "php2", "cshtml", "master", "scala"};
	private static final Set<String> whiteList = new HashSet<>(Arrays.asList(whiteListValues));

	private final static Logger logger = Logger.getInstance(ZipUtil.class.getName());

	public static void zipProject(Project project, File destZipFile, boolean includeExternalLibraries, boolean includeExternalModules) throws IOException {

		FileOutputStream fileWriter = new FileOutputStream(destZipFile);
		//Zip file to contain project files
		ZipOutputStream zip = new ZipOutputStream(fileWriter);

		//get files in project directory
		VirtualFile baseVirtualFile = project.getBaseDir();

		// Get the file array for the base directory
		File baseDir = new File(baseVirtualFile.getPath());
		if (baseDir.isDirectory() && baseDir.exists()) {
			addFolderToZip(baseDir, baseDir.getName(), zip, true);
		} else {
			//This shouldn't happen because it should be project directory
			logger.warn("Project location is not a directory. Cannot zip project.");
			throw new IOException();
		}


		//Add modules from external sources to zip
		if (includeExternalModules) {
			Module[] modules = ModuleManager.getInstance(project).getModules();
			for (Module module : modules) {

				try {
					Path modulePath = Paths.get(module.getModuleFilePath()).toAbsolutePath();
					Path projectPath = Paths.get(baseDir.toURI()).toAbsolutePath();

					//check if the module is not under the project directory
					if (!modulePath.startsWith(projectPath)) {
						//If it isn't, include it in the zip file
						File moduleFolder = modulePath.getParent().toFile();
						if (moduleFolder.isDirectory()) {
							addFolderToZip(moduleFolder, moduleFolder.getName(), zip, true);
						}
					}
				} catch (InvalidPathException ipe) {
					logger.warn(ipe);
					DisplayDialog.displayDialog("Could not add module " + module.getName() + " to the analysis. Analysis will not include this module.");
				}


			}
		}


		// Add external libraries to zip
		if (includeExternalLibraries) {
			Module[] modules = ModuleManager.getInstance(project).getModules();
			for (Module module : modules) {

				ModuleRootManager.getInstance(module).orderEntries().forEachLibrary(library -> {


					try {
						for (VirtualFile each : library.getFiles(OrderRootType.SOURCES)) {

							//Skip null libraries because they are java SDK
							if (each.getName().equals("") || each.getName().equals("null")) {
								continue;
							}

							File libraryFile = new File(each.getPath().replace("!/", ""));

							String path = baseDir.getName() + "/" + each.getName();
							if (libraryFile.isDirectory()) {
								addFolderToZip(libraryFile, path, zip, false);
							} else {
								addFileToZip(libraryFile, path, zip);
							}
						}

						for (VirtualFile each : library.getFiles(OrderRootType.CLASSES)) {
							File libraryFile = new File(each.getPath().replace("!/", ""));
							String path = baseDir.getName() + "/" + each.getName();

							if (libraryFile.isDirectory()) {
								addFolderToZip(libraryFile, path, zip, false);
							} else {
								addFileToZip(libraryFile, path, zip);
							}
						}
					} catch (IOException e) {
						logger.warn(e);
					}

					return true;
				});
			}
		}

		zip.close();
	}

	private static void addFileToZip(File file, String path, ZipOutputStream zip) throws IOException {
		zip.putNextEntry(new ZipEntry(path));
		FileUtils.copyFile(file, zip);
	}


	*//**
	 * Adds zip entries for the children (and recursively their children) of the
	 * given folder.
	 *
	 * @param folder   The folder whose children will be added
	 * @param basePath The base path used for constructing entry paths for the
	 *                 children, i.e. the zip entry for a child file will be
	 *                 <code>basePath/filename</code>
	 * @param zip      The ZipOutputStream that the files will be copied to
	 * @throws IOException
	 *//*
	private static void addFolderToZip(File folder, String basePath, ZipOutputStream zip, boolean obeyWhitelist) throws IOException {

		for (File file : folder.listFiles()) {
			String filename = file.getName();
			String entryPath = basePath + "/" + filename;
			if (file.isDirectory()) {
				addFolderToZip(file, entryPath, zip, obeyWhitelist);
			} else {
				String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();
				if (!obeyWhitelist || whiteList.contains(extension)) {
					// include it in the analysis
					addFileToZip(file, entryPath, zip);
				}
			}
		}
	}*/

}
