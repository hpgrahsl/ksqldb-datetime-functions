/*
 * Copyright (c) 2020. Hans-Peter Grahsl (grahslhp@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.github.hpgrahsl.ksqldb.functions.util;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;

public class JsonObjectFileArgumentsProvider {

  public static List<JsonObject> parseJsonSampleFile(String filePath) {
    try (var jr = Json.createReader(
        new FileInputStream(Objects.requireNonNull(JsonObjectFileArgumentsProvider.class
            .getClassLoader().getResource(filePath)).getFile()))) {
      return jr.readArray().getValuesAs(JsonObject.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

}
