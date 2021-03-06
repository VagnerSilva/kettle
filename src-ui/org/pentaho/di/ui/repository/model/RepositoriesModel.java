/*
 * Copyright (c) 2010 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * Data Integration.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 */
package org.pentaho.di.ui.repository.model;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.ui.xul.XulEventSourceAdapter;

public class RepositoriesModel  extends XulEventSourceAdapter{

  private String username;
  private String password;
  private boolean showDialogAtStartup;
  private List<RepositoryMeta> availableRepositories;
  private RepositoryMeta selectedRepository;
  
  public RepositoriesModel() {
    super();
    availableRepositories = new ArrayList<RepositoryMeta>();
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    String previousValue = this.username;
    this.username = username;
    this.firePropertyChange("username", previousValue, username); //$NON-NLS-1$
    checkIfModelValid();
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    String previousValue = this.password;
    this.password = password;
    this.firePropertyChange("password", previousValue, password); //$NON-NLS-1$
  }
  public boolean isShowDialogAtStartup() {
    return showDialogAtStartup;
  }
  public void setShowDialogAtStartup(boolean showDialogAtStartup) {
    boolean previousValue = this.showDialogAtStartup;
    this.showDialogAtStartup = showDialogAtStartup;
    this.firePropertyChange("showDialogAtStartup", previousValue, showDialogAtStartup); //$NON-NLS-1$    
  }
  public List<RepositoryMeta> getAvailableRepositories() {
    return availableRepositories;
  }
  public void setAvailableRepositories(List<RepositoryMeta> repositoryList) {
    List<RepositoryMeta> previousValue = new ArrayList<RepositoryMeta>();
    previousValue.addAll(this.availableRepositories);
    this.availableRepositories = repositoryList;
    this.firePropertyChange("availableRepositories", previousValue, repositoryList); //$NON-NLS-1$
  }
  public void addToAvailableRepositories(RepositoryMeta meta) {
    List<RepositoryMeta> previousValue = new ArrayList<RepositoryMeta>();
    previousValue.addAll(this.availableRepositories);
    this.availableRepositories.add(meta);
    this.firePropertyChange("availableRepositories", previousValue, this.availableRepositories); //$NON-NLS-1$    
  }
  public void removeFromAvailableRepositories(RepositoryMeta meta) {
    List<RepositoryMeta> previousValue = new ArrayList<RepositoryMeta>();
    previousValue.addAll(this.availableRepositories);
    this.availableRepositories.remove(meta);
    this.firePropertyChange("availableRepositories", previousValue, this.availableRepositories); //$NON-NLS-1$
  }  
  public void clear() {
    setUsername(null);
    setPassword(null);
    setShowDialogAtStartup(true);
    setAvailableRepositories(null);
  }

  public void setSelectedRepositoryUsingName(String repositoryName) {
    setSelectedRepository(getRepository(repositoryName));
  }
  public void setSelectedRepository(RepositoryMeta selectedRepository) {
    RepositoryMeta previousValue = this.selectedRepository;
    this.selectedRepository = selectedRepository;
    this.firePropertyChange("selectedRepository", previousValue, selectedRepository); //$NON-NLS-1$
    checkIfModelValid();
  }

  public RepositoryMeta getSelectedRepository() {
    return selectedRepository;
  }
  public RepositoryMeta getRepository(String repositoryName) {
    if(availableRepositories != null && availableRepositories.size() > 0) { 
      for(RepositoryMeta meta:availableRepositories) {
        if(meta != null && meta.getName().equals(repositoryName)) {
          return meta;
        }
      }
    }
    return null;
  }
  public int getRepositoryIndex(RepositoryMeta repositoryMeta) {
    int index = 0;
    if(availableRepositories != null && availableRepositories.size() > 0) {
      for(RepositoryMeta meta:availableRepositories) {
        if(meta != null && meta.getName().equals(repositoryMeta.getName())) {
          break;
        } else {
          index++;
        }
      }
    } else {
      index = -1;
    }
    return index;
  }
  
  public RepositoryMeta getRepository(int index) {
    return availableRepositories.get(index);
  }
  
  public void checkIfModelValid() {
    this.firePropertyChange("valid", null, isValid());//$NON-NLS-1$
  }
  
  public boolean isValid() {
    return username != null && username.length() > 0 && selectedRepository != null;
  }
}
