package domain;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public interface WebToPdfConverter {
    String convert(String url, String name);
}
