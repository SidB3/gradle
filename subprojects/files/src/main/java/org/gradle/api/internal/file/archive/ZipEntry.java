/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.file.archive;

import org.gradle.internal.io.IoFunction;

import java.io.IOException;
import java.io.InputStream;

public interface ZipEntry {

    boolean isDirectory();

    String getName();

    /**
     * This method or {@link #withInputStream(IoFunction)} ()} may or may not support being called more than
     * once per entry.  Use {@link #canReopen()} to determine if more than one call is supported.
     */
    byte[] getContent() throws IOException;

    /**
     * Declare an action to be run against this ZipEntry's content as a {@link InputStream}.
     * The {@link InputStream} passed to the {@link InputStreamAction#run(InputStream)} will
     * be closed right after the action's return.
     *
     * This method or {@link #getContent()} may or may not support being called more than once per entry.
     * Use {@link #canReopen()} to determine if more than one call is supported.
     */
    <T> T withInputStream(IoFunction<InputStream, T> action) throws IOException;

    /**
     * The size of the content in bytes, or -1 if not known.
     */
    int size();

    /**
     * Whether or not the zip entry can safely be read again if any bytes
     * have already been read from it.
     */
    boolean canReopen();
}
