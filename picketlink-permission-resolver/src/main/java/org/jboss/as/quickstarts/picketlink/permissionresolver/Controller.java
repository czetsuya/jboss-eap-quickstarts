/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.picketlink.permissionresolver;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.as.quickstarts.picketlink.permissionresolver.model.Account;
import org.picketlink.Identity;


/**
 * The controller defines the business logic for this example
 * 
 * @author Shane Bryzak
 * 
 */
// Expose the bean to EL
@Named
@RequestScoped
public class Controller {

    @Inject
    private FacesContext facesContext;

    @Inject EntityManager entityManager;

    @Inject Identity identity;

    public List<Account> getAccounts() {
        return entityManager.createQuery("select a from Account a", Account.class).getResultList();
    }

    public void editAccount(Account value) {
        if (identity.hasPermission(value, Account.PERMISSION_EDIT)) {
            facesContext.addMessage(null, new FacesMessage("You have permission to edit account #" +
                    value.getNumber()));
        } else {
            facesContext.addMessage(null, new FacesMessage("You do NOT have permission to edit account #" +
                    value.getNumber()));
        }
    }

    public String getStackTrace() {
        Throwable throwable = (Throwable)  FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("javax.servlet.error.exception");
        StringBuilder builder = new StringBuilder();
        builder.append(throwable.getMessage()).append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            builder.append(element).append("\n");
        }
        return builder.toString();
    }
}
