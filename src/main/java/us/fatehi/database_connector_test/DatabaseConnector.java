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

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;

@CommandLine.Command(
    name = "Database Connection Test",
    description = "Tests connection to a database",
    version = "Database Connection Test, v1.0.0",
    mixinStandardHelpOptions = true)
public class DatabaseConnector implements Callable<Integer> {

  public static void main(final String... args) {
    final DatabaseConnector databaseConnector = new DatabaseConnector(args);
    final int exitCode = new CommandLine(databaseConnector).execute(args);
    if (exitCode != 0) {
      System.err.println(DatabaseConnector.class.getSimpleName() + " has exited with an error");
    }
  }

  private final String[] args;

  @CommandLine.Option(
      names = {"--url"},
      description = "JDBC connection URL to the database",
      paramLabel = "<url>")
  private String connectionUrl;

  @ArgGroup(heading = "Specify the database user name using one of these options\n")
  private UserOptions userOptions;

  @ArgGroup(heading = "Specify the database password using one of these options\n")
  private PasswordOptions passwordOptions;

  @CommandLine.Option(
      names = {"--debug", "-d"},
      description = "Debug trace")
  private boolean debug;

  private DatabaseConnector(final String[] args) {
    this.args = args;
  }

  @Override
  public Integer call() {

    System.out.println("Database Connection Test, v1.0.0");
    if (debug) {
      SystemInfo.printArgs(args);
      SystemInfo.printSystemInfo();
      SystemInfo.printJvmArch();
    }

    if (connectionUrl == null || connectionUrl.isEmpty()) {
      System.err.println("Database connection url is required");
      return 1;
    }
    try (final Connection connection =
        DriverManager.getConnection(
            connectionUrl, userOptions.getUser(), passwordOptions.getPassword())) {
      SystemInfo.printDatabaseInfo(connection);
    } catch (final Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }
}
