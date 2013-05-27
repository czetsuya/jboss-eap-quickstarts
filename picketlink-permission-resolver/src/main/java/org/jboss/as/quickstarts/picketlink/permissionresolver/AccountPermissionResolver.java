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

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.as.quickstarts.picketlink.permissionresolver.model.Account;
import org.picketlink.Identity;
import org.picketlink.permission.PermissionResolver;

/**
 * This custom permission resolver implementation grants the currently logged in user
 * the "edit" permission for Account objects which they are the manager for.
 *
 * @author Shane Bryzak
 */
public class AccountPermissionResolver implements PermissionResolver {

    @Inject Identity identity;
    @Inject EntityManager entityManager;

    @Override
    public PermissionStatus hasPermission(Object resource, String permission) {
        if (resource instanceof Account) {
            Account account = (Account) resource;
            if (Account.PERMISSION_EDIT.equals(permission) &&
                identity != null && 
                identity.getAgent().getLoginName().equals(account.getManager())) {
                return PermissionStatus.ALLOW;
            } else {
                return PermissionStatus.DENY;
            }
        } else {
            return PermissionStatus.NOT_APPLICABLE;
        }
    }

    @Override
    public PermissionStatus hasPermission(Class<?> resource, Serializable identifier, String permission) {
        Account account = entityManager.getReference(Account.class, identifier);
        return hasPermission(account, permission);
    }

}
