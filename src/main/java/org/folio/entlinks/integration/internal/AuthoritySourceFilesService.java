package org.folio.entlinks.integration.internal;

import static java.util.Objects.nonNull;
import static org.folio.entlinks.config.constants.CacheNames.AUTHORITY_SOURCE_FILES_CACHE;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.entlinks.client.AuthoritySourceFileClient;
import org.folio.entlinks.client.AuthoritySourceFileClient.AuthoritySourceFile;
import org.folio.entlinks.exception.FolioIntegrationException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthoritySourceFilesService {

  private final AuthoritySourceFileClient client;

  @Cacheable(cacheNames = AUTHORITY_SOURCE_FILES_CACHE,
             key = "@folioExecutionContext.tenantId",
             unless = "#result.isEmpty()")
  public Map<UUID, String> fetchAuthoritySourceUrls() throws FolioIntegrationException {
    log.info("Fetching authority source files");
    var authoritySourceFiles = fetchAuthoritySourceFiles();
    if (authoritySourceFiles.isEmpty()) {
      throw new FolioIntegrationException("Authority source files are empty.");
    }

    return authoritySourceFiles.stream()
      .filter(file -> nonNull(file.id()) && nonNull(file.baseUrl()))
      .collect(Collectors.toMap(AuthoritySourceFile::id, AuthoritySourceFile::baseUrl));
  }

  private List<AuthoritySourceFile> fetchAuthoritySourceFiles() {
    try {
      return client.fetchAuthoritySourceFiles().authoritySourceFiles();
    } catch (Exception e) {
      throw new FolioIntegrationException("Failed to fetch authority source files", e);
    }
  }
}
