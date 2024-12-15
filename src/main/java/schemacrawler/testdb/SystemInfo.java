package schemacrawler.testdb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SystemInfo {

  public static void printDatabaseInfo(final Connection connection) throws Exception {

    System.out.println("Database Information:");

    final DatabaseMetaData dbMetaData = connection.getMetaData();

    printDatabaseInfo(() -> "  driver.name=" + dbMetaData.getDriverName());
    printDatabaseInfo(() -> "  driver.version=" + dbMetaData.getDriverVersion());

    printDatabaseInfo(() -> "  database.product.name=" + dbMetaData.getDatabaseProductName());
    printDatabaseInfo(() -> "  database.product.version=" + dbMetaData.getDatabaseProductVersion());
  }

  public static void printJvmArch() {

    System.out.println("JVM Architecture:");

    final String arch = System.getProperty("os.arch");
    final String archDescription;
    switch (arch) {
      case "x86":
        archDescription = "32-bit x86";
        break;
      case "amd64":
      case "x86_64":
        archDescription = "64-bit x86-64";
        break;
      case "arm":
        archDescription = "32-bit ARM";
        break;
      case "aarch64":
        archDescription = "64-bit ARM";
        break;
      case "risc":
        archDescription = "RISC (Reduced Instruction Set Computer)";
        break;
      case "sparc":
        archDescription = "SPARC (Scalable Processor Architecture)";
        break;
      case "ppc":
      case "powerpc":
        archDescription = "PowerPC";
        break;
      case "mips":
        archDescription = "MIPS (Microprocessor without Interlocked Pipeline Stages)";
        break;
      default:
        archDescription = "Unknown architecture";
        break;
    }
    System.out.printf("  %s (%s)%n", archDescription, arch);
  }

  public static void printSystemInfo() {

    System.out.println("System Information:");

    final Pattern systemInfoProperty =
        Pattern.compile("^(java|jvm|os).*$", Pattern.CASE_INSENSITIVE);

    System.getProperties().keySet().stream().map(String::valueOf).sorted()
        .filter(key -> systemInfoProperty.matcher(key).matches())
        .forEachOrdered(key -> System.out.printf("  %s=%s%n", key, System.getProperty(key)));
  }

  private static void printDatabaseInfo(final Callable<String> propertyFunction) {
    try {
      final String property = propertyFunction.call();
      System.out.println(property);
    } catch (final Exception e) {
      // Ignore
    }
  }

  public static void printArgs(final String... args) {

    System.out.println("Commandline Parameters:");

    Stream.of(args).forEach(arg -> System.out.println("  " + arg));
  }
}
