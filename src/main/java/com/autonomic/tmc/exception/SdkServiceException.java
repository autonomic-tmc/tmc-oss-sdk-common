/*-
 * ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
 * TMC OSS SDK Common Classes
 * ——————————————————————————————————————————————————————————————————————————————
 * Copyright (C) 2016 - 2022 Autonomic, LLC
 * ——————————————————————————————————————————————————————————————————————————————
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 * ______________________________________________________________________________
 */
package com.autonomic.tmc.exception;

import static com.autonomic.tmc.exception.ErrorSourceType.SERVICE;

public class SdkServiceException extends BaseSdkException {

    public SdkServiceException(String clientMessage, String sdkName, String sdkVersion) {
        super(buildMessage(SERVICE, clientMessage, sdkName, sdkVersion));
    }

    public SdkServiceException(String clientMessage, Throwable cause, String sdkName, String sdkVersion) {
        super(buildMessage(SERVICE, clientMessage, sdkName, sdkVersion), cause);
    }

    public SdkServiceException(Throwable cause, String sdkName, String sdkVersion) {
        super(buildMessage(SERVICE, "", sdkName, sdkVersion), cause);
    }
}
