package sanlab.itv.nakivojpslaveowner.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;
import sanlab.itv.nakivojpshared.request.CreateJobRequest;
import sanlab.itv.nakivojpslaveowner.exception.ProcessingCreateRequestException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class HashingRequestService {

    private final ObjectMapper objMapper;

    public HashingRequestService(ObjectMapper standardObjectMapper) {
        this.objMapper = standardObjectMapper.copy();
        this.objMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        this.objMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        this.objMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    public String hash(CreateJobRequest req) {
        try {
            String json = objMapper.writeValueAsString(req);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(json.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (JsonProcessingException | NoSuchAlgorithmException e) {
            throw ProcessingCreateRequestException.hashingRequest();
        }
    }

}
