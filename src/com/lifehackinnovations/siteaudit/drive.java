/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
//test
package com.lifehackinnovations.siteaudit;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Changes;
import com.google.api.services.drive.Drive.Children;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.Change;
import com.google.api.services.drive.model.ChangeList;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentList;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.ParentReference;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class drive{

//Files
	
	 public static void printFile(Drive service, String fileId) {

		    try {
		      File file = service.files().get(fileId).execute();

		      System.out.println("Title: " + file.getTitle());
		      System.out.println("Description: " + file.getDescription());
		      System.out.println("MIME type: " + file.getMimeType());
		    } catch (IOException e) {
		      System.out.println("An error occured: " + e);
		    }
		  }

		  /**
		   * Download a file's content.
		   *
		   * @param service Drive API service instance.
		   * @param file Drive File instance.
		   * @return InputStream containing the file's content if successful,
		   *         {@code null} otherwise.
		   */
		  public static InputStream downloadFile(Drive service, File file) {
		    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
		      try {
		        HttpResponse resp =
		            service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
		                .execute();
		        return resp.getContent();
		      } catch (IOException e) {
		        // An error occurred.
		        e.printStackTrace();
		        return null;
		      }
		    } else {
		      // The file doesn't have any content stored on Drive.
		      return null;
		    }
		  }
	
		  public static File insertFile(Drive service, String title, String description,
			      String parentId, String mimeType, String filename) {
			    // File's metadata.
			    File body = new File();
			    body.setTitle(title);
			    body.setDescription(description);
			    body.setMimeType(mimeType);
			    
			    
			   
	               // Set the parent folder.
			    if (parentId != null && parentId.length() > 0) {
			    	ParentReference newParent = new ParentReference();
		               newParent.setId(parentId);
		               body.setParents(Arrays.asList(newParent));
			    }

			    // File's content.
			    java.io.File fileContent = new java.io.File(filename);
			    FileContent mediaContent = new FileContent(mimeType, fileContent);
			    try {
			      File file = service.files().insert(body, mediaContent).execute();

			      // Uncomment the following line to print the File ID.
			       System.out.println("File ID: %s" + file.getId());

			      return file;
			    } catch (IOException e) {
			      System.out.println("An error occured: " + e);
			      return null;
			    }
			  }
	
		  public static File renameFile(Drive service, String fileId, String newTitle) {
			    try {
			      File file = new File();
			      file.setTitle(newTitle);

			      // Rename the file.
			      Files.Patch patchRequest = service.files().patch(fileId, file);
			      patchRequest.setFields("title");

			      File updatedFile = patchRequest.execute();
			      return updatedFile;
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			      return null;
			    }
			  }
	
		  public static File copyFile(Drive service, String originFileId,
			      String copyTitle) {
			    File copiedFile = new File();
			    copiedFile.setTitle(copyTitle);
			    try {
			      return service.files().copy(originFileId, copiedFile).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			    return null;
			  }
	
		  public static void deleteFile(Drive service, String fileId) {
			    try {
			      service.files().delete(fileId).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			  }
	
		  public static List<File> retrieveAllFiles(Drive service) throws IOException {
			    List<File> result = new ArrayList<File>();
			    Files.List request = service.files().list();

			    do {
			      try {
			        FileList files = request.execute();

			        result.addAll(files.getItems());
			        request.setPageToken(files.getNextPageToken());
			      } catch (IOException e) {
			        System.out.println("An error occurred: " + e);
			        request.setPageToken(null);
			      }
			    } while (request.getPageToken() != null &&
			             request.getPageToken().length() > 0);

			    return result;
			  }
	
		  public static File updateModifiedDate(Drive service, String fileId) {
			    try {
			      return service.files().touch(fileId).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			    return null;
			  }
		  public static File trashFile(Drive service, String fileId) {
			    try {
			      return service.files().trash(fileId).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			    return null;
			  }

		  public static File restoreFile(Drive service, String fileId) {
			    try {
			      return service.files().untrash(fileId).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			    return null;
			  }

		  
//About
		  
		  public static void printAbout(Drive service) {
			    try {
			      About about = service.about().get().execute();

			      System.out.println("Current user name: " + about.getName());
			      System.out.println("Root folder ID: " + about.getRootFolderId());
			      System.out.println("Total quota (bytes): " + about.getQuotaBytesTotal());
			      System.out.println("Used quota (bytes): " + about.getQuotaBytesUsed());
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			  }

//Changes		  
		  
		  public static void printChange(Drive service, String changeId) {
			    try {
			      Change change = service.changes().get(changeId).execute();

			      System.out.println("Changed file ID: " + change.getFileId());
			      if (change.getDeleted()) {
			        System.out.println("File has been deleted");
			      } else {
			        File changedFile = change.getFile();
			        System.out.println("Changed file Title: " + changedFile.getTitle());
			      }
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			  }

		  
		  public static List<Change> retrieveAllChanges(Drive service,
			      String startChangeId) throws IOException {
			    List<Change> result = new ArrayList<Change>();
			    Changes.List request = service.changes().list();

			    if (startChangeId != null && startChangeId.length() > 0) {
//			      request.setStartChangeId(startChangeId);
			    }
			    do {
			      try {
			        ChangeList changes = request.execute();

			        result.addAll(changes.getItems());
			        request.setPageToken(changes.getNextPageToken());
			      } catch (IOException e) {
			        System.out.println("An error occurred: " + e);
			        request.setPageToken(null);
			      }
			    } while (request.getPageToken() != null &&
			             request.getPageToken().length() > 0);

			    return result;
			  }
		  
//Children
		  
		  public static void removeFileFromFolder(Drive service, String folderId,
			      String fileId) {
			    try {
			      service.children().delete(folderId, fileId).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			  }
		  
		  public static boolean isFileInFolder(Drive service, String folderId,
			      String fileId) throws IOException {
			    try {
			      service.children().get(folderId, fileId).execute();
			    } catch (HttpResponseException e) {
			      if (e.getStatusCode() == 404) {
			        return false;
			      } else {
			        System.out.println("An error occurred: " + e);
			        throw e;
			      }
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			      throw e;
			    }
			    return true;
			  }
		  
		  public static ChildReference insertFileIntoFolder(Drive service, String folderId,
			      String fileId) {
			    ChildReference newChild = new ChildReference();
			    newChild.setId(fileId);
			    try {
			      return service.children().insert(folderId, newChild).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			    return null;
			  }
		  
		  public static void printFilesInFolder(Drive service, String folderId) throws IOException {
			    Children.List request = service.children().list(folderId);

			    do {
			      try {
			        ChildList children = request.execute();

			        for (ChildReference child : children.getItems()) {
			          System.out.println("File Id: " + child.getId());
			        }
			        request.setPageToken(children.getNextPageToken());
			      } catch (IOException e) {
			        System.out.println("An error occurred: " + e);
			        request.setPageToken(null);
			      }
			    } while (request.getPageToken() != null &&
			             request.getPageToken().length() > 0);
			  }
		  
//Parents
		  
		  public static void removeFileFromFolderParent(Drive service, String folderId,
			      String fileId) {
			    try {
			      service.parents().delete(fileId, folderId).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			  }
			  
		  
		  
		  public static boolean isFileInFolderParent(Drive service, String folderId,
			      String fileId) throws IOException {
			    try {
			      service.parents().get(fileId, folderId).execute();
			    } catch (HttpResponseException e) {
			      if (e.getStatusCode() == 404) {
			        return false;
			      } else {
			        System.out.println("An error occured: " + e);
			        throw e;
			      }
			    } catch (IOException e) {
			      System.out.println("An error occured: " + e);
			      throw e;
			    }
			    return true;
			  }
			  
		  
		  public static ParentReference insertFileIntoFolderParent(Drive service, String folderId,
			      String fileId) {
			    ParentReference newParent = new ParentReference();
			    newParent.setId(folderId);
			    try {
			      return service.parents().insert(fileId, newParent).execute();
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			    return null;
			  }
	
		  public static void printParents(Drive service, String fileId) {
			    try {
			      ParentList parents = service.parents().list(fileId).execute();

			      for (ParentReference parent : parents.getItems()) {
			        System.out.println("File Id: " + parent.getId());
			      }
			    } catch (IOException e) {
			      System.out.println("An error occurred: " + e);
			    }
			  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
//Custom		
		  public static String getdrivefolderIdbyname(Drive service,String name) throws IOException{
			  String id = null;
			  if (!(name.length()==0||name==null)){ 
					
				 
				 Files.List request = null;
			     
				 request = service.files().list();
			     FileList files = request.execute();
			     files.getItems();
			     List<com.google.api.services.drive.model.File> filesfromdrive=files.getItems();
				 for(int num=0; num<filesfromdrive.size(); num++){
					 Log.d("This is the list of files being checked for "+name,filesfromdrive.get(num).getTitle().toString());
					 if (filesfromdrive.get(num).getMimeType().equals("application/vnd.google-apps.folder")&&filesfromdrive.get(num).getTitle().equals(name)&&(!filesfromdrive.get(num).getLabels().getTrashed())){
						 id=filesfromdrive.get(num).getId();
						 break;
					 }
			     
				 }
				 if (id==null){
					 Log.d("folder,"+name+ " does not exist","  ");
					return null;
				 }
				}
			     return id;
			 }
		 /*
		  public static String getdrivefileIdbyname(Drive service,String name) throws IOException{
				 String id = null;
				 
				 Files.List request = null;
			     
				 request = service.files().list();
			     FileList files = request.execute();
			     files.getItems();
			     List<com.google.api.services.drive.model.File> filesfromdrive=files.getItems();
			     for(int num=0; num<filesfromdrive.size(); num++){
			    	 Log.d("file"+u.s(num),filesfromdrive.get(num).getTitle());
			     }
			     for(int num=0; num<filesfromdrive.size(); num++){
					 if (filesfromdrive.get(num).getTitle().equals(name)&&(!filesfromdrive.get(num).getLabels().getTrashed())){
						 id=filesfromdrive.get(num).getId();
						 
						 break;
					 }
			     
				 }
				 if (id==null){
					 Log.d("file,"+name+ " does not exist","  ");
					return null;
				 }
			     return id;
			 }
			 */
			 public static com.google.api.services.drive.model.File createfolderondrive(Drive service, String foldername, String parentfolder) throws IOException{
				 com.google.api.services.drive.model.File file=null;
					 if (getdrivefolderIdbyname(service,foldername)==null){
						 Log.d("getdrivefolderIdbyname returned null","null");
						 com.google.api.services.drive.model.File body = new com.google.api.services.drive.model.File();
					      	
						 body.setTitle(foldername);
						 body.setMimeType("application/vnd.google-apps.folder");
						 
						 if(!(parentfolder==null)){
							 ParentReference newParent = new ParentReference();
		             
							 if (!(drive.getdrivefolderIdbyname(service,parentfolder)==null)){
								 newParent.setId(drive.getdrivefolderIdbyname(service,parentfolder));
							 }else{
								 createfolderondrive(service, parentfolder, null);
							 }
							 body.setParents(Arrays.asList(newParent));
						 }
						 file = service.files().insert(body).execute();
						 // File file = service.files().
						
						 if (file != null) {
							 Log.d("Folder Created: ", file.getTitle());
							
						 }
					 }else{
					 
				 	 
					 }
					return file;
			 }
			 public static String createfolderondrivebyid(Drive service, String parentfolderid, String foldername) throws IOException{
				Log.d("parentfolderid",parentfolderid);
				Log.d("foldername",foldername);
				 com.google.api.services.drive.model.File file=null;
					 if (!(parentfolderid==null)){
						 com.google.api.services.drive.model.File body = new com.google.api.services.drive.model.File();
					      	
						 body.setTitle(foldername);
						 body.setMimeType("application/vnd.google-apps.folder");
						 
						 if(!(parentfolderid==null)){
							 ParentReference newParent = new ParentReference();
							 newParent.setId(parentfolderid);
							 body.setParents(Arrays.asList(newParent));
							 Log.d("Parent set","true");
						 }
						 file = service.files().insert(body).execute();
						 // File file = service.files().
						
						 if (file != null) {
							 Log.d("Folder Created: ", file.getTitle());
							
						 }
					 }else{
					 
				 	 
					 }
					 String id=file.getId();
					return id;
			 }
			 
			 public static List<String> listfilesandlocation(Drive service, List<com.google.api.services.drive.model.File> files) throws IOException{
				 List<String> list=new ArrayList<String>();
				 About about=service.about().get().execute();
				 	
		        	for(int num=0; num<files.size(); num++){
		        		if (files.get(num).getParents().get(0).getId().equals(about.getRootFolderId())){
		        			if (files.get(num).getMimeType().equals("application/vnd.google-apps.folder")){
		        				Log.d(files.get(num).getTitle(), "is a folder in the root directory");
		        				list.add(files.get(num).getTitle());
		        			}else{
		        				Log.d(files.get(num).getTitle(), "is a file in the root directory");
		        				list.add(files.get(num).getTitle());
		        			}
		        		}
		        	}
		        	return list;
			 }
			 public static List<String> listfilesinfolder(Drive service, String foldername,List<com.google.api.services.drive.model.File> files) throws IOException{
				 
				 List<String> list=new ArrayList<String>();
				 
				 About about=service.about().get().execute();
				 
		        	for(int num=0; num<files.size(); num++){
		        		if (files.get(num).getParents().get(0).getId().equals(getdrivefolderIdbyname(service, foldername))){
		        			if (files.get(num).getMimeType().equals("application/vnd.google-apps.folder")){
		        				Log.d(files.get(num).getTitle(), "is a folder in the "+foldername+" directory");
		        				list.add(files.get(num).getTitle());
		        			}else{
		        				Log.d(files.get(num).getTitle(), "is a file in the "+foldername+" directory");
		        				list.add(files.get(num).getTitle());
		        			}
		        		}
		        	}
		        	return list;
			 }
}
