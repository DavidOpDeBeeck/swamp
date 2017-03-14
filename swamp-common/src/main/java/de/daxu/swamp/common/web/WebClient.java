package de.daxu.swamp.common.web;

import com.fasterxml.jackson.databind.JavaType;
import de.daxu.swamp.common.jackson.SwampObjectMapper;
import de.daxu.swamp.common.web.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;

public class WebClient {

    private static final SwampObjectMapper OBJECT_MAPPER = new SwampObjectMapper();

    public static Resource resource(String resource) {
        return new Resource(resource);
    }

    public static JavaType list(Class returnType) {
        return OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, returnType);
    }

    private WebClient() {
    }

    public static class Resource {

        private final StringBuilder url;
        private final RestTemplate restTemplate;
        private final Logger logger = LoggerFactory.getLogger(Resource.class);

        private JavaType returnType;

        private Resource(String baseUrl) {
            this.url = new StringBuilder(baseUrl);
            this.restTemplate = new RestTemplate();
        }

        public Resource path(String path) {
            this.url.append(format("/%s", path));
            return this;
        }

        public Resource type(JavaType returnType) {
            this.returnType = returnType;
            return this;
        }

        public Resource type(Class returnType) {
            this.returnType = OBJECT_MAPPER.getTypeFactory()
                    .constructType(returnType);
            return this;
        }

        public <T> T get() {
            String url = this.url.toString();
            logRequest(url);

            Object object = restTemplate.getForObject(url, Object.class);
            Response response = OBJECT_MAPPER.convertValue(object, Response.class);
            logResponse(response);

            return convertResponseData(response);
        }

        public String post(Object payload) {
            String url = this.url.toString();
            logRequest(url, payload);

            Object object = restTemplate.postForObject(url, payload, Object.class);
            Response response = OBJECT_MAPPER.convertValue(object, Response.class);
            logResponse(response);

            String location = response.getMeta().getLocation();
            return extractIdFromLocation(location);
        }

        public void put(Object payload) {
            String url = this.url.toString();
            logRequest(url, payload);
            restTemplate.put(url, payload, Response.class);
        }

        public void delete() {
            String url = this.url.toString();
            logRequest(url);
            restTemplate.delete(url, Response.class);
        }

        private String extractIdFromLocation(String location) {
            return location.substring(location.lastIndexOf("/") + 1, location.length());
        }

        private <S> S convertResponseData(Response response) {
            return OBJECT_MAPPER.convertValue(response.getData(), returnType);
        }

        private void logRequest(String url) {
            logger.debug("\n---------------------------------\n" +
                    "[URL] {}" +
                    "\n---------------------------------", url);
        }

        private void logRequest(String url, Object payload) {
            logger.debug("\n---------------------------------\n" +
                    "[URL] {} \n" +
                    "[PAYLOAD] \n{}" +
                    "\n---------------------------------", url, OBJECT_MAPPER.toJSON(payload));
        }

        private void logResponse(Response response) {
            logger.debug("\n---------------------------------\n" +
                    "[RESPONSE] \n{}" +
                    "\n---------------------------------", OBJECT_MAPPER.toJSON(response));
        }
    }
}
