package kr.magicbox.generalgoods.adapter.out.communication.grpc;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import kr.magicbox.generalgoods.adapter.out.communication.grpc.exception.CreatorNotFoundException;
import kr.magicbox.generalgoods.adapter.out.communication.grpc.exception.CreatorServiceUnavailableException;
import kr.magicbox.generalgoods.application.port.out.CreatorIdQueryPort;
import kr.magicbox.generalgoods.domain.vo.CreatorId;
import kr.magicbox.generalgoods.domain.vo.UserId;
import kr.magicbox.generalgoods.grpc.creator.CreatorServiceGrpc;
import kr.magicbox.generalgoods.grpc.creator.GetCreatorIdByUserIdRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatorGrpcAdapter implements CreatorIdQueryPort {

    private final ManagedChannel creatorManagedChannel;

    @Override
    @CircuitBreaker(name = "creatorService", fallbackMethod = "getCreatorIdFallback")
    @TimeLimiter(name = "creatorService", fallbackMethod = "getCreatorIdFallback")
    public CompletableFuture<CreatorId> getCreatorId(UserId userId) {
        return GrpcFutures.toCompletable(
                CreatorServiceGrpc.newFutureStub(creatorManagedChannel).getCreatorIdByUserId(
                        GetCreatorIdByUserIdRequest.newBuilder().setUserId(userId.value()).build()
                )
        ).thenApply(response -> new CreatorId(response.getCreatorId()));
    }

    @SuppressWarnings("unused")
    private CompletableFuture<CreatorId> getCreatorIdFallback(UserId userId, Throwable throwable) {
        if (throwable instanceof StatusRuntimeException statusException
                && statusException.getStatus().getCode() == Status.Code.NOT_FOUND) {
            throw new CreatorNotFoundException();
        }
        log.warn("크리에이터 서비스 연결 실패");
        throw new CreatorServiceUnavailableException(throwable);
    }
}
