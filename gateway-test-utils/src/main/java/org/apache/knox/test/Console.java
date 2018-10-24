/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.knox.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Console {

  PrintStream oldOut, newOut;
  PrintStream oldErr, newErr;
  ByteArrayOutputStream newOutBuf, newErrBuf;

  public void capture() throws UnsupportedEncodingException {
    oldErr = System.err;
    newErrBuf = new ByteArrayOutputStream();
    newErr = new PrintStream( newErrBuf, false, StandardCharsets.UTF_8.name() );

    oldOut = System.out; // I18N not required.
    newOutBuf = new ByteArrayOutputStream();
    newOut = new PrintStream( newOutBuf, false, StandardCharsets.UTF_8.name() );

    System.setErr( newErr );
    System.setOut( newOut );
  }

  public byte[] getOut() {
    return newOutBuf.toByteArray();
  }

  public byte[] getErr() {
    return newErrBuf.toByteArray();
  }

  public void release() {
    System.setErr( oldErr );
    System.setOut( oldOut );
    newErr.close();
    newOut.close();
  }

}
