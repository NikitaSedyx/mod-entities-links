package org.folio.entlinks.integration.kafka;

import static java.util.Collections.singletonList;
import static org.folio.support.TestUtils.mockBatchFailedHandling;
import static org.folio.support.TestUtils.mockBatchSuccessHandling;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.concurrent.Callable;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.folio.entlinks.domain.dto.AuthorityInventoryRecord;
import org.folio.entlinks.domain.dto.AuthorityInventoryRecordMetadata;
import org.folio.entlinks.domain.dto.InventoryEvent;
import org.folio.entlinks.service.messaging.authority.InstanceAuthorityLinkUpdateService;
import org.folio.spring.test.type.UnitTest;
import org.folio.spring.tools.batch.MessageBatchProcessor;
import org.folio.spring.tools.systemuser.SystemUserScopedExecutionService;
import org.folio.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AuthorityInventoryEventListenerTest {

  @Mock
  private SystemUserScopedExecutionService executionService;
  @Mock
  private InstanceAuthorityLinkUpdateService instanceAuthorityLinkUpdateService;
  @Mock
  private MessageBatchProcessor messageBatchProcessor;

  @Mock
  private ConsumerRecord<String, InventoryEvent> consumerRecord;

  @InjectMocks
  private AuthorityInventoryEventListener listener;

  @BeforeEach
  void setUp() {
    when(executionService.executeSystemUserScoped(any(), any())).thenAnswer(invocation -> {
      var argument = invocation.getArgument(1, Callable.class);
      return argument.call();
    });
  }

  @ValueSource(strings = {"UPDATE", "DELETE"})
  @ParameterizedTest
  void shouldHandleEvent_positive_whenLinksExists(String type) {
    var authId = UUID.randomUUID();
    var newRecord = new AuthorityInventoryRecord().id(authId);
    var oldRecord = new AuthorityInventoryRecord().id(authId);
    var event = TestUtils.authorityEvent(type, newRecord, oldRecord);

    mockBatchSuccessHandling(messageBatchProcessor);
    when(consumerRecord.key()).thenReturn(authId.toString());
    when(consumerRecord.value()).thenReturn(event);

    listener.handleEvents(singletonList(consumerRecord));

    verify(instanceAuthorityLinkUpdateService).handleAuthoritiesChanges(singletonList(event));
  }

  @ValueSource(strings = {"UPDATE", "DELETE"})
  @ParameterizedTest
  void shouldHandleEvent_positive_whenNoLinksExists(String type) {
    var authId = UUID.randomUUID();
    var updatedByUserId = UUID.randomUUID();
    var meta = new AuthorityInventoryRecordMetadata().updatedByUserId(updatedByUserId);
    var newRecord = new AuthorityInventoryRecord().id(authId).metadata(meta);
    var oldRecord = new AuthorityInventoryRecord().id(authId).metadata(meta.updatedByUserId(updatedByUserId));
    var event = TestUtils.authorityEvent(type, newRecord, oldRecord);

    mockBatchSuccessHandling(messageBatchProcessor);
    when(consumerRecord.key()).thenReturn(authId.toString());
    when(consumerRecord.value()).thenReturn(event);

    listener.handleEvents(singletonList(consumerRecord));

    verify(instanceAuthorityLinkUpdateService).handleAuthoritiesChanges(singletonList(event));
  }

  @Test
  void shouldNotHandleEvent_negative_whenExceptionOccurred() {
    var authId = UUID.randomUUID();
    var newRecord = new AuthorityInventoryRecord().id(authId);
    var oldRecord = new AuthorityInventoryRecord().id(authId);
    var event = TestUtils.authorityEvent("UPDATE", newRecord, oldRecord);

    mockBatchFailedHandling(messageBatchProcessor, new RuntimeException("test message"));
    when(consumerRecord.key()).thenReturn(authId.toString());
    when(consumerRecord.value()).thenReturn(event);

    listener.handleEvents(singletonList(consumerRecord));

    verify(instanceAuthorityLinkUpdateService, never()).handleAuthoritiesChanges(singletonList(event));
  }

}
