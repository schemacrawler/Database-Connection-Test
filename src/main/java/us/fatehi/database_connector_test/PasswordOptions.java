/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2025, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package us.fatehi.database_connector_test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import picocli.CommandLine.Option;

public final class PasswordOptions {

  @Option(
      names = "--password:env",
      description = "Database password, from an environmental variable value",
      paramLabel = "<environment variable name>")
  private String passwordEnvironmentVariable;

  @Option(
      names = "--password:file",
      description = "Database password, read from a file",
      paramLabel = "<path to password file>")
  private Path passwordFile;

  @Option(
      names = "--password:prompt",
      interactive = true,
      description = "Database password, prompted from the console")
  private String passwordPrompted;

  @Option(
      names = {"--password"},
      description = "Database password",
      paramLabel = "<password>")
  private String passwordProvided;

  /**
   * Get password from various sources, in order of precedence. The password cannot be specified in
   * more than one way.
   *
   * @return Password, can be null
   */
  String getPassword() {
    String password = getPasswordProvided();

    if (password == null) {
      password = getPasswordPrompted();
    }
    if (password == null) {
      password = getPasswordFromFile();
    }
    if (password == null) {
      password = getPasswordFromEnvironment();
    }

    return password;
  }

  private String getPasswordFromEnvironment() {

    String passwordEnvironment = null;
    try {
      if (passwordEnvironmentVariable == null || passwordEnvironmentVariable.isEmpty()) {
        throw new IllegalArgumentException("No password environmental variable provided");
      }
      passwordEnvironment = System.getenv(passwordEnvironmentVariable);
    } catch (final Exception e) {
      throw new IllegalArgumentException(
          String.format(
              "Password could not be read from environmental variable <%s>",
              passwordEnvironmentVariable),
          e);
    }

    return passwordEnvironment;
  }

  private String getPasswordFromFile() {
    if (passwordFile == null) {
      return null;
    }

    String password = null;
    try {
      final List<String> lines = Files.readAllLines(passwordFile);
      if (!lines.isEmpty()) {
        password = lines.get(0);
      }
    } catch (final IOException e) {
      throw new RuntimeException(
          String.format("Password could not be read from file <%s>", passwordFile), e);
    }

    return password;
  }

  private String getPasswordPrompted() {
    return passwordPrompted;
  }

  private String getPasswordProvided() {
    return passwordProvided;
  }
}
