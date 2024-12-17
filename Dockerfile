# ========================================================================
# SchemaCrawler
# http://www.schemacrawler.com
# Copyright (c) 2024-2025, Sualeh Fatehi <sualeh@hotmail.com>.
# All rights reserved.
# ------------------------------------------------------------------------
#
# SchemaCrawler is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
#
# SchemaCrawler and the accompanying materials are made available under
# the terms of the Eclipse Public License v1.0, GNU General Public License
# v3 or GNU Lesser General Public License v3.
#
# You may elect to redistribute this code under any of these licenses.
#
# The Eclipse Public License is available at:
# http://www.eclipse.org/legal/epl-v10.html
#
# The GNU General Public License v3 and the GNU Lesser General Public
# License v3 are available at:
# http://www.gnu.org/licenses/
#
# ========================================================================

FROM eclipse-temurin:8-jre-noble

LABEL \
  "maintainer"="Sualeh Fatehi <sualeh@hotmail.com>" \
  "org.opencontainers.image.authors"="Sualeh Fatehi <sualeh@hotmail.com>" \
  "org.opencontainers.image.title"="Database Connection Test" \
  "org.opencontainers.image.description"="Test connectivity to a database server." \
  "org.opencontainers.image.url"="https://www.schemacrawler.com/" \
  "org.opencontainers.image.source"="https://github.com/schemacrawler/Database-Connection-Test" \
  "org.opencontainers.image.vendor"="SchemaCrawler" \
  "org.opencontainers.image.license"="(GPL-3.0 OR OR LGPL-3.0+ EPL-1.0)"

COPY target/database-connection-test /opt/database-connection-test/

WORKDIR /opt/database-connection-test

ENTRYPOINT ["bash", "./bin/run.sh"]
