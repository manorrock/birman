/*
 *  Copyright (c) 2002-2020, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.birman.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;

/**
 * The file system provider.
 * 
 * <p>
 *  This provider should only be used for testing purposes. DO NOT use in a 
 *  production environment unless you are OK with passwords at rest being visible.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class FilesystemAuthenticationProvider implements AuthenticationProvider {
    
    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FilesystemAuthenticationProvider.class.getPackageName());

    /**
     * Stores the authentication (properties) filename.
     */
    private final File filename;

    /**
     * Constructor.
     *
     * @param filename the filename.
     */
    public FilesystemAuthenticationProvider(File filename) {
        this.filename = filename;
    }

    /**
     * Authenticate.
     *
     * @param username the username.
     * @param password the password.
     * @return true if authenticated, false otherwise.
     */
    @Override
    public boolean authenticate(String username, String password) {
        boolean result = false;
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(filename));
            if (properties.containsKey(username)) {
                String loadedPassword = properties.getProperty(username);
                if (loadedPassword.equals(password)) {
                    result = true;
                }
            }
        } catch (IOException ioe) {
            if (LOGGER.isLoggable(WARNING)) {
                LOGGER.log(WARNING, "Unable to authenticate, because of an I/O error:", ioe);
            }
        }
        return result;
    }
}
