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

import static com.autonomic.tmc.exception.ErrorSourceType.SERVICE;

public class SdkServiceException extends BaseSdkException {

    public <T> SdkServiceException(String clientMessage, Throwable cause, Class<T> origin) {
        super(buildMessage(SERVICE, clientMessage, origin), cause);
    }

    public <T> SdkServiceException(Throwable cause, Class<T> origin) {
        super(buildMessage(SERVICE, "", origin), cause);
    }

    public <T> SdkServiceException(String clientMessage, Class<T> origin) {
        super(buildMessage(SERVICE, clientMessage, origin));
    }
}