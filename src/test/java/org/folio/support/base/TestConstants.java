package org.folio.support.base;

import static org.folio.spring.tools.config.properties.FolioEnvironment.getFolioEnvName;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstants {

  public static final String TENANT_ID = "test";
  public static final String USER_ID = "38d3a441-c100-5e8d-bd12-71bde492b723";

  public static final String AUTHORITY_TOPIC = "inventory.authority";
  public static final String INSTANCE_AUTHORITY_STATS_TOPIC = "links.instance-authority-stats";

  private static final String INSTANCE_LINKS_ENDPOINT_PATH = "/links/instances/{id}";
  private static final String AUTHORITY_LINKS_COUNT_ENDPOINT_PATH = "/links/authorities/bulk/count";
  private static final String LINKS_STATS_INSTANCE_ENDPOINT_PATH = "/links/stats/instance";

  public static String inventoryAuthorityTopic() {
    return String.format("%s.%s.%s", getFolioEnvName(), TENANT_ID, AUTHORITY_TOPIC);
  }

  public static String instanceAuthorityStatsTopic() {
    return String.format("%s.%s.%s", getFolioEnvName(), TENANT_ID, INSTANCE_AUTHORITY_STATS_TOPIC);
  }

  public static String linksInstanceEndpoint() {
    return INSTANCE_LINKS_ENDPOINT_PATH;
  }

  public static String authoritiesLinksCountEndpoint() {
    return AUTHORITY_LINKS_COUNT_ENDPOINT_PATH;
  }

  public static String linksStatsInstanceEndpoint() {
    return LINKS_STATS_INSTANCE_ENDPOINT_PATH;
  }
}
