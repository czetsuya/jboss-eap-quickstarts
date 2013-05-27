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

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Role;
import org.picketlink.idm.model.SimpleRole;
import org.picketlink.idm.model.SimpleUser;
import org.picketlink.idm.model.User;

/**
 * This startup bean creates the default users, groups and roles when the application is started.
 * 
 * @author Shane Bryzak
 */
@Singleton
@Startup
public class IDMInitializer {

    @Inject
    private IdentityManager identityManager;

    @PostConstruct
    public void create() {

        // Create user kate
        User kate = new SimpleUser("kate");
        kate.setEmail("kate@acme.com");
        kate.setFirstName("Kate");
        kate.setLastName("Smith");
        identityManager.add(kate);
        identityManager.updateCredential(kate, new Password("demo"));

        // Create user phil
        User phil = new SimpleUser("phil");
        phil.setEmail("phil@acme.com");
        phil.setFirstName("Philip");
        phil.setLastName("Jones");
        identityManager.add(phil);
        identityManager.updateCredential(phil, new Password("demo"));
    }
}
