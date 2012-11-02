/*
 * Copyright 2012 the original author or authors.
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
package org.gradle.integtests.fixtures

import org.gradle.api.Action
import org.gradle.util.TemporaryFolder

class RedirectMavenCentral implements Action<GradleExecuter> {
    private final TemporaryFolder temporaryFolder

    RedirectMavenCentral(TemporaryFolder temporaryFolder) {
        this.temporaryFolder = temporaryFolder
    }

    void execute(GradleExecuter executer) {
        def file = temporaryFolder.createFile("redirect-maven-central-init.gradle")
        file.text = """
allprojects {
    repositories.withType(org.gradle.api.artifacts.repositories.MavenArtifactRepository) {
        if (url == new URI('http://repo1.maven.org/maven2/')) {
            url = "http://repo.gradle.org/gradle/repo1"
        }
    }
}
"""
        executer.withArgument("-I$file.absolutePath")
    }
}
