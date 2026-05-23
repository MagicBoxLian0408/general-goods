package kr.magicbox.generalgoods.adapter.out.communication.grpc;

import io.grpc.ManagedChannel;
import kr.magicbox.generalgoods.adapter.out.communication.ServiceHost;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcConfiguration {

    @Bean
    public ManagedChannel creatorManagedChannel(GrpcChannelFactory grpcChannelFactory) {
        return grpcChannelFactory.createChannel(ServiceHost.CREATOR.getHostName());
    }
}
