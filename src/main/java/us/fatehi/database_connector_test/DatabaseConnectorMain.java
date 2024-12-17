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

@CommandLine.Command(
    name = "Database Connection Test",
    description = "Tests connection to a database",
    mixinStandardHelpOptions = true)
public class DatabaseConnectorMain implements Callable<Integer> {

  public static void main(final String... args) {

    SystemInfo.printArgs(args);
    SystemInfo.printSystemInfo();
    SystemInfo.printJvmArch();

    final DatabaseConnectorMain databaseConnector = new DatabaseConnectorMain();
    final int exitCode = new CommandLine(databaseConnector).execute(args);
    if (exitCode != 0) {
      throw new RuntimeException(DatabaseConnectorMain.class + " has exited with an error");
    }
  }

  @CommandLine.Option(
      names = {"--url"},
      required = true,
      description = "JDBC connection URL to the database",
      paramLabel = "<url>")
  private String connectionUrl;

  @CommandLine.Option(
      names = {"--user"},
      description = "Database user name",
      paramLabel = "<user>")
  private String user;

  @CommandLine.Option(
      names = {"--password"},
      description = "Database password",
      paramLabel = "<password>")
  private String passwordProvided;

  @CommandLine.Option(
      names = {"--debug", "-d"},
      description = "Debug trace")
  private boolean debug;

  private DatabaseConnectorMain() {}

  @Override
  public Integer call() {
    try (final Connection connection =
        DriverManager.getConnection(connectionUrl, user, passwordProvided)) {
      SystemInfo.printDatabaseInfo(connection);
    } catch (final Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }
}
