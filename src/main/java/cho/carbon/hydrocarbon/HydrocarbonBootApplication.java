package cho.carbon.hydrocarbon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.net.UnknownHostException;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class, MongoAutoConfiguration.class, DataSourceAutoConfiguration.class}  )
public class HydrocarbonBootApplication {
    static Logger logger = LoggerFactory.getLogger(HydrocarbonBootApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(HydrocarbonBootApplication.class, args);

    }

}
