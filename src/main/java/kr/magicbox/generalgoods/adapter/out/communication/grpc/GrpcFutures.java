package kr.magicbox.generalgoods.adapter.out.communication.grpc;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

public final class GrpcFutures {

    private GrpcFutures() {}

    public static <T> CompletableFuture<T> toCompletable(ListenableFuture<T> future) {
        CompletableFuture<T> result = new CompletableFuture<>();
        future.addListener(() -> {
            try {
                result.complete(future.get());
            } catch (Exception e) {
                result.completeExceptionally(e);
            }
        }, Runnable::run);
        return result;
    }
}
