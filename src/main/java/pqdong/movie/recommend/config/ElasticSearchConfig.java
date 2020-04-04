package pqdong.movie.recommend.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import pqdong.movie.recommend.service.ConfigService;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

public class ElasticSearchConfig {

    @Resource
    private ConfigService configService;

    @Bean
    public TransportClient transportClient(@Value("${spring.elasticsearch.ip}") String ip){
        String password = configService.getConfigValue("ESPASSWORD");
        try (TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
                .put("cluster.name", "docker-cluster")
                .put("xpack.security.user", password)
                .put("timeout", 10000)
                .put("client.transport.ping_timeout", 10000)
                .build())
                .addTransportAddress(new TransportAddress(new InetSocketAddress(ip, 9300)))) {
            return client;
        }
    }
}
