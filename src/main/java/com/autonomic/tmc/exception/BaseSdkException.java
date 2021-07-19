/*-
 * ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
 * TMC OSS SDK Common Classes
 * ——————————————————————————————————————————————————————————————————————————————
 * Copyright (C) 2016 - 2021 Autonomic, LLC
 * ——————————————————————————————————————————————————————————————————————————————
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License
 * ______________________________________________________________________________
 */
package com.autonomic.tmc.exception;

import static java.util.Optional.ofNullable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseSdkException extends RuntimeException {

    static final String DEFAULT_NAME = "[ AUTONOMIC ]";
    static final String DEFAULT_VERSION = "[ SDK ]";

    BaseSdkException(String message) {
        super(message);
    }

    BaseSdkException(String message, Throwable cause) {
        super(message, cause);
    }

    static <T> String buildMessage(ErrorSourceType errorSourceType, String clientMessage, Class<T> clazz) {
        try {
            final ProjectProperties properties = ProjectProperties.get(clazz);
            final String name = ofNullable(properties.getName()).orElse(DEFAULT_NAME);
            final String version = ofNullable(properties.getVersion()).orElse(DEFAULT_VERSION);
            return String.format("%s-%s-%s: %s.", name, version, errorSourceType, clientMessage);
        } catch (Throwable e) {
            final String defaultValue = DEFAULT_NAME + "~" + DEFAULT_VERSION + "~"
                + errorSourceType.toString() + clientMessage;
            log.trace("Ignoring exception, returning " + defaultValue, e);
            return defaultValue;
        }
    }

}
